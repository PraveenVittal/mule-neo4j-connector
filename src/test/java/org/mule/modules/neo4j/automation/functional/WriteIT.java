package org.mule.modules.neo4j.automation.functional;

import org.junit.After;
import org.junit.Test;

/**
 * Created by esandoval on 5/3/17.
 */
public class WriteIT extends AbstractTestCases {

    @Test
    public void writeTest() {
        getConnector().write("CREATE (francella:Person {name:'Guillermo Francella', born:1955})");
        System.out.println("hello world");
    }

    @After
    public void tearDown() {
        getConnector().write("MATCH (a:Person {name:\"Guillermo Francella\"}) DELETE a");
    }
}
