package org.mule.modules.neo4j.internal.connection;

import static org.mule.runtime.api.connection.ConnectionValidationResult.failure;
import static org.mule.runtime.api.connection.ConnectionValidationResult.success;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;

public abstract class Neo4jConnectionProvider implements ConnectionProvider<Neo4jConnection> {

	@Override
	public void disconnect(Neo4jConnection connection) {
		connection.disconnect();
	}

	@Override
	public ConnectionValidationResult validate(Neo4jConnection connection) {
		try {
			connection.validate();
			return success();
		} catch (Exception e) {
			return failure(e.getMessage(), e);
		}
	}
}
