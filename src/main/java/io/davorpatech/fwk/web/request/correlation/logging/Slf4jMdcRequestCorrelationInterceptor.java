package io.davorpatech.fwk.web.request.correlation.logging;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelation;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationInterceptor;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * A {@link RequestCorrelationInterceptor} that setups the correlation id
 * into the logging system.
 */
public class Slf4jMdcRequestCorrelationInterceptor implements RequestCorrelationInterceptor
{
    /**
     * The request correlation config properties.
     */
    private final LoggingProperties properties;

    /**
     * Creates new instance of {@link Slf4jMdcRequestCorrelationInterceptor}.
     *
     * @param properties the config properties
     * @throws IllegalArgumentException if {@code properties} is {@code null}
     */
    public Slf4jMdcRequestCorrelationInterceptor(
            final @NonNull RequestCorrelationProperties properties)
    {
        Assert.notNull(properties, "Parameter 'properties' can not be null!");
        this.properties = properties.getLogging();
    }

    @Override
    public void afterCorrelationSet(
            final @NonNull RequestCorrelation requestCorrelation)
    {
        // sets the correlation session id
        String sessionId = requestCorrelation.getSessionId();
        if (sessionId != null) {
            MDC.put(properties.getSessionMdcKey(), sessionId);
        }

        // sets the correlation request id
        String requestId = requestCorrelation.getRequestId();
        if (requestId != null) {
            MDC.put(properties.getRequestMdcKey(), requestId);
        }
    }

    @Override
    public void cleanUp(
            final @NonNull RequestCorrelation requestCorrelation)
    {
        MDC.remove(properties.getRequestMdcKey());
        MDC.remove(properties.getSessionMdcKey());
    }
}
