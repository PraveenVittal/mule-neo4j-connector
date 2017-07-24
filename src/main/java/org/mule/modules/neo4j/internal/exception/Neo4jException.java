/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.exception;

public abstract class Neo4jException extends RuntimeException {

    public Neo4jException() {
        super();
    }

    public Neo4jException(Throwable cause) {
        super(cause);
    }

    public Neo4jException(String message) {
        super(message);
    }

    public Neo4jException(Throwable cause, String message) {
        super(message, cause);
    }

    protected abstract Neo4jErrors getErrorCode();

}
