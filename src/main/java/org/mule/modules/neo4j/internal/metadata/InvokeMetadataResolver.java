package org.mule.modules.neo4j.internal.metadata;

import com.google.common.collect.ImmutableMap;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.java.api.JavaTypeLoader;
import org.mule.modules.neo4j.internal.client.Neo4jMetadataService;
import org.mule.modules.neo4j.internal.client.Neo4jService;
import org.mule.modules.neo4j.internal.client.Neo4jServiceImpl;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
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
import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

public class InvokeMetadataResolver implements InputTypeResolver<String>, TypeKeysResolver, OutputTypeResolver<String> {

    private Map<Class<?>, DataType> dataMapping = ImmutableMap.<Class<?>, DataType>builder()
            .put(Boolean.class, DataType.BOOLEAN)
            .put(String.class, DataType.STRING)
            .put(Integer.class, DataType.NUMBER)
            .put(Long.class, DataType.NUMBER)
            .put(Float.class, DataType.NUMBER)
            .put(Double.class, DataType.NUMBER)
            .build();

    @Override
    public MetadataType getInputMetadata(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException {
        List<Map<String, Object>> nodes = getService(context).execute(format("MATCH (a:%s) RETURN a LIMIT 1", key), null);
        ObjectTypeBuilder builder = new BaseTypeBuilder(JavaTypeLoader.JAVA).objectType().label(key);
        if (nodes.size() == 1) {
            getMetadataService(context).getConstraintProperties(key)
                    .forEach(field -> builder.addField().key(field).value().typeParameter(String.valueOf(dataMapping.get(Map.class.cast(nodes.get(0).get("a")).get(field).getClass()))));
        }
        return builder.build();
    }

    @Override
    public MetadataType getOutputType(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException {
        return null;
    }

    @Override
    public Set<MetadataKey> getKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException {
        return getMetadataService(context).getLabels().stream()
                .map(label -> newKey(label).withDisplayName(label).build())
                .collect(toSet());
    }

    @Override
    public String getCategoryName() {
        return InvokeMetadataResolver.class.getCanonicalName();
    }

    @Override
    public String getResolverName() {
        return this.getClass().getSimpleName();
    }

    private Neo4jMetadataService getMetadataService(MetadataContext context) throws ConnectionException {
        return ((Neo4jConnection) context.getConnection().get()).getMetadataService();
    }

    private Neo4jService getService(MetadataContext context) throws ConnectionException {
        return new Neo4jServiceImpl((Neo4jConnection) context.getConnection().get());
    }
}
