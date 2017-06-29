
package org.mule.modules.neo4j.internal.connector.generated.connectivity;

import javax.annotation.Generated;
import org.mule.api.ConnectionException;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.TestableConnection;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConfig;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-06-29T06:11:54-03:00", comments = "Build UNNAMED.2793.f49b6c7")
public class BasicAuthenticationConfigNeo4jConnectorAdapter
    extends BasicAuthenticationConfig
    implements ConnectionManagementConnectionAdapter<BasicAuthenticationConfig, ConnectionManagementConfigNeo4jConnectorConnectionKey> , TestableConnection<ConnectionManagementConfigNeo4jConnectorConnectionKey>
{


    @Override
    public BasicAuthenticationConfig getStrategy() {
        return this;
    }

    @Override
    public void test(ConnectionManagementConfigNeo4jConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void connect(ConnectionManagementConfigNeo4jConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public String connectionId() {
        return super.connectionId();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

}
