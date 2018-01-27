/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.internal.service;

import java.util.List;
import java.util.Map;

import org.mule.connectors.commons.template.service.ConnectorService;

public interface Neo4jService extends ConnectorService {

	void createNode(String label, Map<String, Object> parameters);

	List<Map<String, Object>> selectNodes(String label, Map<String, Object> parameters);

	void updateNodes(String label, Map<String, Object> parameters, Map<String, Object> setParameters);

	void deleteNodes(String label, boolean removeRelationships, Map<String, Object> parameters);

	List<Map<String, Object>> execute(String label, Map<String, Object> parameters);
}
