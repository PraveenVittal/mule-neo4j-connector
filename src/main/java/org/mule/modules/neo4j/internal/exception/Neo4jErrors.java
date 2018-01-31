package org.mule.modules.neo4j.internal.exception;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum Neo4jErrors implements ErrorTypeDefinition<Neo4jErrors> {
	CONNECTIVITY(MuleErrors.CONNECTIVITY), REQUEST_FAILED(CONNECTIVITY), INCORRECT_CREDENTIALS(
			CONNECTIVITY), UNKNOWN_HOST(CONNECTIVITY), INVALID_SESSION(CONNECTIVITY), CLIENT_ERROR(
					CONNECTIVITY), AUTHENTICATION_ERROR(CONNECTIVITY), DATABASE_EXCEPTION(REQUEST_FAILED), EXCEPTION(
							REQUEST_FAILED), NO_SUCH_RECORD_EXCEPTION(REQUEST_FAILED), PROTOCOL_EXCEPTION(
									CONNECTIVITY), SECURITY_EXCEPTION(CONNECTIVITY), SERVICE_UNAVAILABLE_EXCEPTION(
											CONNECTIVITY), SESSION_EXPIRED_EXCEPTION(CONNECTIVITY), TRANSIENT_EXCEPTION(
													CONNECTIVITY), UNKNOWN(CONNECTIVITY);

	private ErrorTypeDefinition<?> parentErrorType;

	@SuppressWarnings("rawtypes")
	Neo4jErrors(ErrorTypeDefinition parentErrorType) {
		this.parentErrorType = parentErrorType;
	}

	@Override
	public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
		return ofNullable(parentErrorType);
	}
}
