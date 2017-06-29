
package org.mule.modules.neo4j.internal.connector.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.devkit.internal.lic.LicenseValidatorFactory;
import org.mule.devkit.internal.lic.validator.LicenseValidator;
import org.mule.modules.neo4j.internal.connector.Neo4jConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>Neo4jConnectorProcessAdapter</code> is a wrapper around {@link Neo4jConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-06-29T06:11:54-03:00", comments = "Build UNNAMED.2793.f49b6c7")
public class Neo4jConnectorProcessAdapter
    extends Neo4jConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<Neo4jConnectorCapabilitiesAdapter> , Initialisable
{


    public<P >ProcessTemplate<P, Neo4jConnectorCapabilitiesAdapter> getProcessTemplate() {
        final Neo4jConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,Neo4jConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, Neo4jConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, Neo4jConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

    @Override
    public void initialise()
        throws InitialisationException
    {
        super.initialise();
        checkMuleLicense();
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    private void checkMuleLicense() {
        LicenseValidator licenseValidator = LicenseValidatorFactory.getValidator("Neo4j");
        licenseValidator.checkEnterpriseLicense(false);
    }

}
