package io.davorpatech.fwk.web.request.correlation;

import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The entry point for the request correlation.
 *
 * This filter intercepts any incoming request and in case that it
 * does not contain the correlation header creates new identifier
 * and stores it both as the request header and also as an attribute.
 */
public class RequestCorrelationFilter extends OncePerRequestFilter
{
    /**
     * Logger instance used by this class.
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass()); // NOSONAR

    /**
     * The request generator used for generating new identifiers.
     */
    protected final RequestCorrelationGenerator generator;

    /**
     * List of optional interceptors.
     */
    protected final List<RequestCorrelationInterceptor> interceptors;

    /**
     * The request correlation config properties.
     */
    protected final RequestCorrelationProperties properties;

    /**
     * Creates new instance of {@link RequestCorrelationFilter} class.
     *
     * @param generator the request correlation id generator
     * @param interceptors the correlation interceptors
     * @param properties the request correlation configuration properties
     * @throws IllegalArgumentException if {@code generator} is {@code null}
     *         or {@code interceptors} is {@code null}
     *         or {@code properties} is {@code null}
     */
    public RequestCorrelationFilter(
            final RequestCorrelationGenerator generator,
            final List<RequestCorrelationInterceptor> interceptors,
            final RequestCorrelationProperties properties)
    {
        Assert.notNull(generator, "Parameter 'generator' can not be null!");
        Assert.notNull(interceptors, "Parameter 'interceptors' can not be null!");
        Assert.notNull(properties, "Parameter 'properties' can not be null!");
        this.generator = generator;
        this.interceptors = interceptors;
        this.properties = properties;
    }

    /**
     * Performs 'enrichment' of incoming HTTP request.
     *
     * @param request     the http servlet request
     * @param response    the http servlet response
     * @param filterChain the filter processing chain
     * @throws IOException if any error occurs
     * @throws ServletException if any error occurs
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
        throws ServletException, IOException
    {
        // extract correlation ids from request or generate one if not present
        String sessionId = extractSessionId(request);
        String requestId = extractRequestId(request);

        // instantiates new request correlation
        final RequestCorrelation requestCorrelation = new DefaultRequestCorrelation(
                sessionId, requestId);

        // triggers interceptors after set event
        triggerInterceptorsSet(requestCorrelation);

        // do filter chain with the enriched request
        try {
            filterChain.doFilter(
                    enrichRequest(request, requestCorrelation),
                    enrichResponse(response, requestCorrelation));
        } finally {
            // triggers interceptors cleanup event
            triggerInterceptorsCleanup(requestCorrelation);
        }
    }

    /**
     * Extracts the correlation session id from the HTTP request, if present,
     * or generates new one if not.
     *
     * @param request the HTTP servlet request
     * @return the request correlation session id
     *
     * @see RequestCorrelationProperties#isEnableSessions()
     * @see RequestCorrelationProperties#getSessionHeaderName()
     * @see RequestCorrelationGenerator#generateSessionId(HttpServletRequest)
     */
    protected String extractSessionId(final HttpServletRequest request)
    {
        // get value from request header
        String sessionId = request.getHeader(properties.getSessionHeaderName());
        // if value is blank and generate session is enabled, generates new one
        if (properties.isEnableSessions() && !StringUtils.hasText(sessionId)) {
            sessionId = generator.generateSessionId(request);
            logger.debug("Session correlation id was not present, generating new one: {}", sessionId);
        }
        return sessionId;
    }

    /**
     * Extracts the correlation request id from the HTTP request, if present,
     * or generates new one if not.
     *
     * @param request the HTTP servlet request
     * @return the request correlation request id
     *
     * @see RequestCorrelationProperties#getRequestHeaderName()
     * @see RequestCorrelationGenerator#generateRequestId(HttpServletRequest)
     */
    protected String extractRequestId(final HttpServletRequest request)
    {
        // get value from request header
        String requestId = request.getHeader(properties.getRequestHeaderName());
        // if value is blank, generates new one
        if (!StringUtils.hasText(requestId)) {
            requestId = generator.generateRequestId(request);
            logger.debug("Request correlation id was not present, generating new one: {}", requestId);
        }
        return requestId;
    }

    /**
     * Triggers the correlationSet method of all configured interceptors.
     *
     * @param requestCorrelation the correlation id holder
     */
    protected void triggerInterceptorsSet(final RequestCorrelation requestCorrelation)
    {
        for (RequestCorrelationInterceptor interceptor : interceptors) {
            interceptor.afterCorrelationSet(requestCorrelation);
        }
    }

    /**
     * Triggers the cleanUp method of all configured interceptors.
     *
     * @param requestCorrelation the correlation id holder
     */
    protected void triggerInterceptorsCleanup(final RequestCorrelation requestCorrelation)
    {
        for (RequestCorrelationInterceptor interceptor : interceptors) {
            interceptor.cleanUp(requestCorrelation);
        }
    }

    /**
     * "Enriches" the request.
     *
     * @param request the HTTP servlet request
     * @param requestCorrelation the request correlation ids holder
     * @return the servlet request
     */
    protected HttpServletRequest enrichRequest(
            final HttpServletRequest request,
            final RequestCorrelation requestCorrelation)
    {
        final CorrelatedHttpServletRequest enrichedRequest = new CorrelatedHttpServletRequest(request);
        // set request attributes (used by RequestCorrelationUtils)
        enrichedRequest.setAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME, requestCorrelation);

        // set request headers, if enabled and valued
        String sessionId;
        String requestId;
        if (StringUtils.hasText(sessionId = requestCorrelation.getSessionId())) { // NOSONAR
            enrichedRequest.setHeader(properties.getSessionHeaderName(), sessionId);
        }
        if (StringUtils.hasText(requestId = requestCorrelation.getRequestId())) { // NOSONAR
            enrichedRequest.setHeader(properties.getRequestHeaderName(), requestId);
        }
        return enrichedRequest;
    }

    /**
     * "Enriches" the response.
     *
     * @param response the HTTP servlet response
     * @param requestCorrelation the request correlation ids holder
     * @return the servlet response
     */
    protected HttpServletResponse enrichResponse(
            final HttpServletResponse response,
            final RequestCorrelation requestCorrelation)
    {
        // set response headers, if enabled and valued
        String sessionId;
        String requestId;
        if (StringUtils.hasText(sessionId = requestCorrelation.getSessionId())) { // NOSONAR
            response.setHeader(properties.getSessionHeaderName(), sessionId);
        }
        if (StringUtils.hasText(requestId = requestCorrelation.getRequestId())) { // NOSONAR
            response.setHeader(properties.getRequestHeaderName(), requestId);
        }
        return response;
    }

    /**
     * An HTTP servlet wrapper that allows to register additional HTTP headers.
     */
    public static class CorrelatedHttpServletRequest extends HttpServletRequestWrapper
    {
        /**
         * Map with additional customizable headers.
         */
        private final Map<String, String> additionalHeaders = new ConcurrentHashMap<>();

        /**
         * Creates an HttpServletRequest adaptor wrapping the given request object.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public CorrelatedHttpServletRequest(
                final @NonNull HttpServletRequest request)
        {
            super(request);
        }

        /**
         * Sets a header value.
         *
         * @param key   the header name
         * @param value the header value
         */
        public void setHeader(
                final @NonNull String key,
                final @NonNull String value)
        {
            additionalHeaders.put(key, value);
        }

        @Override
        public String getHeader(final @NonNull String name)
        {
            // check the custom headers first
            if (additionalHeaders.containsKey(name)) {
                return additionalHeaders.get(name);
            }
            // else return from into the original wrapped object
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(
                final @NonNull String name)
        {
            // create a list to hold merged header values
            final List<String> values = new ArrayList<>();
            // pick from custom headers if exists into that set
            if (additionalHeaders.containsKey(name)) {
                values.add(additionalHeaders.get(name));
            } else {
                values.addAll(Collections.list(super.getHeaders(name)));
            }
            // to enumeration
            return Collections.enumeration(values);
        }

        @Override
        public Enumeration<String> getHeaderNames()
        {
            // create a set to hold merged header names
            final Set<String> names = new LinkedHashSet<>();
            // first add the custom header names
            names.addAll(additionalHeaders.keySet());
            // now add the headers from the wrapped request object
            names.addAll(Collections.list(super.getHeaderNames()));
            // to enumeration
            return Collections.enumeration(names);
        }
    }
}
