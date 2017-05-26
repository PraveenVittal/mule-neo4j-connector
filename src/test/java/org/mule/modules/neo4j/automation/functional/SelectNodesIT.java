/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;

import org.junit.After;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SelectNodesIT extends AbstractTestCases {

    @After
    public void setUp() {
        getConnector().createNode(TEST_LABEL, PARAMS_MAP);
    }

    @Test
    public void selecNodesIT() throws JsonProcessingException {
        assertThat(objectToJsonString(getConnector().selectNodes(TEST_LABEL, null)), equalTo(getTestLabelNode(TEST_LABEL)));
    }

    @Test
    public void selectNodesWithParamsIT() throws JsonProcessingException {
        assertThat(objectToJsonString(getConnector().selectNodes(TEST_LABEL, PARAMS_MAP)), equalTo(getTestLabelNode(TEST_LABEL)));
    }

}