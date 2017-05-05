package org.mule.modules.neo4j.automation.functional;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public class ReadIT extends AbstractTestCases {

    @Test
    public void readTest(){
        List<Map<String,Object>> result = getConnector().read("MATCH (tom {name: \"Tom Hanks\"}) RETURN tom");
    }
}
