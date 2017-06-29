/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.connection.basic;

import static org.mule.api.ConnectionExceptionCode.CANNOT_REACH;
import static org.mule.api.ConnectionExceptionCode.INCORRECT_CREDENTIALS;
import static org.mule.api.ConnectionExceptionCode.UNKNOWN_HOST;

import java.io.IOException;
import java.util.Map;

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
import org.mule.modules.neo4j.internal.connection.Neo4JConnection;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectionManagement(friendlyName = "Basic Authentication")
public class BasicAuthenticationConfig {

    @Configurable
    @Placement(order = 1)
    @FriendlyName("BOLT URL")
    private String boltUrl;

    @Configurable
    @Placement(order = 2)
    @FriendlyName("REST URL")
    private String restUrl;

    private Neo4JConnection connection;
    private Map<String, Object> metadataInfoConnection;
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthenticationConfig.class);

    /**
     * Connect
     *
     * @param username
     *            A username from Neo4j
     * @param password
     *            A password from Neo4j
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password) throws ConnectionException {
        try {
            this.connection = new BasicAuthenticationConnectionBuilder().withBoltUrl(boltUrl).withUsername(username).withPassword(password).create();
            this.connection.validate();
            this.metadataInfoConnection = ImmutableMap.<String, Object>builder().put("username", username).put("password", password).put("restUrl", restUrl).build();
        } catch (ClientException | AuthenticationException e) {
            logger.trace("Connection failed: incorrect credentials", e);
            throw new ConnectionException(INCORRECT_CREDENTIALS, e.code(), e.getMessage());
        } catch (IllegalArgumentException | SecurityException e) {
            logger.trace("Connection failed: unknown host", e);
            throw new ConnectionException(UNKNOWN_HOST, "", e.getMessage());
        } catch (ServiceUnavailableException e) {
            logger.trace("Connection failed: cannot reach", e);
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
            logger.trace("isConnected() failed: not connected" ,e);
            return false;
        }
    }

    @ConnectionIdentifier
    public String connectionId() {
        return connection.getId();
    }

    public String getBoltUrl() {
        return boltUrl;
    }

    public void setBoltUrl(String boltUrl) {
        this.boltUrl = boltUrl;
    }

    public Neo4JConnection getConnection() {
        return connection;
    }

    public Map<String, Object> getMetadataInfoConnection() {
        return metadataInfoConnection;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

}