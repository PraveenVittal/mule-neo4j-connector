
package org.mule.modules.neo4j.internal.connector.generated.processors;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.common.metadata.key.property.TypeDescribingProperty;
import org.mule.devkit.internal.metadata.MetaDataGeneratorUtils;
import org.mule.devkit.internal.metadata.fixes.STUDIO7157;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;
import org.mule.modules.neo4j.internal.exception.Neo4JExceptionHandler;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * SelectNodesMessageProcessor invokes the {@link org.mule.modules.neo4j.internal.connector.Neo4jConnector#selectNodes(java.lang.String, java.util.Map)} method in {@link Neo4jConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-06-29T06:11:54-03:00", comments = "Build UNNAMED.2793.f49b6c7")
public class SelectNodesMessageProcessor
    extends DevkitBasedMessageProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object label;
    protected String _labelType;
    protected Object parameters;
    protected Map<String, Object> _parametersType;

    public SelectNodesMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets label
     * 
     * @param value Value to set
     */
    public void setLabel(Object value) {
        this.label = value;
    }

    /**
     * Sets parameters
     * 
     * @param value Value to set
     */
    public void setParameters(Object value) {
        this.parameters = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(null, false, event);
            final String _transformedLabel = ((String) evaluateAndTransform(getMuleContext(), event, SelectNodesMessageProcessor.class.getDeclaredField("_labelType").getGenericType(), null, label));
            final Map<String, Object> _transformedParameters = ((Map<String, Object> ) evaluateAndTransform(getMuleContext(), event, SelectNodesMessageProcessor.class.getDeclaredField("_parametersType").getGenericType(), null, parameters));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    try {
                        return ((Neo4jConnector) object).selectNodes(_transformedLabel, _transformedParameters);
                    } catch (Exception e) {
                        Neo4JExceptionHandler handler = new Neo4JExceptionHandler();
                        handler.customHandler(e);
                        throw e;
                    }
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        if (((label) == null)||((label).toString() == null)) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error retrieving metadata from parameter: label at processor selectNodes at module Neo4jConnector");
        }
        DefaultMetaDataKey metaDataKey = new DefaultMetaDataKey((label).toString(), null);
        metaDataKey.setCategory("InvokeMetaData");
        metaDataKey.addProperty(new TypeDescribingProperty(TypeDescribingProperty.TypeScope.INPUT, "selectNodes"));
        Result<MetaData> genericMetaData = getGenericMetaData(metaDataKey);
        if ((Result.Status.FAILURE).equals(genericMetaData.getStatus())) {
            return genericMetaData;
        }
        MetaDataModel metaDataPayload = genericMetaData.get().getPayload();
        DefaultMetaDataKey keyForStudio = new DefaultMetaDataKey((label).toString(), null);
        keyForStudio.setCategory("InvokeMetaData");
        metaDataPayload.addProperty(STUDIO7157 .getStructureIdentifierMetaDataModelProperty(keyForStudio, false, false));
        MetaDataModel wrappedMetaDataModel = metaDataPayload;
        return new DefaultResult<MetaData>(MetaDataGeneratorUtils.extractPropertiesToMetaData(wrappedMetaDataModel, genericMetaData.get()));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        if (((label) == null)||((label).toString() == null)) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error retrieving metadata from parameter: label at processor selectNodes at module Neo4jConnector");
        }
        DefaultMetaDataKey metaDataKey = new DefaultMetaDataKey((label).toString(), null);
        metaDataKey.setCategory("InvokeMetaData");
        metaDataKey.addProperty(new TypeDescribingProperty(TypeDescribingProperty.TypeScope.OUTPUT, "selectNodes"));
        Result<MetaData> genericMetaData = getGenericMetaData(metaDataKey);
        if ((Result.Status.FAILURE).equals(genericMetaData.getStatus())) {
            return genericMetaData;
        }
        MetaDataModel metaDataPayload = genericMetaData.get().getPayload();
        DefaultMetaDataKey keyForStudio = new DefaultMetaDataKey(((label).toString()+" Result"), null);
        keyForStudio.setCategory("InvokeMetaData");
        metaDataPayload.addProperty(STUDIO7157 .getStructureIdentifierMetaDataModelProperty(keyForStudio, false, true));
        MetaDataModel wrappedMetaDataModel = new DefaultListMetaDataModel(metaDataPayload);
        return new DefaultResult<MetaData>(MetaDataGeneratorUtils.extractPropertiesToMetaData(wrappedMetaDataModel, genericMetaData.get()));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(null, false, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at Neo4jConnector at selectNodes retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
