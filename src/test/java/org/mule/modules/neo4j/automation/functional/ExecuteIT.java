/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsString;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getTestLabel;

public class ExecuteIT extends AbstractTestCases {

    @Test
    public void executeTest() throws JsonProcessingException {
        assertThat(new ObjectMapper().writeValueAsString(getConnector().execute(format("CREATE (a:%s %s) RETURN a", getTestLabel(), getParamsString()), null)),
                equalTo(getTestLabelNode()));
    }

    @Test
    public void executeWithParamsTest() throws JsonProcessingException {
        assertThat(
                new ObjectMapper().writeValueAsString(getConnector().execute(format("CREATE (a:%s {name: $name, born: toInt($born)}) RETURN a", getTestLabel()), getParamsMap())),
                equalTo(getTestLabelNode()));
    }
}
