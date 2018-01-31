///**
// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package com.mulesoft.connectors.neo4j.automation.functional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class UpdateNodesTestCase extends AbstractTestCases {

	@Override
	@Before
	public void setUp() throws Exception {
		createNode(TestDataBuilder.TEST_LABEL, TestDataBuilder.getParamsMap());
	}

	@Test
	public void updateNodesTest() throws Exception {
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), Matchers.is(TestDataBuilder.NAME));
		updateNodes(TestDataBuilder.TEST_LABEL, null, TestDataBuilder.toJSONStream(TestDataBuilder.TOMHANKS_BORN_PARAM));
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.BORN_TAG), Matchers.is(TestDataBuilder.UPDATE_BORN));
	}

	@Test
	public void updateNodesWithParamsTest() throws Exception {
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.NAME_TAG), Matchers.is(TestDataBuilder.NAME));
		updateNodes(TestDataBuilder.TEST_LABEL, TestDataBuilder.toJSONStream(TestDataBuilder.TOMHANKS_NAME_PARAM), TestDataBuilder.toJSONStream(TestDataBuilder.TOMHANKS_BORN_PARAM));
		assertThat(getMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.BORN_TAG), Matchers.is(TestDataBuilder.UPDATE_BORN));
	}

}
