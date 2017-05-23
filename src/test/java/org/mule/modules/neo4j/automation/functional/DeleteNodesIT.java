/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.*;

public class DeleteNodesIT extends AbstractTestCases {

    @Before
    public void setUp() {
        getConnector().createNode(TEST_LABEL, PARAMS_MAP);
    }

    @Test
    public void deleteNodeTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(), equalTo(TOMHANKS_NODE));
        getConnector().deleteNodes(TEST_LABEL, null);
        assertThat(getTestLabelNode(), equalTo(EMPTY_JSON_LIST));
    }

    @Test
    public void deleteNodeWithParamsTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(), equalTo(TOMHANKS_NODE));
        getConnector().deleteNodes(TEST_LABEL, PARAMS_MAP);
        assertThat(getTestLabelNode(), equalTo(EMPTY_JSON_LIST));
    }
}
