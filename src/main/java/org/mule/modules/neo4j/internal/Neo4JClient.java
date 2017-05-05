package org.mule.modules.neo4j.internal;

import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public interface Neo4JClient {

    public void connect(Map<String,Object> map) throws Exception;

    public List<Map<String,Object>> read(String query);

    public List<Map<String,Object>> read(String query,Map<String,Object> parameters);

    public List<Map<String,Object>> write(String query);

    public List<Map<String,Object>> write(String query,Map<String,Object> parameters);

    public void close();
}
