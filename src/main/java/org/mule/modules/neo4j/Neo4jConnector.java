package org.mule.modules.neo4j;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.modules.neo4j.config.Config;
import org.mule.modules.neo4j.internal.Neo4JClientImpl;

import java.util.List;
import java.util.Map;

@Connector(name="neo4j", friendlyName="Neo4j")
public class Neo4jConnector {

    @org.mule.api.annotations.Config
    Config config;

    @Processor
    public List<Map<String,Object>> read(String query) {
            return getClient().read(query);

    }

    @Processor
    public List<Map<String,Object>> readWithParameters(String query,Map<String,Object> parameters) {
        return getClient().read(query,parameters);

    }

    @Processor
    public List<Map<String,Object>> write(String query) {
        return getClient().write(query);
    }

    @Processor
    public List<Map<String,Object>> writeWithParameters(String query,Map<String,Object> parameters) {
        return getClient().write(query,parameters);
    }

    private Neo4JClientImpl getClient(){
        return getConfig().getClient();
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}