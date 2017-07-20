/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.A_NODE_1980;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TOMHANKS_BORN_PARAM;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TOMHANKS_NAME_PARAM;

public class UpdateNodesIT extends AbstractTestCases {

    @Override
    @Before
    public void setUp() throws Exception {
        createNode(TEST_LABEL, PARAMS_MAP);
    }

    @Test
    public void updateNodesTest() throws Exception {
        assertThat(execute(TEST_LABEL), contains(A_NODE));
        updateNodes(TEST_LABEL, null, TOMHANKS_BORN_PARAM);
        assertThat(execute(TEST_LABEL), contains(A_NODE_1980));
    }

    @Test
    public void updateNodesWithParamsTest() throws Exception {
        assertThat(execute(TEST_LABEL), contains(A_NODE));
        updateNodes(TEST_LABEL, TOMHANKS_NAME_PARAM, TOMHANKS_BORN_PARAM);
        assertThat(execute(TEST_LABEL), contains(A_NODE_1980));
    }

}
