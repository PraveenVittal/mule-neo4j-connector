package com.mulesoft.connectors.neo4j.internal.config;

import com.mulesoft.connectors.neo4j.internal.operation.Neo4jOperations;
import org.mule.connectors.commons.template.config.ConnectorConfig;
import com.mulesoft.connectors.neo4j.internal.connection.basic.BasicAuthenticationConnectionProvider;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@ConnectionProviders(BasicAuthenticationConnectionProvider.class)
@Operations(Neo4jOperations.class)
public class Neo4jConfig implements ConnectorConfig {

}
