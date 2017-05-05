package org.mule.modules.neo4j.automation.functional;

import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public class WriteWithParametersIT extends AbstractTestCases {

    @Test
    public void writeWithParametersTest() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name","Guillermo Francella");
        parameters.put("born","1955");
        List<Map<String, Object>> result = getConnector().writeWithParameters("CREATE (a:Person {name: {name}, born: {born}})",parameters);
    }

    @After
    public void tearDown() {
        getConnector().write("MATCH (a:Person {name:\"Guillermo Francella\"}) DELETE a");
    }
}
