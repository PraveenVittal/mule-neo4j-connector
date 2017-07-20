package org.mule.modules.neo4j.automation.functional;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mule.modules.neo4j.internal.metadata.InvokeMetadataResolver;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataService;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.core.api.registry.RegistrationException;
import org.mule.runtime.core.internal.metadata.MuleMetadataService;
import org.mule.test.runner.RunnerDelegateTo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Iterables.find;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunnerDelegateTo(Parameterized.class)
public class InvokeMetadataResolverTestCase extends AbstractTestCases {

    protected static final String FAIL_MESSAGE = "No assertions file was found for metadata key =  '%s'. It was created in the file %s. Please move it into src/test/resources/datasense/%s and re-run the test.";
    private static final String PATH_TEMPLATE = "/datasense/%s/%s/%s__%s.json";
    protected static Location location;
    protected static MetadataService metadataService;
    private File serializedInputMetadataFile;
    private File serializedOutputMetadataFile;
    private MetadataKey metadataKey;

    @Override
    public int getTestTimeoutSecs() {
        return 999999;
    }

    @Override
    protected String getConfigFile() {
        return FLOW_CONFIG_LOCATION;
    }

    public InvokeMetadataResolverTestCase(MetadataKey metadataKey){
        this.metadataKey = metadataKey;
        serializedInputMetadataFile = createSerializedFile(Scope.INPUT);
        serializedOutputMetadataFile = createSerializedFile(Scope.OUTPUT);
    }

    private File createSerializedFile(Scope scope){
        File metadataFile = new File(getClass().getResource("/").getFile(), format(PATH_TEMPLATE, getMetadataCategory(), scope.toString().toLowerCase(), metadataKey, scope.toString().toLowerCase()));
        metadataFile.getParentFile().mkdirs();
        return metadataFile;
    }

    @Before
    public void setup() throws Exception {
        location = Location.builder().globalName("executeFlow").addProcessorsPart().addIndexPart(0).build();
        metadataService = muleContext.getRegistry().lookupObject(MuleMetadataService.class);
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() throws RegistrationException {
        return metadataService.getMetadataKeys(location).get().getKeys(InvokeMetadataResolver.class.getSimpleName().toLowerCase()).get()
                .stream()
                .map(Collections::singletonList)
                .map(List::toArray)
                .collect(toList());
    }

    @Test
    public void testInputMetadata() throws Exception {
        assertMetadataContents(serializedInputMetadataFile, Scope.INPUT);
    }

    @Test
    public void testOutputMetadata() throws Exception {
        assertMetadataContents(serializedOutputMetadataFile, Scope.OUTPUT);
    }

    public void assertMetadataContents(File serializedMetadataFile, Scope scope) throws Exception {
        final MetadataResult<ComponentMetadataDescriptor<OperationModel>> metadataDescriptor = metadataService.getOperationMetadata(location, metadataKey);
        String msg = metadataDescriptor.getFailures().stream().map(f -> "Failure: " + f.getMessage()).collect(joining(", "));
        assertThat(msg, metadataDescriptor.isSuccess(), is(true));
        String actualMetadata;
        if(scope == Scope.INPUT) {
            ParameterModel bodyParams = find(metadataDescriptor.get().getModel().getAllParameterModels(), new Predicate<ParameterModel>() {
                @Override
                public boolean apply(ParameterModel input) {
                    return input.getName().equals("body");
                }
            });
            actualMetadata = new Gson().toJson(bodyParams);
        }
        else {
            actualMetadata = new Gson().toJson(metadataDescriptor.get().getModel().getOutput().getType());
        }
        if (serializedMetadataFile.createNewFile()) {
            write(serializedMetadataFile, actualMetadata);
            fail(format(FAIL_MESSAGE, metadataKey, serializedMetadataFile.getAbsolutePath(), getMetadataCategory()));
        } else {
            assertThat(actualMetadata, is(readFileToString(serializedMetadataFile)));
        }
    }

    private String getMetadataCategory() {
        return InvokeMetadataResolver.class.getSimpleName().toLowerCase();
    }

    private enum Scope {
        INPUT,
        OUTPUT
    }


}
