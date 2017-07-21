package org.mule.modules.neo4j.internal.exception;

import com.google.common.collect.ImmutableSet;
import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.CONNECTIVITY;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.INCORRECT_CREDENTIALS;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.INVALID_SESSION;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.REQUEST_FAILED;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN_HOST;

public class Neo4jErrorTypeProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        return ImmutableSet.<ErrorTypeDefinition>builder()
                .add(CONNECTIVITY)
                .add(REQUEST_FAILED)
                .add(INCORRECT_CREDENTIALS)
                .add(UNKNOWN_HOST)
                .add(INVALID_SESSION)
                .add(UNKNOWN)
                .build();
    }

}
