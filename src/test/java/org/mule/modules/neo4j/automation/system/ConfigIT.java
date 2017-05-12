/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.system;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.modules.neo4j.config.Config;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigIT {

    private Config config;
    private String username;
    private String password;

    @Before
    public void setUp() throws ConfigurationLoadingFailedException {
        final Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();

        setUsername(properties.getProperty("config.username"));
        setPassword(properties.getProperty("config.password"));

        setConfig(new Config());
        config.setUrl(properties.getProperty("config.url"));
    }

    // TODO: Change Matchers.notNullValue for other verification type
    @Test
    public void testConnection() throws ConnectionException {
        getConfig().connect(username, password);
        assertThat(getConfig(), Matchers.notNullValue());
        getConfig().disconnect();
    }

    @Test(expected = ConnectionException.class)
    public void testIllegalArgument() throws ConnectionException {
        getConfig().setUrl("invalidURLFormat");
        getConfig().connect(username, password);
    }

    @Test(expected = ConnectionException.class)
    public void testClientException() throws ConnectionException {
        getConfig().setUrl(getConfig().getUrl().replace("bolt", "http"));
        getConfig().connect(username, password);
    }

    @Test(expected = ConnectionException.class)
    public void testServiceUnavailableException() throws ConnectionException {
        getConfig().setUrl("bolt://localhost:80/");
        getConfig().connect(username, password);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
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
}
