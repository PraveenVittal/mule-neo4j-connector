/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.exception;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.exception.ExceptionHandler;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN;

public class Neo4jExceptionHandler extends ExceptionHandler {

    @Override
    public ModuleException enrichException(Exception exception) {
        if(exception instanceof Neo4jException) {
            return new ModuleException(((Neo4jException) exception).getErrorCode(), exception);
        }
        else {
            return new ModuleException(UNKNOWN, exception);
        }
    }
}
