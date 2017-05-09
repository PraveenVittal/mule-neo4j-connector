/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal;

import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Neo4JClientImpl implements Neo4JClient {

    private Driver neo4jClient;
    private Session session;

    public void connect(Map<String, Object> map) {

        neo4jClient = GraphDatabase.driver(String.class.cast(map.get("url")), AuthTokens.basic(String.class.cast(map.get("username")), String.class.cast(map.get("password"))));

        session = neo4jClient.session();
        session.readTransaction(new TransactionWork<Boolean>() {

            @Override public Boolean execute(Transaction tx) {
                tx.run("MATCH (a) RETURN a LIMIT 1");
                return true;
            }
        });
    }

    @Override public List<Map<String, Object>> read(final String query) {
        return session.readTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override public List<Map<String, Object>> execute(Transaction tx) {
                return Neo4JClientImpl.this.runTransaction(tx, query);
            }
        });
    }

    @Override public List<Map<String, Object>> read(final String query, final Map<String, Object> parameters) {
        return session.readTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override public List<Map<String, Object>> execute(Transaction tx) {
                return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
            }
        });
    }

    @Override public void write(final String query) {
        session.writeTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override public List<Map<String, Object>> execute(Transaction tx) {
                return Neo4JClientImpl.this.runTransaction(tx, query);
            }
        });
    }

    @Override public void write(final String query, final Map<String, Object> parameters) {
        session.writeTransaction(new TransactionWork<List<Map<String, Object>>>() {

            @Override public List<Map<String, Object>> execute(Transaction tx) {
                return Neo4JClientImpl.this.runTransaction(tx, query, parameters);
            }
        });
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

    @Override public void close() {
        session.close();
        neo4jClient.close();
    }
}
