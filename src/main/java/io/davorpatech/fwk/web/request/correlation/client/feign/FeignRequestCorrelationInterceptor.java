package io.davorpatech.fwk.web.request.correlation.client.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationUtils;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * Feign request correlation interceptor.
 */
public class FeignRequestCorrelationInterceptor implements RequestInterceptor
{
    /**
     * The request correlation config properties.
     */
    private final RequestCorrelationProperties properties;

    /**
     * Creates new instance of {@link FeignRequestCorrelationInterceptor}.
     *
     * @param properties the config properties
     * @throws IllegalArgumentException if {@code properties} is {@code null}
     */
    public FeignRequestCorrelationInterceptor(
            final @NonNull RequestCorrelationProperties properties)
    {
        Assert.notNull(properties, "Parameter 'properties' can not be null!");
        this.properties = properties;
    }

    @Override
    public void apply(
            final @NonNull RequestTemplate template)
    {
        // sets the correlation session id
        final String sessionId = RequestCorrelationUtils.getCurrentSessionId();
        if (sessionId != null) {
            template.header(properties.getSessionHeaderName(), sessionId);
        }

        // sets the correlation request id
        final String requestId = RequestCorrelationUtils.getCurrentRequestId();
        if (requestId != null) {
            template.header(properties.getRequestHeaderName(), requestId);
        }
    }
}
