/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;

import org.junit.After;
import org.junit.Test;

public class SelectNodesTestCase extends AbstractTestCases {

	@Override
	@After
	public void setUp() throws Exception {
		createNode(TEST_LABEL, getParamsMap());
	}

	@Test
	public void selectNodesIT() throws Exception {
		assertThat(getSelectMapKeyValue(TEST_LABEL, null, NAME_TAG), is(NAME));
	}

	@Test
	public void selectNodesWithParamsIT() throws Exception {
		assertThat(getSelectMapKeyValue(TEST_LABEL, getParamsMap(), NAME_TAG), is(NAME));
	}
}
