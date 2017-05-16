package org.mule.modules.neo4j.internal.connection.basic;

import org.mule.modules.neo4j.internal.connection.ConnectionBuilder;

public class BasicAuthenticationConnectionBuilder implements ConnectionBuilder<BasicAuthenticationConnection> {

    private String url;
    private String username;
    private String password;

    public BasicAuthenticationConnectionBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public BasicAuthenticationConnectionBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public BasicAuthenticationConnectionBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public BasicAuthenticationConnection create() {
        return new BasicAuthenticationConnection(url, username, password);
    }
}
