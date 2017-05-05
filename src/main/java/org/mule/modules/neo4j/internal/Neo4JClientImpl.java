package org.mule.modules.neo4j.internal;

import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by esandoval on 5/3/17.
 */
public class Neo4JClientImpl implements Neo4JClient {

    private Driver neo4jClient;

    public void connect(Map<String, Object> map) throws Exception {
        neo4jClient = GraphDatabase.driver(
                String.class.cast(map.get("url")),
                AuthTokens.basic(
                        String.class.cast(map.get("username")),
                        String.class.cast(map.get("password"))
                )
        );
    }

    @Override
    public List<Map<String, Object>> read(final String query) {
        try (Session session = getNeo4jClient().session()) {
            return session.readTransaction(new TransactionWork<List<Map<String, Object>>>() {
                @Override
                public List<Map<String, Object>> execute(Transaction tx) {
                    return Neo4JClientImpl.this.runTransaction(tx, query);
                }
            });
        }
    }

    @Override
    public List<Map<String, Object>> read(final String query, final Map<String, Object> parameters) {
        try (Session session = getNeo4jClient().session()) {
            return session.readTransaction(new TransactionWork<List<Map<String, Object>>>() {
                @Override
                public List<Map<String, Object>> execute(Transaction tx) {
                    return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
                }
            });
        }
    }

    @Override
    public List<Map<String, Object>> write(final String query) {
        try (Session session = getNeo4jClient().session()) {
            return session.writeTransaction(new TransactionWork<List<Map<String, Object>>>() {
                @Override
                public List<Map<String, Object>> execute(Transaction tx) {
                    return Neo4JClientImpl.this.runTransaction(tx, query);
                }
            });
        }
    }

    @Override
    public List<Map<String, Object>> write(final String query, final Map<String, Object> parameters) {
        try (Session session = getNeo4jClient().session()) {
            return session.writeTransaction(new TransactionWork<List<Map<String, Object>>>() {
                @Override
                public List<Map<String, Object>> execute(Transaction tx) {
                    return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
                }
            });
        }
    }

    private List<Map<String, Object>> runTransaction(Transaction tx, String query) {
        List<Map<String, Object>> result = new ArrayList<>();
        StatementResult stResults = tx.run(query);
        while (stResults.hasNext()) {
            result.add(stResults.next().asMap());
        }
        return result;
    }

    private List<Map<String, Object>> runTransaction(Transaction tx, String query, Map<String, Object> parameters) {
        List<Map<String, Object>> result = new ArrayList<>();
        StatementResult stResults = tx.run(query, parameters);
        while (stResults.hasNext()) {
            result.add(stResults.next().asMap());
        }
        return result;
    }

    @Override
    public void close() {
        getNeo4jClient().close();
    }

    public Driver getNeo4jClient() {
        return neo4jClient;
    }

    public void setNeo4jClient(Driver neo4jClient) {
        this.neo4jClient = neo4jClient;
    }
}
