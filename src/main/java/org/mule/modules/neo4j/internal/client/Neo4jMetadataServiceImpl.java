/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.client;

import com.mule.connectors.commons.rest.builder.RequestBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class Neo4jMetadataServiceImpl implements Neo4jMetadataService {

    private final Client client = ClientBuilder.newClient();
    private String username;
    private String password;
    private String endpointUrl;

    public Neo4jMetadataServiceImpl(Map<String, Object> metadataInfoConnection) {
        this.username = String.class.cast(metadataInfoConnection.get("username"));
        this.password = String.class.cast(metadataInfoConnection.get("password"));
        this.endpointUrl = String.class.cast(metadataInfoConnection.get("restUrl"));
    }

    @Override
    public List<String> getLabels() {
        return RequestBuilder.<List<String>>get(client, format("%s/db/data/labels", endpointUrl))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .responseType(List.class, String.class)
                .basicAuthorization(username, password)
                .execute();
    }

    @Override
    public Set<String> getConstraintProperties(String label) {
        Set<String> result = newHashSet();
        for (Map<String, Object> obj : RequestBuilder.<List<Map<String, Object>>>get(client, format("%s/db/data/schema/constraint/%s", endpointUrl, label))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .responseType(List.class, Map.class)
                .basicAuthorization(username, password)
                .execute()) {
            List<String> str = List.class.cast(obj.get("property_keys"));
            result.addAll(str);
        }
        return result;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
}
