/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.neo4j.internal.connection;

import com.mulesoft.connectors.neo4j.internal.service.Neo4jMetadataService;
import com.mulesoft.connectors.neo4j.internal.service.Neo4jMetadataServiceImpl;
import org.mule.connectors.commons.template.connection.ConnectorConnection;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import java.util.UUID;

import static org.neo4j.driver.v1.AuthTokens.basic;
import static org.neo4j.driver.v1.GraphDatabase.driver;

public class Neo4jConnection implements ConnectorConnection {

    private final Driver client;
    private final Session session;
    private final Neo4jMetadataService metadataService;
    private HttpClient httpClient;

    public Neo4jConnection(String username, String password, String boltUrl, String restUrl, HttpService httpService) {
        client = driver(boltUrl, basic(username, password));
        session = client.session();
        httpClient = httpService.getClientFactory().create(new HttpClientConfiguration.Builder().setName("Neo4JMetadata").build());
        httpClient.start();
        metadataService = new Neo4jMetadataServiceImpl(restUrl, username, password, httpClient);
    }

    @Override
    public void disconnect() {
        httpClient.stop();
    }

    @Override
    public void validate() {
        session.run("MATCH (a) RETURN a LIMIT 1");
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }

    public Session getSession() {
        return session;
    }

    public Neo4jMetadataService getMetadataService() {
        return metadataService;
    }
}
