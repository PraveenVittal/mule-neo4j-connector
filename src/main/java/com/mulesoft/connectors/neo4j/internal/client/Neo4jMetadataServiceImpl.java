/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.neo4j.internal.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.neo4j.internal.exception.Neo4JMetadataException;
import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.auth.HttpAuthentication;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;
import static org.mule.runtime.http.api.HttpConstants.Method.GET;
import static org.mule.runtime.http.api.HttpHeaders.Names.ACCEPT;
import static org.mule.runtime.http.api.HttpHeaders.Names.CONTENT_TYPE;

public class Neo4jMetadataServiceImpl implements Neo4jMetadataService {

	private String username;
	private String password;
	private String restUrl;
	private HttpClient httpClient;

	public Neo4jMetadataServiceImpl(String restUrl, String username, String password, HttpClient httpClient) {
		this.restUrl = restUrl;
		this.username = username;
		this.password = password;
		this.httpClient = httpClient;
	}

	@Override
	public List<String> getLabels() {
		try {
			HttpRequest request = HttpRequest.builder()
				.method(GET)
				.uri(format("%s/db/data/labels", restUrl))
				.addHeader(ACCEPT, APPLICATION_JSON)
				.addHeader(CONTENT_TYPE, APPLICATION_JSON)
				.build();
			return new ObjectMapper().readValue(IOUtils.toString(
					httpClient.send(request, 30000 /* response timeout */, true,
							HttpAuthentication.basic(username, password).build()).getEntity().getContent()
			), new TypeReference<List<String>>() {});
		} catch (IOException | TimeoutException e) {
			throw new Neo4JMetadataException(e);
		}
	}

	@Override
	public Set<String> getConstraintProperties(String label) {
		try {
			Set<String> result = new HashSet<>();
			List<Map<String,Object>> list = new ObjectMapper().readValue(IOUtils.toString(
                    httpClient.send(HttpRequest.builder()
                            .method(GET)
                            .uri(format("%s/db/data/schema/constraint/%s", restUrl, label))
                            .addHeader(ACCEPT, APPLICATION_JSON)
                            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                            .build()
                            , 30000 /* response timeout */, true,
                            HttpAuthentication.basic(username, password).build()).getEntity().getContent()
            ), new TypeReference<List<Map<String,Object>>>() {});

			for (Map<String, Object> obj : list){
				List<String> str = List.class.cast(obj.get("property_keys"));
				result.addAll(str);
			}
			return result;
		} catch (IOException | TimeoutException e) {
			throw new Neo4JMetadataException(e);
		}
	}
}
