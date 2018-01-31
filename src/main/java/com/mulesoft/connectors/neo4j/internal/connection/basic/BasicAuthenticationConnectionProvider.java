package com.mulesoft.connectors.neo4j.internal.connection.basic;

import com.mulesoft.connectors.neo4j.internal.connection.Neo4jConnection;
import com.mulesoft.connectors.neo4j.internal.connection.Neo4jConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;

import javax.inject.Inject;

public class BasicAuthenticationConnectionProvider extends Neo4jConnectionProvider {

	@Inject HttpService httpService;

	@Parameter
	@Placement(order = 1)
	private String username;

	@Parameter
	@Password
	@Placement(order = 2)
	private String password;

	@ParameterGroup(name = "Endpoint Settings")
	private AdditionalConnectionParams additionalConnectionParams;

	@Override
	public Neo4jConnection connect() throws ConnectionException {
		return new BasicAuthenticationConnectionBuilder().withUsername(username).withPassword(password)
				.withBoltUrl(additionalConnectionParams.getBoltUrl())
				.withRestUrl(additionalConnectionParams.getRestUrl())
				.withHttpService(httpService).create();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AdditionalConnectionParams getAdditionalConnectionParams() {
		return additionalConnectionParams;
	}

	public void setAdditionalConnectionParams(AdditionalConnectionParams additionalConnectionParams) {
		this.additionalConnectionParams = additionalConnectionParams;
	}
}
