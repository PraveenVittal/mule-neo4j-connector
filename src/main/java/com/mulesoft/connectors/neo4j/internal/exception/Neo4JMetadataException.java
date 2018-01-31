package com.mulesoft.connectors.neo4j.internal.exception;

public class Neo4JMetadataException extends Neo4jException {

    @Override public Neo4jErrors getErrorCode() {
        return Neo4jErrors.REQUEST_FAILED;
    }

    public Neo4JMetadataException(Throwable cause) {
        super(cause);
    }

}
