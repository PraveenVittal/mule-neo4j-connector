package org.mule.modules.neo4j.internal.operation;

import org.mule.modules.neo4j.internal.client.Neo4jService;
import org.mule.modules.neo4j.internal.client.Neo4jServiceImpl;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
import org.mule.modules.neo4j.internal.exception.Neo4jErrorTypeProvider;
import org.mule.modules.neo4j.internal.metadata.InvokeMetadataResolver;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import javax.ws.rs.DefaultValue;
import java.util.List;
import java.util.Map;

@Throws(Neo4jErrorTypeProvider.class)
public class Neo4jOperations {

    @OutputResolver(output = InvokeMetadataResolver.class)
    public List<Map<String, Object>> execute(@Connection Neo4jConnection connection, @MetadataKeyId(InvokeMetadataResolver.class) @Content String query, @Optional Map<String, Object> parameters) {
        return getClient(connection).execute(query, parameters);
    }

    public void createNode(@Connection Neo4jConnection connection, @Content String label, @Optional Map<String, Object> parameters) {
        getClient(connection).createNode(label, parameters);
    }

    @OutputResolver(output = InvokeMetadataResolver.class)
    public List<Map<String, Object>> selectNodes(@Connection Neo4jConnection connection, @MetadataKeyId(InvokeMetadataResolver.class) @Content String label, @Optional Map<String, Object> parameters) {
        return getClient(connection).selectNodes(label, parameters);
    }

    public void updateNodes(@Connection Neo4jConnection connection, @Content String label, @Optional Map<String, Object> parameters, Map<String, Object> setParameters) {
        getClient(connection).updateNodes(label, parameters, setParameters);
    }

    public void deleteNodes(@Connection Neo4jConnection connection, @Content String label,
            @DisplayName("Also remove existing relationships") @DefaultValue("false") boolean removeRelationships, @Optional Map<String, Object> parameters) {
        getClient(connection).deleteNodes(label, removeRelationships, parameters);
    }

    private Neo4jService getClient(Neo4jConnection connection) {
        return new Neo4jServiceImpl(connection);
    }

}
