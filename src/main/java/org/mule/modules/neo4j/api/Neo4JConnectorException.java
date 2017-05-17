/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.api;

public class Neo4JConnectorException extends RuntimeException {

    public Neo4JConnectorException() {
        super();
    }

    public Neo4JConnectorException(Throwable cause) {
        super(cause);
    }

    public Neo4JConnectorException(String message) {
        super(message);
    }

    public Neo4JConnectorException(Throwable cause, String message) {
        super(message, cause);
    }
}
