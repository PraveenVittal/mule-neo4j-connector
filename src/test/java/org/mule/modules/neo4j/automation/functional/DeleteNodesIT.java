/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_TEST_RELATION;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL2;

import org.junit.Before;
import org.junit.Test;
import org.mule.modules.neo4j.api.Neo4JConnectorException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DeleteNodesIT extends AbstractTestCases {

    @Override
    @Before
    public void setUp() {
        getConnector().createNode(TEST_LABEL, PARAMS_MAP);
    }

    @Test
    public void deleteNodeTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
        getConnector().deleteNodes(TEST_LABEL, false, null);
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(0));
    }

    @Test
    public void deleteNodeWithParamsTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
        getConnector().deleteNodes(TEST_LABEL, false, PARAMS_MAP);
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(0));
    }

    @Test(expected = Neo4JConnectorException.class)
    public void deleteNodeFailTest() throws JsonProcessingException {
        getConnector().createNode(TEST_LABEL2, PARAMS_MAP);
        getConnector().execute(CREATE_TEST_RELATION, null);

        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
        assertThat(getTestLabelNode(TEST_LABEL2), contains(A_NODE));

        try {
            getConnector().deleteNodes(TEST_LABEL2, false, PARAMS_MAP);
        } finally {
            getConnector().deleteNodes(TEST_LABEL2, true, null);
        }
    }

    @Test
    public void deleteNodeWithAllReleationshipsTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
        getConnector().deleteNodes(TEST_LABEL, true, null);
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(0));
    }

    @Test
    public void deleteNodeWithAllReleationshipsWithParamsTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(TEST_LABEL), contains(A_NODE));
        getConnector().deleteNodes(TEST_LABEL, true, PARAMS_MAP);
        assertThat(getTestLabelNode(TEST_LABEL), hasSize(0));
    }
}
