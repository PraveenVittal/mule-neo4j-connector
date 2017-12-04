package org.mule.modules.neo4j.internal.operation;

import org.mule.modules.neo4j.internal.client.Neo4jServiceImpl;
import org.mule.modules.neo4j.internal.config.Neo4jConfig;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
import org.mule.modules.neo4j.internal.exception.Neo4jErrorTypeProvider;
import org.mule.modules.neo4j.internal.metadata.NodeMetadataResolver;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.*;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import javax.ws.rs.DefaultValue;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.mule.modules.neo4j.internal.util.FormatFunction.toJsonResult;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

@Throws(Neo4jErrorTypeProvider.class)
public class Neo4jOperations {

    @OutputResolver(output = NodeMetadataResolver.class)
    @MediaType(APPLICATION_JSON)
    public InputStream createNode(@Config Neo4jConfig config,
                               @Connection Neo4jConnection connection,
                               String username,
                               String password) {
        return toJsonResult(newExecutionBuilder(config, connection).execute(DatabaseService::addUser)
                .withParam(username)
                .withParam(password));
    }

    @OutputResolver(output = NodeMetadataResolver.class)
    public List<Map<String, Object>> execute(@Connection Neo4jConnection connection, @Content String query, @Optional(defaultValue = "#[payload]") Map<String, Object> parameters) {
        return new Neo4jServiceImpl(connection).execute(query, parameters);
    }

    public void createNode(@Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label, @TypeResolver(NodeMetadataResolver.class) @Optional(defaultValue = "#[payload]") Map<String, Object> parameters) {
        new Neo4jServiceImpl(connection).createNode(label, parameters);
    }

    @OutputResolver(output = NodeMetadataResolver.class)
    public List<Map<String, Object>> selectNodes(@Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label, @Optional(defaultValue = "#[payload]") Map<String, Object> parameters) {
        return new Neo4jServiceImpl(connection).selectNodes(label, parameters);
    }

    public void updateNodes(@Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label, @Optional(defaultValue = "#[payload]") Map<String, Object> parameters, @Content Map<String, Object> setParameters) {
        new Neo4jServiceImpl(connection).updateNodes(label, parameters, setParameters);
    }

    public void deleteNodes(@Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label,
            @DisplayName("Also remove existing relationships") @DefaultValue("false") boolean removeRelationships, @Optional(defaultValue = "#[payload]") Map<String, Object> parameters) {
        new Neo4jServiceImpl(connection).deleteNodes(label, removeRelationships, parameters);
    }

}
