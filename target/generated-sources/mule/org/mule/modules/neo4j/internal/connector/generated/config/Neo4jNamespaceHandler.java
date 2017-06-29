
package org.mule.modules.neo4j.internal.connector.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/neo4j</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-06-29T06:11:54-03:00", comments = "Build UNNAMED.2793.f49b6c7")
public class Neo4jNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(Neo4jNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [neo4j] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [neo4j] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new Neo4jConnectorBasicAuthenticationConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("execute", new ExecuteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("execute", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-node", new CreateNodeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-node", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("select-nodes", new SelectNodesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("select-nodes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-nodes", new UpdateNodesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-nodes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-nodes", new DeleteNodesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-nodes", "@Processor", ex);
        }
    }

}
