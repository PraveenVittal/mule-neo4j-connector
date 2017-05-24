/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_DELETE_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_RETURN_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;

import org.junit.After;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractTestCases extends AbstractTestCase<Neo4jConnector> {

    public AbstractTestCases() {
        super(Neo4jConnector.class);
    }

    protected String getTestLabelNode(String label) throws JsonProcessingException {
        return objectToJsonString(getConnector().execute(format(QUERY_RETURN_A_NODE, label), null));
    }

    public String objectToJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @After
    public void tearDown() {
        getConnector().execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), null);
    }
}
