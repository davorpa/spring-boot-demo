package io.davorpatech.fwk.web.request.correlation.client.http;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelationUtils;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Rest template HTTP interceptor, that propagates the currents thread bound
 * request identifier to the outgoing request, through 'X-Request-Id' header.
 */
public class ClientHttpRequestCorrelationInterceptor implements ClientHttpRequestInterceptor
{
    /**
     * The request correlation config properties.
     */
    private final RequestCorrelationProperties properties;

    /**
     * Creates new instance of {@link ClientHttpRequestCorrelationInterceptor}.
     *
     * @param properties the config properties
     * @throws IllegalArgumentException if {@code properties} is {@code null}
     */
    public ClientHttpRequestCorrelationInterceptor(
            final @NonNull RequestCorrelationProperties properties)
    {
        Assert.notNull(properties, "Parameter 'properties' can not be null!");
        this.properties = properties;
    }

    @Override
    public ClientHttpResponse intercept(
            final @NonNull HttpRequest request,
            final @NonNull byte[] body,
            final @NonNull ClientHttpRequestExecution execution)
        throws IOException
    {
        // sets the correlation session id
        final String sessionId = RequestCorrelationUtils.getCurrentSessionId();
        if (sessionId != null) {
            request.getHeaders().add(properties.getSessionHeaderName(), sessionId);
        }

        // sets the correlation request id
        final String requestId = RequestCorrelationUtils.getCurrentRequestId();
        if (requestId != null) {
            request.getHeaders().add(properties.getRequestHeaderName(), requestId);
        }

        // proceeds with execution
        return execution.execute(request, body);
    }
}
