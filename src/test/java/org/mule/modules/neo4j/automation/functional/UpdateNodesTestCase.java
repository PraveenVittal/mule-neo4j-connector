///**
// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package org.mule.modules.neo4j.automation.functional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.BORN_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TOMHANKS_BORN_PARAM;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TOMHANKS_NAME_PARAM;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.UPDATE_BORN;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.toJSONStream;

import org.junit.Before;
import org.junit.Test;

public class UpdateNodesTestCase extends AbstractTestCases {

	@Override
	@Before
	public void setUp() throws Exception {
		createNode(TEST_LABEL, getParamsMap());
	}

	@Test
	public void updateNodesTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		updateNodes(TEST_LABEL, null, toJSONStream(TOMHANKS_BORN_PARAM));
		assertThat(getMapKeyValue(TEST_LABEL, BORN_TAG), is(UPDATE_BORN));
	}

	@Test
	public void updateNodesWithParamsTest() throws Exception {
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		updateNodes(TEST_LABEL, toJSONStream(TOMHANKS_NAME_PARAM), toJSONStream(TOMHANKS_BORN_PARAM));
		assertThat(getMapKeyValue(TEST_LABEL, BORN_TAG), is(UPDATE_BORN));
	}

}
