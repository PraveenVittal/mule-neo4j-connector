/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class WriteIT extends AbstractTestCases {

    @Test
    public void writeTest() throws IOException {
        getConnector().execute("CREATE (a:Person {name: \"John Cena\", born: toInt(1977)})", null);

        assertThat(getGson().toJson(getConnector().execute("MATCH (a:Person) WHERE a.name = \"John Cena\" RETURN a.name,a.born", null)),
                equalTo(getGson().toJson(getParser().parse("[{\"a.name\":\"John Cena\",\"a.born\":1977}]").getAsJsonArray())));
    }

    @Test
    public void writeParamTest() throws IOException {
        Map<String, Object> param = ImmutableMap.<String, Object>builder().put("name", "Johnny Tolengo").put("born", 1934).build();

        getConnector().execute("CREATE (a:Person {name: $name, born: toInt($born)})", param);

        assertThat(getGson().toJson(getConnector().execute("MATCH (a:Person) WHERE a.name = $name RETURN a.name,a.born", param)),
                equalTo(getGson().toJson(getParser().parse("[{\"a.name\":\"Johnny Tolengo\",\"a.born\":1934}]").getAsJsonArray())));
    }
}
