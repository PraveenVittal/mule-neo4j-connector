/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CreateNodesIT extends AbstractTestCases {

    @Test
    public void createNodeWithoutLabel() {
        Map<String, Object> node = ImmutableMap.<String, Object>builder().put("name", "George Porcel").put("born", 1936).build();
        List<Map<String, Object>> props = ImmutableList.<Map<String, Object>>builder().add(node).build();

        getConnector().createNodes(props, null);
        assertThat(getConnector().execute("MATCH (a) WHERE a.name=$name AND a.born=$born RETURN a", node).size(), equalTo(props.size()));

        getConnector().execute("MATCH (a) WHERE a.name=$name AND a.born=$born DELETE a", node);
    }

    @Test
    public void createNodeWithLabel() {
        String labelValidator = "createNodeWithLabel";
        List<Map<String, Object>> props = ImmutableList.<Map<String, Object>>builder()
                .add(ImmutableMap.<String, Object>builder().put("name", "Emile Dissi").put("born", 1943).build())
                .build();

        getConnector().createNodes(props, asList(labelValidator));
        assertThat(getConnector().execute(format("MATCH (a:%s) RETURN a", labelValidator), null).size(), equalTo(props.size()));

        getConnector().execute(format("MATCH (a:%s) DELETE a", labelValidator), null);
    }

    @Test
    public void createNodeWithLabels() {
        String labelValidator = "createNodeWithLabels";
        List<Map<String, Object>> props = ImmutableList.<Map<String, Object>>builder()
                .add(ImmutableMap.<String, Object>builder().put("name", "Charly Bala").put("born", 1925).build())
                .build();

        getConnector().createNodes(props, asList("Person", "Actor", labelValidator));
        assertThat(getConnector().execute(format("MATCH (a:%s) RETURN a", labelValidator), null).size(), equalTo(props.size()));
    }

    @Test
    public void createNodesWithLabels() {
        String labelValidator = "createNodesWithLabels";
        List<Map<String, Object>> props = ImmutableList.<Map<String, Object>>builder()
                .add(ImmutableMap.<String, Object>builder().put("name", "Charly G").put("born", 1958).build())
                .add(ImmutableMap.<String, Object>builder().put("name", "William Francella").put("born", 1955).build())
                .add(ImmutableMap.<String, Object>builder().put("name", "Richard Darin").put("born", 1957).build())
                .build();

        getConnector().createNodes(props, asList("Person", "Actor", labelValidator));
        assertThat(getConnector().execute(format("MATCH (a:%s) RETURN a", labelValidator), null).size(), equalTo(props.size()));
    }
}
