package org.mule.modules.neo4j.internal.operation;

import org.mule.modules.neo4j.internal.client.Neo4jService;
import org.mule.modules.neo4j.internal.client.Neo4jServiceImpl;
import org.mule.modules.neo4j.internal.config.Neo4jConfig;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
import org.mule.modules.neo4j.internal.exception.Neo4jErrorTypeProvider;
import org.mule.modules.neo4j.internal.metadata.NodeMetadataResolver;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.*;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import javax.ws.rs.DefaultValue;
import java.util.List;
import java.util.Map;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

@Throws(Neo4jErrorTypeProvider.class)
public class Neo4jAdvancedOperations extends Neo4jOperations {

    public Neo4jAdvancedOperations() {
        super(Neo4jServiceImpl::new);
    }
    @OutputResolver(output = NodeMetadataResolver.class)
    @MediaType(APPLICATION_JSON)
    public void createNode(@Config Neo4jConfig config,
                               @Connection Neo4jConnection connection,
                               String label,Map<String, Object> map) {
        newExecutionBuilder(config, connection).execute(Neo4jService::createNode)
                .withParam(label)
                .withParam(map);
    }

    @OutputResolver(output = NodeMetadataResolver.class)

    public  List<Map<String, Object>>  execute(@Config Neo4jConfig config,
                           @Connection Neo4jConnection connection,String cql,
                           String label,Map<String, Object> map) {
        return newExecutionBuilder(config, connection).execute(Neo4jService::execute)
                .withParam(cql)
                .withParam(label)
                .withParam(map);
    }


    @OutputResolver(output = NodeMetadataResolver.class)

    public  List<Map<String, Object>>  selectNodes(@Config Neo4jConfig config,
                                                   @Connection Neo4jConnection connection,
                                                   String label, Map<String, Object> map) {
        return newExecutionBuilder(config, connection).execute(Neo4jService::selectNodes)
                .withParam(label)
                .withParam(map);
    }

    public void  updateNodes(@Config Neo4jConfig config,
                             @Connection Neo4jConnection connection,
                             String label, Map<String, Object> parameters, Map<String, Object> setParameters) {
       newExecutionBuilder(config, connection).execute(Neo4jService::updateNodes)
                .withParam(label)
                .withParam(parameters)
                .withParam( setParameters );
    }

    public void  deleteNodes(@Config Neo4jConfig config,
                             @Connection Neo4jConnection connection,
                             String label, boolean removeRelationships, Map<String, Object> parameters) {
        newExecutionBuilder(config, connection).execute(Neo4jService::deleteNodes)
                .withParam(label)
                .withParam( removeRelationships )
                .withParam(parameters);
    }



}
