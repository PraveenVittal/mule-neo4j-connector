/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.RefOnly;
import org.mule.modules.neo4j.config.BasicAuthenticationConfig;
import org.mule.modules.neo4j.exception.Neo4JHandlerException;
import org.mule.modules.neo4j.internal.client.Neo4JClient;

import java.util.List;
import java.util.Map;

@Connector(name = "neo4j", friendlyName = "Neo4j")
@RequiresEnterpriseLicense
@OnException(handler = Neo4JHandlerException.class)
public class Neo4jConnector {

    @Config
    private BasicAuthenticationConfig config;

    @Processor
    public List<Map<String, Object>> execute(@Default("#[payload]") String query, @Optional @RefOnly Map<String, Object> parameters) {
        return getClient().execute(query, parameters);
    }

    @Processor
    public void createNodes(@Default("#[payload]") @RefOnly List<Map<String, Object>> parameters, @Optional @RefOnly List<String> labels) {
        getClient().createNodes(parameters, labels);
    }

    @Processor
    public void createRelationBetweenNodes(@Optional @RefOnly List<String> labelsA, @Optional @RefOnly List<String> labelsB, @Default("#[payload]") String condition,
            @RefOnly String labelR, @Optional @RefOnly Map<String, Object> relProps) {
        getClient().createRelationBetweenNodes(labelsA, labelsB, condition, labelR, relProps);
    }

    private Neo4JClient getClient() {
        return getConfig().getClient();
    }

    public BasicAuthenticationConfig getConfig() {
        return config;
    }

    public void setConfig(BasicAuthenticationConfig config) {
        this.config = config;
    }
}