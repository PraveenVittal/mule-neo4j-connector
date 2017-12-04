package org.mule.modules.neo4j.internal.operation;

import org.mule.connectors.atlantic.commons.builder.config.exception.DefinedExceptionHandler;
import org.mule.connectors.atlantic.commons.builder.execution.ExecutionBuilder;
import org.mule.modules.neo4j.internal.client.Neo4jService;
import org.mule.modules.neo4j.internal.config.Neo4jConfig;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
import org.mule.connectors.commons.template.operation.ConnectorOperations;
import org.mule.connectors.atlantic.commons.builder.lambda.function.BiFunction;
import org.mule.modules.neo4j.internal.exception.Neo4jErrors;
import org.mule.modules.neo4j.internal.exception.Neo4jException;
import org.mule.modules.neo4j.internal.exception.Neo4jExceptionHandler;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.neo4j.driver.v1.exceptions.AuthenticationException;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.AUTHENTICATION_ERROR;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN_HOST;


public class Neo4jOperationsError <SERVICE extends Neo4jService> extends ConnectorOperations<Neo4jConfig,Neo4jConnection, SERVICE> {

    public Neo4jOperationsError(BiFunction<Neo4jConfig, Neo4jConnection, SERVICE> serviceConstructor) {
        super(serviceConstructor);
    }

    private <T extends Throwable> DefinedExceptionHandler<T> handle(Class<T> exceptionClass, Neo4jErrors errorCode) {
        return new DefinedExceptionHandler<>(exceptionClass, exception -> {
            throw new ModuleException(errorCode, exception);
        });
    }

    protected ExecutionBuilder<SERVICE> newExecutionBuilder(Neo4jConfig config, Neo4jConnection connection) {
        return super.newExecutionBuilder(config, connection)
                .withExceptionHandler(handle(AuthenticationException.class, AUTHENTICATION_ERROR))
                .withExceptionHandler(Neo4jException.class, exception -> new ModuleException(exception.getErrorCode(), exception));
    }




}
