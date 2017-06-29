/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CreateNodeIT extends AbstractTestCases {

    @Test
    public void createNodeTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(0));
        getConnector().createNode(TEST_LABEL, null);
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(1));
    }

    @Test
    public void createNodeWithParamsTest() throws JsonProcessingException {
        getConnector().createNode(TEST_LABEL, PARAMS_MAP);
        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
    }
}
