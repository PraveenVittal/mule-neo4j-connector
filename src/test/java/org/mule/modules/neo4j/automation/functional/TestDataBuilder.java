/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestDataBuilder {

    public static String readResourceStatement(String fileName) throws IOException {
        return IOUtils.toString(TestDataBuilder.class.getResourceAsStream(fileName), StandardCharsets.UTF_8);
    }
}
