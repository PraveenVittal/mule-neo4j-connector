package org.mule.modules.neo4j.internal.exception;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.REQUEST_FAILED;

public class Neo4JMetadataException extends Neo4jException {

    @Override public Neo4jErrors getErrorCode() {
        return REQUEST_FAILED;
    }

    public Neo4JMetadataException(Throwable cause) {
        super(cause);
    }

}
