/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getParamsMap;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.getTestLabel;

public class UpdateNodesIT extends AbstractTestCases {

    @Before
    public void setUp() {
        getConnector().createNode(getTestLabel(), getParamsMap());
    }

    @Test
    public void updateNodesTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{\"born\":1956,\"name\":\"Tom Hanks\"}}]"));
        getConnector().updateNodes(getTestLabel(), null, ImmutableMap.<String, Object>builder().put("born", 1980).build());
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{\"born\":1980,\"name\":\"Tom Hanks\"}}]"));
    }

    @Test
    public void updateNodesWithParamsTest() throws JsonProcessingException {
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{\"born\":1956,\"name\":\"Tom Hanks\"}}]"));
        getConnector().updateNodes(getTestLabel(), ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").build(),
                ImmutableMap.<String, Object>builder().put("born", 1980).build());
        assertThat(getTestLabelNode(), equalTo("[{\"a\":{\"born\":1980,\"name\":\"Tom Hanks\"}}]"));

    }

}
