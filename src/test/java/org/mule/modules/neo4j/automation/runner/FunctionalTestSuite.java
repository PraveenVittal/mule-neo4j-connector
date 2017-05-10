/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.runner;

import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.readResourceStatement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.neo4j.automation.functional.ReadIT;
import org.mule.modules.neo4j.automation.functional.ReadWithParametersIT;
import org.mule.modules.neo4j.automation.functional.WriteIT;
import org.mule.modules.neo4j.automation.functional.WriteWithParametersIT;
import org.mule.modules.neo4j.internal.Neo4JClientImpl;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

@RunWith(Suite.class) @SuiteClasses({
        ReadIT.class, ReadWithParametersIT.class, WriteIT.class, WriteWithParametersIT.class
})

public class FunctionalTestSuite {

    private static final Neo4JClientImpl client = new Neo4JClientImpl();

    @BeforeClass public static void initialiseSuite() throws Exception {

        Map<String, Object> config = new HashMap<>();
        Properties properties = ConfigurationUtils.getAutomationCredentialsProperties();

        config.put("username", properties.getProperty("config.username"));
        config.put("password", properties.getProperty("config.password"));
        config.put("url", properties.getProperty("config.url"));

        client.connect(config);

        cleanData();

        client.write(readResourceStatement("/populate/dataSet.txt"));
    }

    @AfterClass public static void shutdownSuite() throws Exception {
        cleanData();
        client.close();
    }

    private static void cleanData() throws IOException {
        client.write(readResourceStatement("/populate/removeQuery.txt"));
    }

}
