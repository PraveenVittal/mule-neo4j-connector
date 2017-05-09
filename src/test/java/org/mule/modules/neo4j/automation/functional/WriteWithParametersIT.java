/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.readResourceStatement;

public class WriteWithParametersIT extends AbstractTestCases {

    @Test public void writeWithParametersTest() throws IOException {
        Map<String, Object> parameters = getGson().fromJson(readResourceStatement("/write/parameters/WriteWithParametersIT.json"), new TypeToken<Map<String, Object>>() {

        }.getType());
        getConnector().writeWithParameters(readResourceStatement("/write/fixtures/WriteWithParametersIT.txt"), parameters);

        List<Map<String, Object>> result = getConnector().readWithParameters(readResourceStatement("/write/validatorQuery/WriteWithParametersIT.txt"), parameters);

        assertThat(getGson().toJson(getParser().parse(readResourceStatement("/write/assertions/WriteWithParametersIT.json")).getAsJsonArray()), equalTo(getGson().toJson(result)));
    }
}
