///**
// / * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
//*//
package com.mulesoft.connectors.neo4j.automation.functional;

import org.junit.Test;

import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.CREATE_A_NODE_QUERY;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.CREATE_A_WITH_PARAMS_NODE_QUERY;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.NAME;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.NAME_TAG;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.PARAMS_STRING;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static com.mulesoft.connectors.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExecuteTestCase extends AbstractTestCases {

    @Test
    public void executeTest() throws Exception {
        assertThat(getMapQueryValue(CREATE_A_NODE_QUERY, TEST_LABEL, PARAMS_STRING, NAME_TAG), is(NAME));
    }

    @Test
    public void executeWithParamsTest() throws Exception {
        assertThat(getMapKeyValueWihtParams(format(CREATE_A_WITH_PARAMS_NODE_QUERY, TEST_LABEL), getParamsMap(), NAME_TAG), is(NAME));
    }
}
