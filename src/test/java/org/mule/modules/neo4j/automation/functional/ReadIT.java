/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReadIT extends AbstractTestCases {

    @Test
    public void readANode() throws JsonProcessingException {
        assertThat(new ObjectMapper().writeValueAsString(getConnector().execute(format("MATCH (a {name: \"Tom Hanks\"}) RETURN a", null), null)),
                equalTo("[{\"a\":{\"born\":1956,\"name\":\"Tom Hanks\"}}]"));
    }

    @Test
    public void readTest() {
        assertThat(getGson().toJson(getConnector().execute("MATCH (a {name: \"Tom Hanks\"}) RETURN a.name,a.born", null)),
                equalTo(getGson().toJson(getParser().parse("[{\"a.name\":\"Tom Hanks\",\"a.born\":1956}]").getAsJsonArray())));
    }

    @Test
    public void readParamTest() {
        Map<String, Object> param = ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").put("born", 1956).build();

        assertThat(getGson().toJson(getConnector().execute("MATCH (a {name: $name}) RETURN a.name,a.born", param)),
                equalTo(getGson().toJson(getParser().parse("[{\"a.name\":\"Tom Hanks\",\"a.born\":1956}]").getAsJsonArray())));
    }
}
