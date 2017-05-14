/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.MapUtils;
import org.mule.modules.neo4j.exception.Neo4JConnectorException;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.internal.value.InternalValue;
import org.neo4j.driver.v1.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.join;

public class Neo4JClientImpl implements Neo4JClient {

    private Driver neo4jClient;
    private Session session;
    private List<Class<?>> internals = ImmutableList.<Class<?>>builder().add(InternalNode.class).add(InternalRelationship.class).add(InternalPath.class).build();

    public void connect(Map<String, Object> map) {
        neo4jClient = GraphDatabase.driver(String.class.cast(map.get("url")), AuthTokens.basic(String.class.cast(map.get("username")), String.class.cast(map.get("password"))));

        session = neo4jClient.session();
        session.readTransaction(new TransactionWork<Boolean>() {

            @Override
            public Boolean execute(Transaction tx) {
                tx.run("MATCH (a) RETURN a LIMIT 1");
                return true;
            }
        });
    }

    @Override
    public List<Map<String, Object>> read(final String query, final Map<String, Object> parameters) {
        return session.readTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override
            public List<Map<String, Object>> execute(Transaction tx) {
                try {
                    return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new Neo4JConnectorException(e);
                }
            }
        });
    }

    @Override
    public void write(final String query, final Map<String, Object> parameters) {
        session.writeTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override
            public List<Map<String, Object>> execute(Transaction tx) {
                try {
                    return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new Neo4JConnectorException(e);
                }
            }
        });
    }

    @Override
    public void createNodes(List<Map<String, Object>> parameters, List<String> labels) {
        Map<String, Object> props = ImmutableMap.<String, Object>builder().put("props", parameters).build();
        write(format("UNWIND $props AS map CREATE (n%s) SET n = map", concatLabels(labels)), props);
    }

    @Override
    public void createRelationBetweenNodes(List<String> labelsA, List<String> labelsB, String condition, String labelR, Map<String, Object> relProps) {
        String propsMap = "";
        Map<String, Object> props = null;

        if (!MapUtils.isEmpty(relProps)) {
            propsMap = "$props";
            props = ImmutableMap.<String, Object>builder().put("props", relProps).build();
        }

        write(format("MATCH (a%s),(b%s) WHERE %s CREATE (a)-[r:%s %s]->(b) RETURN r", concatLabels(labelsA), concatLabels(labelsB), condition, labelR, propsMap), props);
    }

    private List<Map<String, Object>> runTransaction(Transaction tx, String query, Map<String, Object> parameters)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Map<String, Object>> result = new ArrayList<>();
        StatementResult stResults = tx.run(query, parameters);
        while (stResults.hasNext()) {
            Map<String, Object> map = stResults.next().asMap();
            Map<String, Object> resultMap = new HashMap<>();

            for (String key : map.keySet()) {
                resultMap.put(key, convertInternalsToObject(map.get(key)));
            }

            result.add(resultMap);
        }
        return result;
    }

    private Object convertInternalsToObject(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (!internals.contains(obj.getClass())) {
            return obj;
        } else {
            Method asValue = obj.getClass().getMethod("asValue", null);
            Map<String, Object> map = InternalValue.class.cast(asValue.invoke(obj, null)).asMap();
            Map<String, Object> result = new HashMap<>();

            for (String key : map.keySet()) {
                result.put(key, convertInternalsToObject(map.get(key)));
            }

            return result;
        }
    }

    private String concatLabels(List<String> labels) {
        if (!isEmpty(labels)) {
            return format(":%s", join(labels, ":"));
        } else {
            return "";
        }
    }

    @Override
    public void close() {
        session.close();
        neo4jClient.close();
    }
}
