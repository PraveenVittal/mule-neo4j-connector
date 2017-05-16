package org.mule.modules.neo4j.internal.connection;

import org.neo4j.driver.v1.Session;

import java.io.Closeable;

public interface Neo4JConnection extends Closeable {

    String getId();

    Session getSession();

    void validate();
}
