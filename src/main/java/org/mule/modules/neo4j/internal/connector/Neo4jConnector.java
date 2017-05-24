/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.connector;

import java.util.List;
import java.util.Map;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.RefOnly;
import org.mule.modules.neo4j.internal.client.Neo4JClient;
import org.mule.modules.neo4j.internal.client.Neo4JClientImpl;
import org.mule.modules.neo4j.internal.client.Neo4JMetadataClient;
import org.mule.modules.neo4j.internal.client.Neo4JMetadataClientImpl;
import org.mule.modules.neo4j.internal.connection.basic.BasicAuthenticationConfig;
import org.mule.modules.neo4j.internal.exception.Neo4JExceptionHandler;
import org.mule.modules.neo4j.internal.metadata.InvokeMetaData;

import static org.mule.api.annotations.param.MetaDataKeyParamAffectsType.BOTH;
import static org.mule.api.annotations.param.MetaDataKeyParamAffectsType.INPUT;

@Connector(name = "neo4j", friendlyName = "Neo4j")
@RequiresEnterpriseLicense
@OnException(handler = Neo4JExceptionHandler.class)
@MetaDataScope(InvokeMetaData.class)
public class Neo4jConnector {

    @Config
    private BasicAuthenticationConfig config;

    @Processor
    public List<Map<String, Object>> execute(@Default("#[payload]") String query, @Optional @RefOnly Map<String, Object> parameters) {
        return getClient().execute(query, parameters);
    }

    @Processor
    public void createNode(@MetaDataKeyParam(affects = INPUT) @Default("#[payload]") String label, @Optional @RefOnly Map<String, Object> parameters) {
        getClient().createNode(label, parameters);
    }

    @Processor
    public List<Map<String, Object>> selectNodes(@MetaDataKeyParam(affects = BOTH) @Default("#[payload]") String label,
            @Optional @RefOnly Map<String, Object> parameters) {
        return getClient().selectNodes(label, parameters);
    }

    @Processor
    public void updateNodes(@MetaDataKeyParam(affects = INPUT) @Default("#[payload]") String label, @Optional @RefOnly Map<String, Object> parameters,
            @RefOnly Map<String, Object> setParameters) {
        getClient().updateNodes(label, parameters, setParameters);
    }

    @Processor
    public void deleteNodes(@MetaDataKeyParam(affects = INPUT) @Default("#[payload]") String label,
            @FriendlyName("Also remove existing relationships") boolean removeRelationships, @Optional @RefOnly Map<String, Object> parameters) {
        getClient().deleteNodes(label, removeRelationships, parameters);
    }

    private Neo4JClient getClient() {
        return new Neo4JClientImpl(getConfig().getConnection());
    }

    public Neo4JMetadataClient getMetadataClient() {
        return new Neo4JMetadataClientImpl(getConfig().getMetadataInfoConnection());
    }

    public BasicAuthenticationConfig getConfig() {
        return config;
    }

    public void setConfig(BasicAuthenticationConfig config) {
        this.config = config;
    }
}