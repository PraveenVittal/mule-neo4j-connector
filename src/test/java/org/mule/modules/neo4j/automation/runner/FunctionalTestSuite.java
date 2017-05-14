/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.runner;

import com.google.common.collect.ImmutableMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.neo4j.automation.functional.CreateNodesIT;
import org.mule.modules.neo4j.automation.functional.CreateRelationBetweenNodesIT;
import org.mule.modules.neo4j.automation.functional.ReadIT;
import org.mule.modules.neo4j.automation.functional.WriteIT;
import org.mule.modules.neo4j.internal.Neo4JClientImpl;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import java.io.IOException;
import java.util.Map;
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

    private static final Neo4JClientImpl client = new Neo4JClientImpl();

    @BeforeClass
    public static void initialiseSuite() throws Exception {

        Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();
        Map<String, Object> config = ImmutableMap.<String, Object>builder()
                .put("username", properties.getProperty("config.username"))
                .put("password", properties.getProperty("config.password"))
                .put("url", properties.getProperty("config.url"))
                .build();

        client.connect(config);

        cleanData();

        client.write(readResourceStatement("/data/dataSet.txt"), null);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        cleanData();
        client.close();
    }

    private static void cleanData() throws IOException {
        client.write(readResourceStatement("/data/removeQuery.txt"), null);
    }

}
