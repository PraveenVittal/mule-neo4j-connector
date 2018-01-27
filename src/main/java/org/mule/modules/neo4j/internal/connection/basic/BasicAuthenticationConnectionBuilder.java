/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.connection.basic;

import org.mule.modules.neo4j.internal.connection.ConnectionBuilder;

public class BasicAuthenticationConnectionBuilder implements ConnectionBuilder<BasicAuthenticationConnection> {

	private String username;
	private String password;
	private String restUrl;
	private String boltUrl;

	public BasicAuthenticationConnectionBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

	public BasicAuthenticationConnectionBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public BasicAuthenticationConnectionBuilder withBoltUrl(String boltUrl) {
		this.boltUrl = boltUrl;
		return this;
	}

	public BasicAuthenticationConnectionBuilder withRestUrl(String restUrl) {
		this.restUrl = restUrl;
		return this;
	}

	@Override
	public BasicAuthenticationConnection create() {
		return new BasicAuthenticationConnection(username, password, boltUrl, restUrl);
	}

}
