package com.mulesoft.connectors.neo4j.internal.connection.basic;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

public class AdditionalConnectionParams {

	@Parameter
	@DisplayName("BOLT URL")
	private String boltUrl;

	@Parameter
	@DisplayName("REST URL")
	private String restUrl;

	public String getBoltUrl() {
		return boltUrl;
	}

	public void setBoltUrl(String boltUrl) {
		this.boltUrl = boltUrl;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

}
