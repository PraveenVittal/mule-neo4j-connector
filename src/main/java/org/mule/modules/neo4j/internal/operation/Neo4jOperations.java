package org.mule.modules.neo4j.internal.operation;

import org.mule.connectors.atlantic.commons.builder.config.exception.DefinedExceptionHandler;
import org.mule.connectors.atlantic.commons.builder.execution.ExecutionBuilder;
import org.mule.connectors.commons.template.operation.ConnectorOperations;
import org.mule.modules.neo4j.internal.client.Neo4jService;
import org.mule.modules.neo4j.internal.client.Neo4jServiceImpl;
import org.mule.modules.neo4j.internal.config.Neo4jConfig;
import org.mule.modules.neo4j.internal.connection.Neo4jConnection;
import org.mule.modules.neo4j.internal.exception.Neo4jErrors;
import org.mule.modules.neo4j.internal.exception.Neo4jException;
import org.mule.modules.neo4j.internal.metadata.NodeMetadataResolver;
import org.mule.modules.neo4j.internal.util.ConverterUtils;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.neo4j.driver.v1.exceptions.ProtocolException;
import org.neo4j.driver.v1.exceptions.SecurityException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;
import org.neo4j.driver.v1.exceptions.SessionExpiredException;
import org.neo4j.driver.v1.exceptions.TransientException;

import java.io.IOException;
import java.io.InputStream;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.AUTHENTICATION_ERROR;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.CLIENT_ERROR;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.DATABASE_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.NO_SUCH_RECORD_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.PROTOCOL_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.SECURITY_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.SERVICE_UNAVAILABLE_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.SESSION_EXPIRED_EXCEPTION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.TRANSIENT_EXCEPTION;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

public class Neo4jOperations extends ConnectorOperations<Neo4jConfig, Neo4jConnection, Neo4jService> {

	public Neo4jOperations() {
		super(Neo4jServiceImpl::new);
	}

	public void createNode(@Config Neo4jConfig config, @Connection Neo4jConnection connection,
			@MetadataKeyId(NodeMetadataResolver.class) String label,
			@Optional @TypeResolver(NodeMetadataResolver.class) InputStream input) throws IOException {
		newExecutionBuilder(config, connection).execute(Neo4jService::createNode).withParam(label).withParam(input,
				ConverterUtils::toMap);
	}

	@OutputResolver(output = NodeMetadataResolver.class)
	@MediaType(APPLICATION_JSON)
	public InputStream execute(@Config Neo4jConfig config, @Connection Neo4jConnection connection,
			@Content  String query,
			@Optional  InputStream input) {
		return newExecutionBuilder(config, connection).execute(Neo4jService::execute, ConverterUtils::toJSONStream)
				.withParam(query).withParam(input, ConverterUtils::toMap);
	}

	@OutputResolver(output = NodeMetadataResolver.class)
	@MediaType(APPLICATION_JSON)
	public InputStream selectNodes(@Config Neo4jConfig config, @Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label,
			@TypeResolver(NodeMetadataResolver.class) @Optional InputStream input) {
		return newExecutionBuilder(config, connection).execute(Neo4jService::selectNodes, ConverterUtils::toJSONStream)
				.withParam(label).withParam(input, ConverterUtils::toMap);
	}

	public void updateNodes(@Config Neo4jConfig config, @Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label,
			@Optional @TypeResolver(NodeMetadataResolver.class) InputStream parameters, @Content InputStream setParameters) throws IOException {
		newExecutionBuilder(config, connection).execute(Neo4jService::updateNodes).withParam(label)
				.withParam(parameters, ConverterUtils::toMap).withParam(setParameters, ConverterUtils::toMap);
	}

	public void deleteNodes(@Config Neo4jConfig config, @Connection Neo4jConnection connection, @MetadataKeyId(NodeMetadataResolver.class) String label,
			boolean removeRelationships, @Optional @TypeResolver(NodeMetadataResolver.class) InputStream parameters) throws IOException {
		newExecutionBuilder(config, connection).execute(Neo4jService::deleteNodes).withParam(label)
				.withParam(removeRelationships).withParam(parameters, ConverterUtils::toMap);
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
				.withExceptionHandler(handle(ClientException.class, CLIENT_ERROR))
				.withExceptionHandler(handle(DatabaseException.class, DATABASE_EXCEPTION))
				.withExceptionHandler(handle(Neo4jException.class, EXCEPTION))
				.withExceptionHandler(handle(NoSuchRecordException.class, NO_SUCH_RECORD_EXCEPTION))
				.withExceptionHandler(handle(ProtocolException.class, PROTOCOL_EXCEPTION))
				.withExceptionHandler(handle(SecurityException.class, SECURITY_EXCEPTION))
				.withExceptionHandler(handle(ServiceUnavailableException.class, SERVICE_UNAVAILABLE_EXCEPTION))
				.withExceptionHandler(handle(SessionExpiredException.class, SESSION_EXPIRED_EXCEPTION))
				.withExceptionHandler(handle(TransientException.class, TRANSIENT_EXCEPTION)).withExceptionHandler(
						Neo4jException.class, exception -> new ModuleException(exception.getErrorCode(), exception));
	}
}
