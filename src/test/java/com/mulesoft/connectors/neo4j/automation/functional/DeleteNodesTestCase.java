/////**
/// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package com.mulesoft.connectors.neo4j.automation.functional;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class DeleteNodesTestCase extends AbstractTestCases {

    @Before
    public void setUp() throws Exception {
        createNode(TestDataBuilder.TEST_LABEL, TestDataBuilder.getParamsMap());
    }

    @Test
    public void deleteNodeTest() throws Exception {
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        deleteNodes(TestDataBuilder.TEST_LABEL, false, null);
        assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
    }

    @Test
    public void deleteNodeWithParamsTest() throws Exception {
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        deleteNodes(TestDataBuilder.TEST_LABEL, false, TestDataBuilder.getParamsMap());
        assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
    }

    @Test
    public void deleteNodeFailTest() throws Exception {
        createNode(TestDataBuilder.TEST_LABEL2, TestDataBuilder.getParamsMap());
        execute(TestDataBuilder.CREATE_TEST_RELATION, null);
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL2, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        try {
            deleteNodes(TestDataBuilder.TEST_LABEL2, true, TestDataBuilder.getParamsMap());
        } finally {
            deleteNodes(TestDataBuilder.TEST_LABEL2, true, null);
        }
    }

    @Test
    public void deleteNodeWithAllRelationshipsTest() throws Exception {
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        deleteNodes(TestDataBuilder.TEST_LABEL, true, null);
        assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
    }

    @Test
    public void deleteNodeWithAllRelationshipsWithParamsTest() throws Exception {
        assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), CoreMatchers.is(TestDataBuilder.NAME));
        deleteNodes(TestDataBuilder.TEST_LABEL, true, TestDataBuilder.getParamsMap());
        assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
    }
}
