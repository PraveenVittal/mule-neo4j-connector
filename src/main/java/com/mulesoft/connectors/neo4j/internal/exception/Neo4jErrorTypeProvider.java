package com.mulesoft.connectors.neo4j.internal.exception;

import com.google.common.collect.ImmutableSet;
import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

public class Neo4jErrorTypeProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        return ImmutableSet.<ErrorTypeDefinition>builder().add(Neo4jErrors.CONNECTIVITY).add(Neo4jErrors.REQUEST_FAILED)
                .add(Neo4jErrors.INCORRECT_CREDENTIALS).add(Neo4jErrors.UNKNOWN_HOST).add(Neo4jErrors.INVALID_SESSION).add(Neo4jErrors.UNKNOWN).build();
    }

}
