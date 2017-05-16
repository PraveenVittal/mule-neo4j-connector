/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CreateRelationBetweenNodesIT extends AbstractTestCases {

    @Test
    public void createSimpleRelation() throws JsonProcessingException {
        getConnector().createRelationBetweenNodes(null, null, "a.name = \"Tom Hanks\" AND b.name = \"Keanu Reeves\"", "TestSimpleRelation", null);

        assertThat(new ObjectMapper().writeValueAsString(getConnector().execute("MATCH (a)<-[r:TestSimpleRelation]-(b) RETURN r", null)), equalTo("[{\"r\":{}}]"));
    }

    @Test
    public void createFullRelation() throws JsonProcessingException {
        Map<String, Object> props = ImmutableMap.<String, Object>builder().put("relation", "1on1").put("knowsFrom", "A Movie").build();

        getConnector().createRelationBetweenNodes(asList("Person"), asList("Person"), "a.name = \"Tom Hanks\" AND b.name = \"Keanu Reeves\"", "TestFullRelation", props);

        assertThat(new ObjectMapper().writeValueAsString(getConnector().execute("MATCH (a)<-[r:TestFullRelation]-(b) RETURN r", null)),
                equalTo("[{\"r\":{\"knowsFrom\":\"A Movie\",\"relation\":\"1on1\"}}]"));
    }
}
