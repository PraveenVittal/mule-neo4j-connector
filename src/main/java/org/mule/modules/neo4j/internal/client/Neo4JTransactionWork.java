package org.mule.modules.neo4j.internal.client;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neo4JTransactionWork implements TransactionWork<List<Map<String, Object>>> {

    private final String query;
    private final Map<String, Object> parameters;

    public Neo4JTransactionWork(String query, Map<String, Object> parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    @Override
    public List<Map<String, Object>> execute(Transaction transaction) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Record record : transaction.run(query, parameters).list()) {
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

}
