package org.mule.modules.neo4j.internal.exception;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum Neo4JErrors implements ErrorTypeDefinition<Neo4JErrors> {
    CONNECTIVITY(MuleErrors.CONNECTIVITY),
    REQUEST_FAILED(CONNECTIVITY),
    INCORRECT_CREDENTIALS(CONNECTIVITY),
    UNKNOWN_HOST(CONNECTIVITY),
    INVALID_SESSION(CONNECTIVITY),
    UNKNOWN(CONNECTIVITY);

    private ErrorTypeDefinition<?> parentErrorType;

    @SuppressWarnings("rawtypes")
    Neo4JErrors(ErrorTypeDefinition parentErrorType) {
        this.parentErrorType = parentErrorType;
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return ofNullable(parentErrorType);
    }
}
