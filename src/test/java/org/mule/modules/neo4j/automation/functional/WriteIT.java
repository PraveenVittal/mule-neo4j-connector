/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.readResourceStatement;

public class WriteIT extends AbstractTestCases {

    @Test public void writeTest() throws IOException {
        getConnector().write(readResourceStatement("/write/fixtures/WriteIT.txt"));
        List<Map<String, Object>> result = getConnector().read(readResourceStatement("/write/validatorQuery/WriteIT.txt"));

        assertThat(getGson().toJson(getParser().parse(readResourceStatement("/write/assertions/WriteIT.json")).getAsJsonArray()), equalTo(getGson().toJson(result)));
    }
}
