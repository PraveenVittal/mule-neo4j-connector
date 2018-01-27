/**
// * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
// */
package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.BORN;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.BORN_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.NAME_TAG;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_DELETE_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.toList;

import org.junit.Test;

public class CreateNodeTestCase extends AbstractTestCases {

	@Test
	public void createNodeTest() throws Exception {
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
		createNode(TEST_LABEL, null);
		assertThat(toList(execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), getParamsMap())), nullValue());
	}

	@Test
	public void createNodeWithParamsTest() throws Exception {
		createNode(TEST_LABEL, getParamsMap());
		assertThat(getMapKeyValue(TEST_LABEL, NAME_TAG), is(NAME));
		assertThat(getMapKeyValue(TEST_LABEL, BORN_TAG), is(BORN));
	}
}
