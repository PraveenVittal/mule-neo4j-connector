package org.mule.modules.neo4j.internal.connection;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.core.api.routing.ValidationException;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.mule.runtime.api.connection.ConnectionValidationResult.failure;
import static org.mule.runtime.api.connection.ConnectionValidationResult.success;

public abstract class Neo4jConnectionProvider implements ConnectionProvider<Neo4jConnection> {

    @Override
    public void disconnect(Neo4jConnection connection) {
        closeQuietly(connection);
    }

    @Override
    public ConnectionValidationResult validate(Neo4jConnection connection) {
        try {
            connection.validate();
            return success();
        } catch (ValidationException e) {
            return failure(e.getMessage(), e);
        }
    }
}
