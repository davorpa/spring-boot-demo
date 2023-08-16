package io.davorpatech.fwk.web.request.correlation.errorattr;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelation;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationConstants;
import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that always adds the request correlation ids to
 * the processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see RequestCorrelation
 */
public class CorrelationErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport
        implements Ordered
{
    /**
     * Constructs a new {@link CorrelationErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public CorrelationErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        super(customizerProperties);
    }

    @Override
    public void customize(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull WebRequest webRequest,
            final @NonNull ErrorAttributeOptions options)
    {
        final RequestCorrelation requestCorrelation = getAttribute(
                webRequest, RequestCorrelationConstants.ATTRIBUTE_NAME);
        if (requestCorrelation == null) return;

        putInDeepPath(errorAttributes,
                customizerProperties.getCorrelation().getAttrPath(),
                requestCorrelation);
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getCorrelation().getOrder();
    }
}
