///**
// / * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
//*//
package com.mulesoft.connectors.neo4j.automation.functional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ExecuteTestCase extends AbstractTestCases {

	@Test
	public void executeTest() throws Exception {

		assertThat(getMapQueryValue(TestDataBuilder.CREATE_A_NODE_QUERY, TestDataBuilder.TEST_LABEL, TestDataBuilder.PARAMS_STRING, TestDataBuilder.NAME_TAG), Matchers.is(
                TestDataBuilder.NAME));
	}

	@Test
	public void executeWithParamsTest() throws Exception {
		assertThat(
				getMapKeyValueWihtParams(
                        String.format(TestDataBuilder.CREATE_A_WITH_PARAMS_NODE_QUERY, TestDataBuilder.TEST_LABEL), TestDataBuilder.getParamsMap(), TestDataBuilder.NAME_TAG),
				Matchers.is(TestDataBuilder.NAME));
	}
}
