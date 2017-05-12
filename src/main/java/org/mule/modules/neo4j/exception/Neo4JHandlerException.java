/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.exception;

import org.mule.api.annotations.Handle;
import org.mule.api.annotations.components.Handler;

@Handler
public class Neo4JHandlerException {

    @Handle
    public void customHandler(Exception exception) throws Exception {
        throw new Neo4JConnectorException(exception);
    }
}
