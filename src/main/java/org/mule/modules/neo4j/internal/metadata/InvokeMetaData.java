/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.metadata;

import static com.google.common.collect.Lists.transform;
import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.components.MetaDataCategory;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.modules.neo4j.internal.client.Neo4JMetadataClient;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

@MetaDataCategory
public class InvokeMetaData {

    private static final Function<String, MetaDataKey> META_DATA_KEY_FUNCTION = new Function<String, MetaDataKey>() {

        @Override
        public MetaDataKey apply(String label) {
            DefaultMetaDataKey key = new DefaultMetaDataKey(label, label);
            key.setCategory(InvokeMetaData.class.getSimpleName());
            return key;
        }
    };

    private Map<Class<?>, DataType> dataMapping = ImmutableMap.<Class<?>, DataType>builder()
            .put(Integer.class, DataType.INTEGER)
            .put(Long.class, DataType.LONG)
            .put(Boolean.class, DataType.BOOLEAN)
            .put(String.class, DataType.STRING)
            .put(Float.class, DataType.FLOAT)
            .put(Double.class, DataType.DOUBLE)
            .build();

    @Inject
    private Neo4jConnector connector;

    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetadataKeys() {
        return transform(getMetadataClient().getLabels(), META_DATA_KEY_FUNCTION);
    }

    @MetaDataRetriever
    public MetaData getMetaData(MetaDataKey key) {
        List<Map<String, Object>> nodes = connector.execute(format("MATCH (a:%s) RETURN a LIMIT 1", key.getId()), null);
        DynamicObjectBuilder<?> metadataBuilder = new DefaultMetaDataBuilder().createDynamicObject(key.getId());

        if (nodes.size() == 1) {
            for (String field : getMetadataClient().getConstraintProperties(key.getId())) {
                metadataBuilder.addSimpleField(field, dataMapping.get(Map.class.cast(nodes.get(0).get("a")).get(field).getClass()));
            }
        }

        return new DefaultMetaData(metadataBuilder.build());
    }

    private Neo4JMetadataClient getMetadataClient() {
        return connector.getMetadataClient();
    }

    public void setConnector(Neo4jConnector connector) {
        this.connector = connector;
    }
}
