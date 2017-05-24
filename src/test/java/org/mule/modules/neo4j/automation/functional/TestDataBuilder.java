/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class TestDataBuilder {

    public static final String TEST_LABEL = "TestLabel";
    public static final String TEST_LABEL2 = "TestLabel2";
    public static final String TEST_REL = "TestRel";
    public static final String CREATE_TEST_RELATION = format("MATCH (a:%s),(b:%s) CREATE (a)-[r:%s]->(b) RETURN r", TEST_LABEL, TEST_LABEL2, TEST_REL);
    public static final String EMPTY_JSON_LIST = "[]";
    public static final String QUERY_RETURN_A_NODE = "MATCH (a:%s) RETURN a";
    public static final String QUERY_DELETE_A_NODE = "MATCH (a:%s) DELETE a";
    public static final String EMPTY_NODE = "[{\"a\":{}}]";
    public static final String PARAMS_STRING = "{name:\"Tom Hanks\",born:1956}";
    public static final String TOMHANKS_NODE = "[{\"a\":{\"born\":1956,\"name\":\"Tom Hanks\"}}]";
    public static final String TOMHANKS_NODE_1980 = "[{\"a\":{\"born\":1980,\"name\":\"Tom Hanks\"}}]";
    public static final String CREATE_A_NODE_QUERY = "CREATE (a:%s %s) RETURN a";
    public static final String CREATE_A_WITH_PARAMS_NODE_QUERY = "CREATE (a:%s {name: $name, born: toInt($born)}) RETURN a";
    public static final String CREATE_INDEX_QUERY = "CREATE INDEX on :%s(name)";
    public static final String CREATE_CONSTRAINT_NAME = "CREATE CONSTRAINT ON ( a:%s ) ASSERT a.name IS UNIQUE";
    public static final String CREATE_CONSTRAINT_BORN = "CREATE CONSTRAINT ON ( a:%s ) ASSERT a.born IS UNIQUE";
    public static final String CREATE_CONSTRAINT_HEIGHT = "CREATE CONSTRAINT ON ( a:%s ) ASSERT a.height IS UNIQUE";
    public static final String DROP_INDEX_QUERY = "DROP INDEX on :%s(name)";
    public static final String DROP_CONSTRAINT_NAME = "DROP CONSTRAINT ON ( a:%s ) ASSERT a.name IS UNIQUE";
    public static final String DROP_CONSTRAINT_BORN = "DROP CONSTRAINT ON ( a:%s ) ASSERT a.born IS UNIQUE";
    public static final String DROP_CONSTRAINT_HEIGHT = "DROP CONSTRAINT ON ( a:%s ) ASSERT a.height IS UNIQUE";

    public static final List<String> METADATA_KEYS = ImmutableList.<String>builder().add("KEY1").add("KEY2").add("KEY3").add("KEY4").build();

    public static final Map<String, Object> PARAMS_MAP = ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").put("born", 1956).build();
    public static final Map<String, Object> TOMHANKS_NAME_PARAM = ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").build();
    public static final Map<String, Object> TOMHANKS_BORN_PARAM = ImmutableMap.<String, Object>builder().put("born", 1980).build();
    public static final Map<String, Object> METADATA_NODE_PROPERTIES = ImmutableMap.<String, Object>builder().put("name", "metadata").put("born", 2017).put("height", 1.78).build();
}
