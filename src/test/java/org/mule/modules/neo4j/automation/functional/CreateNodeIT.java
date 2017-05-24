/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.EMPTY_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TOMHANKS_NODE;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CreateNodeIT extends AbstractTestCases {

    @Test
    public void createNodeTest() throws JsonProcessingException {
        getConnector().createNode(TEST_LABEL, null);
        assertThat(getTestLabelNode(TEST_LABEL), equalTo(EMPTY_NODE));
    }

    @Test
    public void createNodeWithParamsTest() throws JsonProcessingException {
        getConnector().createNode(TEST_LABEL, PARAMS_MAP);
        assertThat(getTestLabelNode(TEST_LABEL), equalTo(TOMHANKS_NODE));
    }
}
