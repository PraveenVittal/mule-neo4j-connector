/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.service;

import java.util.List;
import java.util.Set;

public interface Neo4jMetadataService {

	List<String> getLabels();

	Set<String> getConstraintProperties(String label);
}
