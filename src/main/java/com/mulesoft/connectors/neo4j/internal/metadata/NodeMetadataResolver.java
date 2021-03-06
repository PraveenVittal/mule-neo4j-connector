package com.mulesoft.connectors.neo4j.internal.metadata;

import com.google.common.collect.ImmutableMap;
import com.mulesoft.connectors.neo4j.internal.config.Neo4jConfig;
import com.mulesoft.connectors.neo4j.internal.connection.Neo4jConnection;
import com.mulesoft.connectors.neo4j.internal.service.Neo4jMetadataService;
import com.mulesoft.connectors.neo4j.internal.service.Neo4jServiceImpl;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static org.mule.metadata.json.api.JsonTypeLoader.JSON;
import static org.mule.runtime.api.metadata.DataType.BOOLEAN;
import static org.mule.runtime.api.metadata.DataType.NUMBER;
import static org.mule.runtime.api.metadata.DataType.STRING;
import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

public class NodeMetadataResolver implements InputTypeResolver<String>, TypeKeysResolver, OutputTypeResolver<String> {

    private Map<Class<?>, DataType> dataMapping = ImmutableMap.<Class<?>, DataType>builder()
            .put(Boolean.class, BOOLEAN)
            .put(String.class, STRING)
            .put(Integer.class, NUMBER)
            .put(Long.class, NUMBER)
            .put(Float.class, NUMBER)
            .put(Double.class, NUMBER)
            .build();

    @Override
    public MetadataType getInputMetadata(MetadataContext context, String key)
            throws MetadataResolvingException, ConnectionException {
        List<Map<String, Object>> nodes = new Neo4jServiceImpl(context.<Neo4jConfig>getConfig().get(),
                context.<Neo4jConnection>getConnection().get()).execute(format("MATCH (a:%s) RETURN a LIMIT 1", key), null);
        ObjectTypeBuilder builder = new BaseTypeBuilder(JSON).objectType().label(key);
        if (nodes.size() == 1) {
            for (String field : getMetadataService(context).getConstraintProperties(key)) {
                builder.addField()
                        .key(field)
                        .value()
                        .typeParameter(dataMapping.get(Map.class.cast(nodes.get(0).get("a")).get(field).getClass()).getType().getName());
            }
        }
        return builder.build();
    }

    @Override
    public MetadataType getOutputType(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException {
        return BaseTypeBuilder.create(JSON).nullType().build();
    }

    @Override
    public Set<MetadataKey> getKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException {
        return getMetadataService(context).getLabels().stream()
                .map(label -> newKey(label).withDisplayName(label).build()).collect(toSet());
    }

    @Override
    public String getCategoryName() {
        return NodeMetadataResolver.class.getCanonicalName();
    }

    @Override
    public String getResolverName() {
        return this.getClass().getSimpleName();
    }

    private Neo4jMetadataService getMetadataService(MetadataContext context) throws ConnectionException {
        return ((Neo4jConnection) context.getConnection().get()).getMetadataService();
    }
}
