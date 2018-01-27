/////**
/// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_TEST_RELATION;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_DELETE_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL2;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.toList;

import org.junit.Before;
import org.junit.Test;

public class DeleteNodesTestCase extends AbstractTestCases {

	@Before
	public void setUp() throws Exception {
		createNode(TEST_LABEL, getParamsMap());
	}

	@Test
	public void deleteNodeTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		deleteNodes(TEST_LABEL, false, null);
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
	}

	@Test
	public void deleteNodeWithParamsTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		deleteNodes(TEST_LABEL, false, getParamsMap());
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
	}

	@Test
	public void deleteNodeFailTest() throws Exception {
		createNode(TEST_LABEL2, getParamsMap());
		execute(CREATE_TEST_RELATION, null);
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		assertThat(getMapKeyValue(TEST_LABEL2, NAME_TAG), is(NAME));
		try {
			deleteNodes(TEST_LABEL2, true, getParamsMap());
		} finally {
			deleteNodes(TEST_LABEL2, true, null);
		}
	}

	@Test
	public void deleteNodeWithAllRelationshipsTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		deleteNodes(TEST_LABEL, true, null);
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
	}

	@Test
	public void deleteNodeWithAllRelationshipsWithParamsTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		deleteNodes(TEST_LABEL, true, getParamsMap());
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
	}
}
