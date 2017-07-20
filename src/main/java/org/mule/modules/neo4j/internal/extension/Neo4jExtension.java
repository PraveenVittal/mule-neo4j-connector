package org.mule.modules.neo4j.internal.extension;

import org.mule.modules.neo4j.internal.config.Neo4JConfig;
import org.mule.modules.neo4j.internal.exception.Neo4JErrors;
import org.mule.modules.neo4j.internal.exception.Neo4JExceptionHandler;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.OnException;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

@Extension(name = "Neo4j", description = "Neo4j Extension")
@Xml(prefix = "neo4j")
@Configurations(Neo4JConfig.class)
@OnException(Neo4JExceptionHandler.class)
@ErrorTypes(Neo4JErrors.class)
public class Neo4JExtension {
}
