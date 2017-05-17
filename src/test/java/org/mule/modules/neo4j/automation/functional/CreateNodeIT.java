/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getTestLabel;

public class CreateNodeIT extends AbstractTestCases {

    @Test
    public void createNodeTest() throws JsonProcessingException {
        getConnector().createNode(getTestLabel(), null);
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{}}]"));
    }

    @Test
    public void createNodeWithParamsTest() throws JsonProcessingException {
        getConnector().createNode(getTestLabel(), getParamsMap());
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{\"born\":1956,\"name\":\"Tom Hanks\"}}]"));
    }
}
