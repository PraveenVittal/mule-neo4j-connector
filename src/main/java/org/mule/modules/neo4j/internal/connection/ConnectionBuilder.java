package org.mule.modules.neo4j.internal.connection;

public interface ConnectionBuilder<C extends Neo4JConnection> {

    C create();
}
