package com.mulesoft.connectors.neo4j.internal.config;

import com.mulesoft.connectors.neo4j.internal.connection.Neo4jConnectionProvider;
import com.mulesoft.connectors.neo4j.internal.operation.Neo4jOperations;
import org.mule.connectors.commons.template.config.ConnectorConfig;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@ConnectionProviders(Neo4jConnectionProvider.class)
@Operations(Neo4jOperations.class)
public class Neo4jConfig implements ConnectorConfig {

}
