/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.connection;

import org.mule.connectors.commons.template.connection.ConnectorConnection;
import org.mule.modules.neo4j.internal.client.Neo4jMetadataService;
import org.neo4j.driver.v1.Session;

import java.io.Closeable;

public interface Neo4jConnection extends ConnectorConnection {

    String getId();

    Session getSession();

    void validate();

    Neo4jMetadataService getMetadataService();

}
