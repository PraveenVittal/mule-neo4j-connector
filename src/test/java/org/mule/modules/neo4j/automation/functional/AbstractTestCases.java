package org.mule.modules.neo4j.automation.functional;

import org.junit.After;
import org.junit.Before;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_DELETE_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_RETURN_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL2;

public class AbstractTestCases extends MuleArtifactFunctionalTestCase {

    protected static final String FLOW_CONFIG_LOCATION = "src/test/resources/automation-test-flows.xml";

    @Override
    public int getTestTimeoutSecs() {
        return 999999;
    }

    @Override
    protected String[] getConfigFiles() {
        return new String[] {
                FLOW_CONFIG_LOCATION
        };
    }

    @Before
    public void setUp() throws Exception {
        cleanUpDB();
    }

    @After
    public void tearDown() throws Exception {
        cleanUpDB();
    }

    private void cleanUpDB() throws Exception {
        execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), null);
        execute(format(QUERY_DELETE_A_NODE, TEST_LABEL2), null);
    }

    public List<Map<String, Object>> execute(String query, Map<String, Object> parameters) throws Exception {
        return (List<Map<String, Object>>) flowRunner("executeFlow")
                .withPayload(query)
                .withVariable("parameters", parameters)
                .run()
                .getMessage()
                .getPayload()
                .getValue();
    }

    public List<Map<String, Object>> execute(String query) throws Exception {
        return (List<Map<String, Object>>) flowRunner("executeFlow")
                .withPayload(format(QUERY_RETURN_A_NODE, query))
                .withVariable("parameters", null)
                .run()
                .getMessage()
                .getPayload()
                .getValue();
    }

    public void createNode(String label, Map<String, Object> parameters) throws Exception {
        flowRunner("createNodeFlow")
                .withPayload(label)
                .withVariable("parameters", parameters)
                .run();
    }

    public List<Map<String, Object>> selectNodes(String label, Map<String, Object> parameters) throws Exception {
        return (List<Map<String, Object>>) flowRunner("selectNodesFlow")
                .withPayload(label)
                .withVariable("parameters", parameters)
                .run()
                .getMessage()
                .getPayload()
                .getValue();
    }

    public void updateNodes(String label, Map<String, Object> parameters, Map<String, Object> setParameters) throws Exception {
        flowRunner("updateNodesFlow")
                .withPayload(label)
                .withVariable("parameters", parameters)
                .withVariable("setParameters", setParameters)
                .run();
    }

    public void deleteNodes(String label, boolean removeRelationships, Map<String, Object> parameters) throws Exception {
        flowRunner("deleteNodesFlow")
                .withPayload(label)
                .withVariable("removeRelationships", removeRelationships)
                .withVariable("parameters", parameters)
                .run();
    }

}
