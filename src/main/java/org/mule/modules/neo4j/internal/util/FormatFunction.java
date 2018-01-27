/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.util;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.toInputStream;

import java.io.InputStream;

import javax.swing.text.Document;

import com.google.common.base.Function;

/**
 * {@link Function} that applies a format that follows the
 * {@link java.util.Formatter} conventions to the input.
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

	/**
	 * Convert a Document to Json InputStream
	 */
	public static InputStream toJsonResult(Document document) {
		if (document != null) {
			return toInputStream(document.toString(), UTF_8);
		} else {
			return null;
		}
	}
}
