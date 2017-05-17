/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getTestLabel;

public class SelectNodesIT extends AbstractTestCases {

    @After
    public void setUp() {
        getConnector().createNode(getTestLabel(), getParamsMap());
    }

    @Test
    public void selecNodesIT() throws JsonProcessingException {
        assertThat(new ObjectMapper().writeValueAsString(getConnector().selectNodes(getTestLabel(), null)), equalTo(getTestLabelNode()));
    }

    @Test
    public void selectNodesWithParamsIT() throws JsonProcessingException {
        assertThat(new ObjectMapper().writeValueAsString(getConnector().selectNodes(getTestLabel(), getParamsMap())), equalTo(getTestLabelNode()));
    }

}
