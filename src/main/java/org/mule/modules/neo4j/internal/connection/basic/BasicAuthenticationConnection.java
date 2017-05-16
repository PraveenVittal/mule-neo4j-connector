package org.mule.modules.neo4j.internal.connection.basic;

import org.mule.modules.neo4j.internal.connection.Neo4JConnection;
import org.mule.modules.neo4j.internal.connection.ValidationTransactionWork;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.io.IOException;
import java.util.UUID;

public class BasicAuthenticationConnection implements Neo4JConnection {

    private final Driver neo4jClient;
    private final Session session;

    public BasicAuthenticationConnection(String url, String username, String password) {
        neo4jClient = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        session = neo4jClient.session();
    }

    @Override
    public void validate() {
        session.readTransaction(new ValidationTransactionWork());
    }

    @Override
    public void close() throws IOException {
        try {
            session.close();
        } finally {
            neo4jClient.close();
        }
    }

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Session getSession() {
        return session;
    }
}
