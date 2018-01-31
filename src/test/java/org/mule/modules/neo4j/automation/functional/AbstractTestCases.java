package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_DELETE_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.QUERY_RETURN_A_NODE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TEST_LABEL2;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.toList;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.runtime.api.streaming.bytes.CursorStreamProvider;

public abstract class AbstractTestCases extends MuleArtifactFunctionalTestCase {

	protected static final String FLOW_CONFIG_LOCATION = "src/test/resources/automation-test-flows.xml";

	@Override
	public int getTestTimeoutSecs() {
		return 999999;
	}

	@Override
	protected String[] getConfigFiles() {
		return new String[] { FLOW_CONFIG_LOCATION };
	}

	@Before
	public void setUp() throws Exception {
		cleanUpDB();
	}

	@After
	public void tearDown() throws Exception {
		cleanUpDB();
	}

	protected void cleanUpDB() throws Exception {
		execute(format(QUERY_DELETE_A_NODE, TEST_LABEL), null);
		execute(format(QUERY_DELETE_A_NODE, TEST_LABEL2), null);
	}

	public InputStream execute(String query, InputStream parameters) throws Exception {
		return ((CursorStreamProvider) flowRunner("executeFlow").withPayload(query)
				.withVariable("parameters", parameters).keepStreamsOpen().run().getMessage().getPayload().getValue())
						.openCursor();
	}

	public InputStream execute(String query) throws Exception {
		return ((CursorStreamProvider) flowRunner("executeFlow").withPayload(format(QUERY_RETURN_A_NODE, query))
				.withVariable("parameters", null).keepStreamsOpen().run().getMessage().getPayload().getValue())
						.openCursor();
	}

	public void createNode(String label, InputStream parameters) throws Exception {
		flowRunner("createNodeFlow").withVariable("label", label).withPayload(parameters).run();
	}

	public InputStream selectNodes(String label, InputStream parameters) throws Exception {
		return ((CursorStreamProvider) flowRunner("selectNodesFlow").withVariable("label", label)
				.withVariable("parameters", parameters).keepStreamsOpen().run().getMessage().getPayload().getValue())
						.openCursor();
	}

	public void updateNodes(String label, InputStream parameters, InputStream setParameters) throws Exception {
		flowRunner("updateNodesFlow").withPayload(parameters).withVariable("label", label)
				.withVariable("setParameters", setParameters).run();
	}

	public void deleteNodes(String label, boolean removeRelationships, InputStream parameters) throws Exception {
		flowRunner("deleteNodesFlow").withVariable("label", label)
				.withVariable("removeRelationships", removeRelationships).withVariable("parameters", parameters).run();
	}

	protected static String inputStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}

	protected String getMapKeyValue(String test, String key) throws Exception {
		return Map.class.cast(toList(execute(test)).get(0).get("a")).get(key).toString();
	}

	protected String getMapQueryValue(String query, String test, String params, String key) throws Exception {
		return Map.class.cast(toList(execute(format(query, test, params), null)).get(0).get("a")).get(key).toString();
	}

	protected String getMapKeyValueWihtParams(String test, InputStream params, String key) throws Exception {
		return Map.class.cast(toList(execute(test, params)).get(0).get("a")).get(key).toString();
	}

	protected String getSelectMapKeyValue(String test, InputStream params, String key) throws Exception {
		return Map.class.cast(toList(selectNodes(test, params)).get(0).get("a")).get(key).toString();
	}

}