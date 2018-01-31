package com.mulesoft.connectors.neo4j.internal.connection;

import org.mule.connectors.commons.template.connection.ConnectorConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;

import javax.inject.Inject;

public class Neo4jConnectionProvider extends ConnectorConnectionProvider<Neo4jConnection> implements ConnectionProvider<Neo4jConnection> {

    @Inject
    private HttpService httpService;

    @Parameter
    @Placement(order = 1)
    private String username;

    @Parameter
    @Password
    @Placement(order = 2)
    private String password;

    @ParameterGroup(name = "Endpoint Settings")
    private AdditionalConnectionParams additionalConnectionParams;

    @Override
    public Neo4jConnection connect() throws ConnectionException {
        return new Neo4jConnection(username, password, additionalConnectionParams.getBoltUrl(), additionalConnectionParams.getRestUrl(), httpService);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdditionalConnectionParams getAdditionalConnectionParams() {
        return additionalConnectionParams;
    }

    public void setAdditionalConnectionParams(AdditionalConnectionParams additionalConnectionParams) {
        this.additionalConnectionParams = additionalConnectionParams;
    }
}
