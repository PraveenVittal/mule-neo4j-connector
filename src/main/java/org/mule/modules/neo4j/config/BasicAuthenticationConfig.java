/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.config;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.modules.neo4j.internal.client.Neo4JClient;
import org.mule.modules.neo4j.internal.client.Neo4JClientImpl;
import org.mule.modules.neo4j.internal.connection.Neo4JConnection;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConnectionBuilder;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import java.io.IOException;

import static org.mule.api.ConnectionExceptionCode.CANNOT_REACH;
import static org.mule.api.ConnectionExceptionCode.INCORRECT_CREDENTIALS;
import static org.mule.api.ConnectionExceptionCode.UNKNOWN_HOST;

@ConnectionManagement(friendlyName = "Basic Authentication Configuration")
public class BasicAuthenticationConfig {

    @Configurable
    @Placement(order = 1)
    @FriendlyName("Connection URL")
    private String url;

    private Neo4JConnection connection;

    /**
     * Connect
     *
     * @param username A username from Neo4j
     * @param password A password from Neo4j
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password) throws ConnectionException {
        try {
            this.connection = new BasicAuthenticationConnectionBuilder()
                    .withUrl(url)
                    .withUsername(username)
                    .withPassword(password)
                    .create();
            this.connection.validate();
        } catch (ClientException | AuthenticationException e) {
            throw new ConnectionException(INCORRECT_CREDENTIALS, e.code(), e.getMessage());
        } catch (IllegalArgumentException | SecurityException e) {
            throw new ConnectionException(UNKNOWN_HOST, "", e.getMessage());
        } catch (ServiceUnavailableException e) {
            throw new ConnectionException(CANNOT_REACH, e.code(), e.getMessage());
        }
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        try {
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        try {
            connection.validate();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @ConnectionIdentifier
    public String connectionId() {
        return connection.getId();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Neo4JClient getClient() {
        return new Neo4JClientImpl(connection);
    }
}