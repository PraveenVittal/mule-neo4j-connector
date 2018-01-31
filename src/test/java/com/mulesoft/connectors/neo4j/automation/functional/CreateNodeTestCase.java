/**
// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package com.mulesoft.connectors.neo4j.automation.functional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class CreateNodeTestCase extends AbstractTestCases {

	@Test
	public void createNodeTest() throws Exception {
		assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
		createNode(TestDataBuilder.TEST_LABEL, null);
		assertThat(TestDataBuilder.toList(execute(String.format(TestDataBuilder.QUERY_DELETE_A_NODE, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap())), nullValue());
	}

	@Test
	public void createNodeWithParamsTest() throws Exception {
		createNode(TestDataBuilder.TEST_LABEL, TestDataBuilder.getParamsMap());
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), Matchers.is(TestDataBuilder.NAME));
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.BORN_TAG), Matchers.is(TestDataBuilder.BORN));
	}
}
