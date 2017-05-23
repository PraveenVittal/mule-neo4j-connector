/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.system;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.modules.neo4j.internal.client.Neo4JMetadataClientImpl;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConfig;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;

import javax.ws.rs.ProcessingException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BasicAuthenticationConfigIT {

    private BasicAuthenticationConfig config;
    private String username;
    private String password;

    @Before
    public void setUp() throws ConfigurationLoadingFailedException {
        final Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();

        config = new BasicAuthenticationConfig();
        username = properties.getProperty("config.username");
        password =  properties.getProperty("config.password");

        config.setBoltUrl(properties.getProperty("config.boltUrl"));
        config.setRestUrl(properties.getProperty("config.restUrl"));
    }

    @Test
    public void testBoltConnection() throws ConnectionException {
        config.connect(username, password);
        assertThat(config.isConnected(), is(true));
        config.disconnect();
    }

    @Test(expected = ConnectionException.class)
    public void testInvalidBoltUrl() throws ConnectionException {
        config.setBoltUrl(config.getBoltUrl().replace("bolt", "http"));
        config.connect(username, password);
    }

    @Test(expected = ConnectionException.class)
    public void testEmptyBoltUrl() throws ConnectionException {
        config.setBoltUrl("");
        config.connect(username, password);
    }

    @Test(expected = NullPointerException.class)
    public void testNullBoltUrl() throws ConnectionException {
        config.setBoltUrl(null);
        config.connect(username, password);
    }

    @Test
    public void testRestConnection() throws ConnectionException {
        config.connect(username, password);
        assertThat(getLabelsWithRestConnection(), hasSize(greaterThanOrEqualTo(0)));
        config.disconnect();
    }

    @Test(expected = ProcessingException.class)
    public void testInvalidRestUrl() throws ConnectionException{
        config.setRestUrl(config.getBoltUrl().replace("http", "bolt"));
        config.connect(username,password);
        getLabelsWithRestConnection();
    }

    @Test(expected = NullPointerException.class)
    public void testNullRestUrl() throws ConnectionException {
        config.setRestUrl(null);
        config.connect(username,password);
        getLabelsWithRestConnection();
    }

    @Test(expected = ProcessingException.class)
    public void testEmptyRestUrl() throws ConnectionException {
        config.setRestUrl("");
        config.connect(username,password);
        getLabelsWithRestConnection();
    }

    private List<String> getLabelsWithRestConnection(){
        return (new Neo4JMetadataClientImpl(config.getMetadataInfoConnection())).getLabels();
    }
}
