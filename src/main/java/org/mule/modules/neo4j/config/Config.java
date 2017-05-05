package org.mule.modules.neo4j.config;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.modules.neo4j.internal.Neo4JClientImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mule.api.ConnectionExceptionCode.INCORRECT_CREDENTIALS;

@ConnectionManagement(friendlyName = "Configuration")
public class Config {

    @Configurable
    @Placement(order = 1)
    @FriendlyName("Connection URL")
    private String url;

    private Neo4JClientImpl client;

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
        client = new Neo4JClientImpl();

        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("url",url);

        try {
            getClient().connect(map);
        }catch(Exception e){
            throw new ConnectionException(INCORRECT_CREDENTIALS,"",e.getMessage());
        }
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        getClient().close();
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        return getClient()!= null;
    }

    @ConnectionIdentifier
    public String connectionId() {
        return getClient().toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Neo4JClientImpl getClient() {
        return client;
    }

    public void setClient(Neo4JClientImpl client) {
        this.client = client;
    }
}