package org.mule.modules.neo4j.internal.util;

import com.google.common.base.Function;

/**
 * {@link Function} that applies a format that follows the {@link java.util.Formatter} conventions to the input.
 */
public class FormatFunction implements Function<String, String> {

    private final String template;

    public FormatFunction(String template) {
        this.template = template;
    }

    @Override
    public String apply(String input) {
        return String.format(template, input);
    }
}
