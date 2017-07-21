package org.mule.modules.neo4j.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.Set;

public class InvokeMetadataResolver implements InputTypeResolver<MetadataKey>, TypeKeysResolver, OutputTypeResolver<MetadataKey> {

    @Override
    public MetadataType getInputMetadata(MetadataContext metadataContext, MetadataKey metadataKey) throws MetadataResolvingException, ConnectionException {
        return null;
    }

    @Override
    public MetadataType getOutputType(MetadataContext metadataContext, MetadataKey metadataKey) throws MetadataResolvingException, ConnectionException {
        return null;
    }

    @Override
    public Set<MetadataKey> getKeys(MetadataContext metadataContext) throws MetadataResolvingException, ConnectionException {
        return null;
    }

    @Override
    public String getCategoryName() {
        return InvokeMetadataResolver.class.getCanonicalName();
    }

    @Override
    public String getResolverName() {
        return this.getClass().getSimpleName();
    }
}
