package org.mule.modules.neo4j.internal.extension;

import org.mule.modules.neo4j.internal.config.Neo4jConfig;
import org.mule.modules.neo4j.internal.exception.Neo4jErrors;
import org.mule.modules.neo4j.internal.exception.Neo4jExceptionHandler;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.OnException;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

@Extension(name = "Neo4j", description = "Neo4j Extension")
@Xml(prefix = "neo4j")
@Configurations(Neo4jConfig.class)
@OnException(Neo4jExceptionHandler.class)
@ErrorTypes(Neo4jErrors.class)
public class Neo4jExtension {
}
