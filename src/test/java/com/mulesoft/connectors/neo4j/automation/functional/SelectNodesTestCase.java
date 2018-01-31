/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.neo4j.automation.functional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

public class SelectNodesTestCase extends AbstractTestCases {

	@Override
	@After
	public void setUp() throws Exception {
		createNode(TestDataBuilder.TEST_LABEL, TestDataBuilder.getParamsMap());
	}

	@Test
	public void selectNodesIT() throws Exception {
		assertThat(getSelectMapKeyValue(TestDataBuilder.TEST_LABEL, null, TestDataBuilder.NAME_TAG), Matchers.is(TestDataBuilder.NAME));
	}

	@Test
	public void selectNodesWithParamsIT() throws Exception {
		assertThat(getSelectMapKeyValue(TestDataBuilder.TEST_LABEL, TestDataBuilder.getParamsMap(), TestDataBuilder.NAME_TAG), Matchers.is(TestDataBuilder.NAME));
	}
}
