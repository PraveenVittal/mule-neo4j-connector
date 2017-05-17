/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class TestDataBuilder {

    private static final String TEST_LABEL = "TestLabel";
    private static final String PARAMS_STRING = "{name:\"Tom Hanks\",born:1956}";
    private static final Map<String, Object> PARAMS_MAP = ImmutableMap.<String, Object>builder().put("name", "Tom Hanks").put("born", 1956).build();

    public static String getTestLabel() {
        return TEST_LABEL;
    }

    public static Map<String, Object> getParamsMap() {
        return PARAMS_MAP;
    }

    public static String getParamsString() {
        return PARAMS_STRING;
    }
}
