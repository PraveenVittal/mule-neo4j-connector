package org.mule.modules.neo4j.automation.functional;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public class ReadWithParametersIT extends AbstractTestCases {

    @Test
    public void readWithParametersTest(){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name","Tom Hanks");
        List<Map<String,Object>> result = getConnector().readWithParameters("MATCH (a:Person) WHERE a.name = {name} RETURN a", parameters);
    }
}
