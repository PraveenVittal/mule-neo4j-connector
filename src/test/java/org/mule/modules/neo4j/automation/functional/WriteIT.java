package org.mule.modules.neo4j.automation.functional;

import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public class WriteIT extends AbstractTestCases {

    @Test
    public void writeTest() {
        List<Map<String, Object>> result = getConnector().write("CREATE (francella:Person {name:'Guillermo Francella', born:1955})");
    }

    @After
    public void tearDown() {
        getConnector().write("MATCH (a:Person {name:\"Guillermo Francella\"}) DELETE a");
    }
}
