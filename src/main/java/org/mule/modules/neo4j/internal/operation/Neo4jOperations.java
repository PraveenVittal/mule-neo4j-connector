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
import org.mule.runtime.extension.api.exception.ModuleException;
import org.neo4j.driver.v1.exceptions.*;
import org.neo4j.driver.v1.exceptions.SecurityException;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.*;


public class Neo4jOperations extends ConnectorOperations<Neo4jConfig, Neo4jConnection, Neo4jService>{



    public Neo4jOperations(BiFunction<Neo4jConfig, Neo4jConnection, Neo4jService> serviceConstructor) {
        super(  serviceConstructor );
    }

    private <T extends Throwable> DefinedExceptionHandler<T> handle(Class<T> exceptionClass, Neo4jErrors errorCode) {
        return new DefinedExceptionHandler<>(exceptionClass, exception -> {
            throw new ModuleException(errorCode, exception);
        });
    }

    @Override
    protected ExecutionBuilder<Neo4jService> newExecutionBuilder(Neo4jConfig config, Neo4jConnection connection) {
        return super.newExecutionBuilder(config, connection)
                .withExceptionHandler(handle(AuthenticationException.class, AUTHENTICATION_ERROR))
                .withExceptionHandler(handle(ClientException.class,CLIENT_ERROR))
                .withExceptionHandler(handle(DatabaseException.class, DATABASE_EXCEPTION))
                .withExceptionHandler(handle(Neo4jException.class, EXCEPTION))
                .withExceptionHandler(handle(NoSuchRecordException.class, NO_SUCH_RECORD_EXCEPTION))
                .withExceptionHandler(handle(ProtocolException.class,PROTOCOL_EXCEPTION))
                .withExceptionHandler(handle(SecurityException.class, SECURITY_EXCEPTION))
                .withExceptionHandler(handle(ServiceUnavailableException.class, SERVICE_UNAVAILABLE_EXCEPTION))
                .withExceptionHandler(handle(SessionExpiredException.class, SESSION_EXPIRED_EXCEPTION))
                .withExceptionHandler(handle(TransientException.class, TRANSIENT_EXCEPTION))
                .withExceptionHandler(Neo4jException.class, exception -> new ModuleException(exception.getErrorCode(), exception));
    }




}
