package com.mulesoft.connectors.neo4j.internal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mule.runtime.core.api.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

public class ConverterUtils {

    public static Map<String, Object> toMap(InputStream input) throws IOException {
        return input != null
                ? new ObjectMapper().readValue(IOUtils.toString(input), new TypeReference<Map<String, Object>>() {
        }) : new HashMap<>();
    }

    public static InputStream toJSONStream(List<Map<String, Object>> input) throws JsonProcessingException {
        return toInputStream(new ObjectMapper().writeValueAsString(input));
    }
}
