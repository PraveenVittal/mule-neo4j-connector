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

public class ReadWithParametersIT extends AbstractTestCases {

    @Test public void readWithParametersTest() throws IOException {

        Map<String, Object> parameters = getGson().fromJson(readResourceStatement("/read/parameters/ReadWithParametersIT.json"), new TypeToken<Map<String, Object>>() {

        }.getType());
        List<Map<String, Object>> result = getConnector().readWithParameters(readResourceStatement("/read/fixtures/ReadWithParametersIT.txt"), parameters);

        assertThat(getGson().toJson(getParser().parse(readResourceStatement("/read/assertions/ReadWithParametersIT.json")).getAsJsonArray()), equalTo(getGson().toJson(result)));
    }
}
