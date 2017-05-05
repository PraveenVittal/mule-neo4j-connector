package org.mule.modules.neo4j.automation.functional;

import org.mule.modules.neo4j.Neo4jConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

/**
 * Created by esandoval on 5/3/17.
 */
public class AbstractTestCases extends AbstractTestCase<Neo4jConnector> {

    public AbstractTestCases() {
        super(Neo4jConnector.class);
    }
}
