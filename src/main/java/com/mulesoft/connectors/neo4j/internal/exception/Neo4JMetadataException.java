package com.mulesoft.connectors.neo4j.internal.exception;

public class Neo4JMetadataException extends Neo4jException {

    public Neo4JMetadataException(Throwable cause) {
        super(cause);
    }

    @Override
    public Neo4jErrors getErrorCode() {
        return Neo4jErrors.REQUEST_FAILED;
    }

}
