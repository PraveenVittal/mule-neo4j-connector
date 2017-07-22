/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.exception;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.exception.ExceptionHandler;
import org.neo4j.driver.v1.exceptions.ClientException;

import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.REQUEST_FAILED;
import static org.mule.modules.neo4j.internal.exception.Neo4jErrors.UNKNOWN;

public class Neo4jExceptionHandler extends ExceptionHandler {

    @Override
    public ModuleException enrichException(Exception exception) {
        try {
            throw exception;
        } catch(org.mule.modules.neo4j.api.Neo4jException e) {
            return new ModuleException(e.getErrorCode(), e);
        }
        catch (Neo4jException | ClientException e) {
            return new ModuleException(REQUEST_FAILED, e);
        } catch (Exception e) {
            return new ModuleException(UNKNOWN, e);
        }
    }
}
