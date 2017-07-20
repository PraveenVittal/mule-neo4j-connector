package org.mule.modules.neo4j.internal.config;

import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConnectionProvider;
import org.mule.modules.neo4j.internal.operation.Neo4jOperations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

@ConnectionProviders(BasicAuthenticationConnectionProvider.class)
@Operations(Neo4jOperations.class)
public class Neo4JConfig {

    @Parameter
    @DisplayName("REST URL")
    private String restUrl;

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
