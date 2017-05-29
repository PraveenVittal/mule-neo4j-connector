/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.*;

import org.junit.After;
import org.junit.Before;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class AbstractTestCases extends AbstractTestCase<Neo4jConnector> {

    public AbstractTestCases() {
        super(Neo4jConnector.class);
    }

    protected List<Map<String, Object>> getTestLabelNode(String label) throws JsonProcessingException {
        return getConnector().execute(format(QUERY_RETURN_A_NODE, label), null);
    }

    @Before
    public void setUp() {
        cleanUpDB();
    }

    @After
    public void tearDown() {
        cleanUpDB();
    }

    private void cleanUpDB(){
        getConnector().execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), null);
        getConnector().execute(format(QUERY_DELETE_A_NODE, TEST_LABEL2), null);
    }
}
