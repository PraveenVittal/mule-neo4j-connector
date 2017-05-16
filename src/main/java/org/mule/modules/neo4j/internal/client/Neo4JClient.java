/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.client;

import java.util.List;
import java.util.Map;

public interface Neo4JClient {

    List<Map<String, Object>> execute(String query, Map<String, Object> parameters);

    void createNodes(List<Map<String, Object>> parameters, List<String> labels);

    void createRelationBetweenNodes(List<String> labelsA, List<String> labelB, String condition, String labelR, Map<String, Object> relProps);
}
