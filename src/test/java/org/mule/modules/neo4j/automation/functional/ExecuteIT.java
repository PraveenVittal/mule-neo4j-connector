/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_A_NODE_QUERY;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_STRING;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_A_WITH_PARAMS_NODE_QUERY;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;

public class ExecuteIT extends AbstractTestCases {

    @Test
    public void executeTest() throws JsonProcessingException {
        assertThat(objectToJsonString(getConnector().execute(format(CREATE_A_NODE_QUERY, TEST_LABEL, PARAMS_STRING), null)),
                equalTo(getTestLabelNode()));
    }

    @Test
    public void executeWithParamsTest() throws JsonProcessingException {
        assertThat(
                objectToJsonString(getConnector().execute(format(CREATE_A_WITH_PARAMS_NODE_QUERY, TEST_LABEL), PARAMS_MAP)),
                equalTo(getTestLabelNode()));
    }
}
