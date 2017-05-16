/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.client;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.MapUtils;
import org.mule.modules.neo4j.internal.connection.Neo4JConnection;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.join;

public class Neo4JClientImpl implements Neo4JClient {

    private final Neo4JConnection connection;

    public Neo4JClientImpl(Neo4JConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<Map<String, Object>> execute(String cqlStatement, Map<String, Object> parameters) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Record record : connection.getSession().run(cqlStatement, parameters).list()) {
            Map<String, Object> resultMap = new HashMap<>();
            for (Pair<String, Value> pair : record.fields()) {
                resultMap.put(pair.key(), convert(pair.value()));
            }
            result.add(resultMap);
        }
        return result;
    }

    private Object convert(Value value) {
        Map<String, Object> result = new HashMap<>();
        for (String key : value.keys()) {
            result.put(key, convert(value.get(key)));
        }
        return result.isEmpty() ? value.asObject() : result;
    }

    @Override
    public void createNodes(List<Map<String, Object>> parameters, List<String> labels) {
        execute(format("UNWIND $props AS map CREATE (n%s) SET n = map", concatLabels(labels)), ImmutableMap.<String, Object>builder().put("props", parameters).build());
    }

    @Override
    public void createRelationBetweenNodes(List<String> labelsA, List<String> labelsB, String condition, String labelR, Map<String, Object> relProps) {
        String propsMap = "";
        Map<String, Object> props = null;

        if (!MapUtils.isEmpty(relProps)) {
            propsMap = "$props";
            props = ImmutableMap.<String, Object>builder().put("props", relProps).build();
        }

        execute(format("MATCH (a%s),(b%s) WHERE %s CREATE (a)-[r:%s %s]->(b) RETURN r", concatLabels(labelsA), concatLabels(labelsB), condition, labelR, propsMap), props);
    }

    private String concatLabels(List<String> labels) {
        if (!isEmpty(labels)) {
            return format(":%s", join(labels, ":"));
        } else {
            return "";
        }
    }
}
