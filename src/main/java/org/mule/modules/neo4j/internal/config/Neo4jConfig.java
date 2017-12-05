package org.mule.modules.neo4j.internal.config;

import org.mule.connectors.commons.template.config.ConnectorConfig;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConnectionProvider;
import org.mule.modules.neo4j.internal.operation.Neo4jAdvancedOperations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@ConnectionProviders(BasicAuthenticationConnectionProvider.class)
@Operations(Neo4jAdvancedOperations.class)
public class Neo4jConfig implements ConnectorConfig {

}
