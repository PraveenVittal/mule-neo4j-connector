/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.PARAMS_MAP;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;

public class CreateNodeTestCase extends AbstractTestCases {

    @Test
    public void createNodeTest() throws Exception {
        assertThat(execute(TEST_LABEL), hasSize(0));
        createNode(TEST_LABEL, null);
        assertThat(execute(TEST_LABEL), hasSize(1));
    }

    @Test
    public void createNodeWithParamsTest() throws Exception {
        createNode(TEST_LABEL, PARAMS_MAP);
        assertThat(execute(TEST_LABEL), contains(A_NODE));
    }
}
