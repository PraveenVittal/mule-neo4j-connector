package org.mule.modules.neo4j.automation.functional;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataService;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.test.runner.RunnerDelegateTo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_CONSTRAINT_BORN;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_CONSTRAINT_HEIGHT;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_CONSTRAINT_NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.CREATE_INDEX_QUERY;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.DROP_CONSTRAINT_BORN;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.DROP_CONSTRAINT_HEIGHT;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.DROP_CONSTRAINT_NAME;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.DROP_INDEX_QUERY;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.METADATA_KEYS;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.METADATA_NODE_PROPERTIES;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TRUST_STORE_PROPERTY;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TRUST_STORE_PROPERTY_VALUE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.TRUST_STORE_PWD_PROPERTY_VALUE;
import static org.mule.modules.neo4j.automation.functional.TestDataBuilder.setTrustStores;
import static org.mule.runtime.api.component.location.Location.builder;
import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

@RunnerDelegateTo(Parameterized.class)
public class NodeMetadataResolverTestCase extends AbstractTestCases {

    private static final String FAIL_MESSAGE = "No assertions file was found for metadata key =  '%s'. It was created in the file %s. Please move it into src/test/resources/datasense/%s and re-run the test.";
    private static final String PATH_TEMPLATE = "/datasense/%s/%s.json";
    private static Location location;
    private static MetadataService metadataService;
    private File serializedMetadataFile;
    private MetadataKey metadataKey;

    @Override
    public int getTestTimeoutSecs() {
        return 999999;
    }

    @Override
    protected String[] getConfigFiles() {
        return new String[] {
                FLOW_CONFIG_LOCATION
        };
    }

    public NodeMetadataResolverTestCase(MetadataKey metadataKey){
        this.metadataKey = metadataKey;
        serializedMetadataFile = createSerializedFile();
    }

    private File createSerializedFile(){
        File metadataFile = new File(getClass().getResource("/").getFile(), format(PATH_TEMPLATE, getMetadataCategory(), metadataKey.getId()));
        metadataFile.getParentFile().mkdirs();
        return metadataFile;
    }

    @Override
    @Before
    public void setUp() throws Exception {
        cleanUpDB();
        createMetadataKeys();
        location = builder().globalName("createNodeFlow").addProcessorsPart().addIndexPart(0).build();
        metadataService = muleContext.getRegistry().lookupObject(MetadataService.class);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        // drop constraints
        execute(format(DROP_INDEX_QUERY, METADATA_KEYS.get(0)), null);
        execute(format(DROP_CONSTRAINT_NAME, METADATA_KEYS.get(2)), null);
        execute(format(DROP_CONSTRAINT_NAME, METADATA_KEYS.get(3)), null);
        execute(format(DROP_CONSTRAINT_BORN, METADATA_KEYS.get(3)), null);
        execute(format(DROP_CONSTRAINT_HEIGHT, METADATA_KEYS.get(3)), null);
        // drop nodes
        METADATA_KEYS.forEach(label -> {
            try {
                deleteNodes(label, true, null);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        // reset trust-stores
        setTrustStores(TRUST_STORE_PROPERTY, TRUST_STORE_PROPERTY_VALUE);
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return METADATA_KEYS.stream()
                .map(key -> newKey(key).withDisplayName(key).build())
                .map(Collections::singletonList)
                .map(List::toArray)
                .collect(toList());
    }

    @Test
    public void testInputMetadata() throws Exception {
        assertMetadataContents(serializedMetadataFile);
    }

    public void assertMetadataContents(File serializedMetadataFile) throws Exception {
        MetadataResult<ComponentMetadataDescriptor<OperationModel>> result = metadataService.getOperationMetadata(location, metadataKey);
        assertThat(result.isSuccess(), is(true));
        assertThat(result.getFailures(), hasSize(equalTo(0)));
        JSONArray actualMetadataJson = new JSONArray(result.get().getModel().getAllParameterModels().stream()
                .filter(parameterModel -> parameterModel.getName().equals("parameters"))
                .map(ParameterModel::getType)
                .collect(toList()));
        if (serializedMetadataFile.createNewFile()) {
            write(serializedMetadataFile, actualMetadataJson.toString());
            fail(format(FAIL_MESSAGE, metadataKey.getId(), serializedMetadataFile.getAbsolutePath(), getMetadataCategory()));
        } else {
            assertThat(actualMetadataJson.toList(), is(new JSONArray(readFileToString(serializedMetadataFile)).toList()));
        }
    }

    public void createMetadataKeys() throws Exception {
        // trust-stores
        setTrustStores(TRUST_STORE_PROPERTY_VALUE, TRUST_STORE_PWD_PROPERTY_VALUE);
        // constraints
        execute(format(CREATE_INDEX_QUERY, METADATA_KEYS.get(0)), null);
        execute(format(CREATE_CONSTRAINT_NAME, METADATA_KEYS.get(2)), null);
        execute(format(CREATE_CONSTRAINT_NAME, METADATA_KEYS.get(3)), null);
        execute(format(CREATE_CONSTRAINT_BORN, METADATA_KEYS.get(3)), null);
        execute(format(CREATE_CONSTRAINT_HEIGHT, METADATA_KEYS.get(3)), null);
        // nodes
        createNode(METADATA_KEYS.get(1), METADATA_NODE_PROPERTIES);
        createNode(METADATA_KEYS.get(2), METADATA_NODE_PROPERTIES);
        createNode(METADATA_KEYS.get(3), METADATA_NODE_PROPERTIES);
    }

    private String getMetadataCategory() {
        return "invokemetadataresolver";
    }

}