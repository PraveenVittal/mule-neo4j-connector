/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReadIT extends AbstractTestCases {

    @Test
    public void readTest() {
        String query = "MATCH (a {name: \"Tom Hanks\"}) RETURN a.name,a.born";
        String expected = "[{\"a.name\":\"Tom Hanks\",\"a.born\":1956}]";

        List<Map<String, Object>> result = getConnector().read(query, null);
        assertThat(getGson().toJson(result), equalTo(getGson().toJson(getParser().parse(expected).getAsJsonArray())));
    }

    @Test
    public void readParamTest() {
        String query = "MATCH (a {name: $name}) RETURN a.name,a.born";
        String expected = "[{\"a.name\":\"Tom Hanks\",\"a.born\":1956}]";
        Map<String, Object> param = ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").put("born", 1956).build();

        List<Map<String, Object>> result = getConnector().read(query, param);
        assertThat(getGson().toJson(result), equalTo(getGson().toJson(getParser().parse(expected).getAsJsonArray())));
    }
}
