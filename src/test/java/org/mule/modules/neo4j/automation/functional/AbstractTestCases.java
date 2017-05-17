/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import static java.lang.String.format;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getTestLabel;

public class AbstractTestCases extends AbstractTestCase<Neo4jConnector> {

    public AbstractTestCases() {
        super(Neo4jConnector.class);
    }

    protected String getTestLabelNode() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getConnector().execute(format("MATCH (a:%s) RETURN a", getTestLabel()), null));
    }

    @After
    public void tearDown() {
        getConnector().execute(format("MATCH (a:%s) DELETE a", getTestLabel()), null);
    }
}
