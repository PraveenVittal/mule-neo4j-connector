/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import org.mule.modules.neo4j.Neo4jConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class AbstractTestCases extends AbstractTestCase<Neo4jConnector> {

    private JsonParser parser = new JsonParser();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AbstractTestCases() {
        super(Neo4jConnector.class);
    }

    public JsonParser getParser() {
        return parser;
    }

    public void setParser(JsonParser parser) {
        this.parser = parser;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
