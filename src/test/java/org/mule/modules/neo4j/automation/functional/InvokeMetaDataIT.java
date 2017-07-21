/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.neo4j.automation.functional;

//public class InvokeMetaDataIT extends AbstractMetaDataTestCase<Neo4jConnector> {
//
//    private String defaultTrustStore = getProperty(TRUSTSTORE_PROPERTY);
//    private String defaultTrustStorePass = getProperty(TRUSTSTORE_PWD_PROPERTY);
//
//    public InvokeMetaDataIT() {
//        super(METADATA_KEYS, InvokeMetaData.class, Neo4jConnector.class);
//    }
//
//    @Before
//    public void setUp() throws NoSuchAlgorithmException, CertificateException, KeyManagementException, KeyStoreException, IOException {
//
//        setTrustStores(TRUSTSTORE_PROPERTY_VALUE, TRUSTSTORE_PWD_PROPERTY_VALUE);
//
//        getConnector().execute(format(CREATE_INDEX_QUERY, METADATA_KEYS.get(0)), null);
//        getConnector().execute(format(CREATE_CONSTRAINT_NAME, METADATA_KEYS.get(2)), null);
//        getConnector().execute(format(CREATE_CONSTRAINT_NAME, METADATA_KEYS.get(3)), null);
//        getConnector().execute(format(CREATE_CONSTRAINT_BORN, METADATA_KEYS.get(3)), null);
//        getConnector().execute(format(CREATE_CONSTRAINT_HEIGHT, METADATA_KEYS.get(3)), null);
//
//        getConnector().createNode(METADATA_KEYS.get(1), METADATA_NODE_PROPERTIES);
//        getConnector().createNode(METADATA_KEYS.get(2), METADATA_NODE_PROPERTIES);
//        getConnector().createNode(METADATA_KEYS.get(3), METADATA_NODE_PROPERTIES);
//    }
//
//    @Override
//    @MetaDataTest
//    @Test
//    @RunOnlyOn(profiles = DeploymentProfiles.embedded)
//    public void verify() throws IOException {
//        super.verify();
//    }
//
//    @After
//    public void tearDown() {
//        getConnector().execute(format(DROP_INDEX_QUERY, METADATA_KEYS.get(0)), null);
//        getConnector().execute(format(DROP_CONSTRAINT_NAME, METADATA_KEYS.get(2)), null);
//        getConnector().execute(format(DROP_CONSTRAINT_NAME, METADATA_KEYS.get(3)), null);
//        getConnector().execute(format(DROP_CONSTRAINT_BORN, METADATA_KEYS.get(3)), null);
//        getConnector().execute(format(DROP_CONSTRAINT_HEIGHT, METADATA_KEYS.get(3)), null);
//
//        for (String label : METADATA_KEYS) {
//            getConnector().deleteNodes(label, true, null);
//        }
//
//        setTrustStores(defaultTrustStore,defaultTrustStorePass);
//    }
//}
