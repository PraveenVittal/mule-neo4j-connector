/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal;

import java.util.List;
import java.util.Map;

public interface Neo4JClient {

    public void connect(Map<String, Object> map) throws Exception;

    public List<Map<String, Object>> read(String query);

    public List<Map<String, Object>> read(String query, Map<String, Object> parameters);

    public void write(String query);

    public void write(String query, Map<String, Object> parameters);

    public void close();
}
