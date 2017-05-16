/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.neo4j.automation.functional.CreateNodesIT;
import org.mule.modules.neo4j.automation.functional.CreateRelationBetweenNodesIT;
import org.mule.modules.neo4j.automation.functional.ReadIT;
import org.mule.modules.neo4j.automation.functional.WriteIT;
import org.mule.modules.neo4j.internal.client.Neo4JClient;
import org.mule.modules.neo4j.internal.client.Neo4JClientImpl;
import org.mule.modules.neo4j.internal.connection.Neo4JConnection;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConnectionBuilder;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import java.io.IOException;
import java.util.Properties;

import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.readResourceStatement;

@RunWith(Suite.class)
@SuiteClasses({
    ReadIT.class,
    WriteIT.class,
    CreateNodesIT.class,
    CreateRelationBetweenNodesIT.class
})
public class FunctionalTestSuite {



    @BeforeClass
    public static void initialiseSuite() throws Exception {
        Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();
        try (Neo4JConnection connection = connect(properties.getProperty("config.url"), properties.getProperty("config.username"), properties.getProperty("config.password"))) {
            Neo4JClient client = new Neo4JClientImpl(connection);
            cleanData(client);
            client.execute(readResourceStatement("/data/dataSet.txt"), null);
        }
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();
        try (Neo4JConnection connection = connect(properties.getProperty("config.url"), properties.getProperty("config.username"), properties.getProperty("config.password"))) {
            Neo4JClient client = new Neo4JClientImpl(connection);
            cleanData(client);
        }
    }

    private static void cleanData(Neo4JClient client) throws IOException {
        client.execute(readResourceStatement("/data/removeQuery.txt"), null);
    }

    private static Neo4JConnection connect(String url, String username, String password) {
        return new BasicAuthenticationConnectionBuilder()
                .withUrl(url)
                .withUsername(username)
                .withPassword(password)
                .create();
    }
}
