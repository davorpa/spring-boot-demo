package io.davorpatech.fwk.web.request.correlation.config;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelationConstants;
import io.davorpatech.fwk.web.request.correlation.logging.LoggingProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.Ordered;

/**
 * {@link ConfigurationProperties @ConfigurationProperties}
 * for a request correlation
 */
@ConfigurationProperties(prefix = "request.correlation", ignoreUnknownFields = true)
public class RequestCorrelationProperties
{
    /**
     * The priority order of the request correlation filter.
     *
     * <p>
     * Defaults to {@link Ordered#HIGHEST_PRECEDENCE}, so the correlating ids
     * is set very early in the process, but you may need to override this.
     *
     * For example, if you use shared sessions via the spring-session project,
     * you need to make sure the request correlation filter comes AFTER the
     * Spring Session filter, or the request correlation filter won't get the
     * right request object and your ids will be wrong.
     */
    private int filterOrder = Ordered.HIGHEST_PRECEDENCE;

    /**
     * The starting point of the {@literal filter order} property.
     *
     * <p>
     * This value will be added to the {@code filter-order} property to get
     * the actual order.
     * Defaults to "zero" so that using {@code filter-order} by itself gives
     * predictable results.
     * If you want the {@code filter-order} to be an offset from the Highest
     * Precedence, set the {@code filter-order} to the offset, and
     * {@code filter-order-from} to {@code "highest_precedence"}.
     */
    private FilterOrderOffset filterOrderFrom = FilterOrderOffset.ZERO;

    /**
     * When to enable sessions correlation identification.
     *
     * <p>
     * {@code false} by default, to avoid create new HTTP sessions.
     */
    private boolean enableSessions = false;

    /**
     * Header name for the correlation session id.  Defaults to {@code X-Session-Id}.
     */
    private String sessionHeaderName = RequestCorrelationConstants.SESSION_HEADER_NAME;

    /**
     * Header name for the correlation request id.  Defaults to {@code X-Request-Id}.
     */
    private String requestHeaderName = RequestCorrelationConstants.REQUEST_HEADER_NAME;

    /**
     * Configuration properties used to setup the request correlation logging system.
     */
    @NestedConfigurationProperty
    private final LoggingProperties logging = new LoggingProperties();

    /**
     * @return the current ordering of the request correlation filter
     */
    public int getFilterOrder()
    {
        return filterOrder;
    }

    /**
     * @param filterOrder the filter order to use
     */
    public void setFilterOrder(int filterOrder)
    {
        this.filterOrder = filterOrder;
    }

    /**
     * @return the current ordering offset of the request correlation filter
     */
    public FilterOrderOffset getFilterOrderFrom()
    {
        return filterOrderFrom;
    }

    /**
     *
     * @param filterOrderFrom the filter order offset to use
     */
    public void setFilterOrderFrom(FilterOrderOffset filterOrderFrom)
    {
        this.filterOrderFrom = filterOrderFrom;
    }

    /**
     * @return {@code true} if session handling is enabled.
     */
    public boolean isEnableSessions()
    {
        return enableSessions;
    }

    /**
     * @param enableSessions the enable sessions to use
     */
    public void setEnableSessions(boolean enableSessions)
    {
        this.enableSessions = enableSessions;
    }

    /**
     * Retrieves the header name used to extract the correlation session id.
     *
     * Defaults to {@code X-Session-Id}.
     *
     * @return the header name used to extract the correlation session id
     */
    public String getSessionHeaderName()
    {
        return sessionHeaderName;
    }

    /**
     * Sets the header name used to extract the correlation session id.
     *
     * @param sessionHeaderName the session header name to use
     */
    public void setSessionHeaderName(String sessionHeaderName)
    {
        this.sessionHeaderName = sessionHeaderName;
    }

    /**
     * Retrieves the header name used to extract the correlation request id.
     *
     * Defaults to {@code X-Request-Id}.
     *
     * @return the header name used to extract the correlation request id
     */
    public String getRequestHeaderName()
    {
        return requestHeaderName;
    }

    /**
     * Sets the header name used to extract the correlation request id.
     *
     * @param requestHeaderName the request header name to use
     */
    public void setRequestHeaderName(String requestHeaderName)
    {
        this.requestHeaderName = requestHeaderName;
    }

    /**
     * Gets the configuration properties used to configure the
     * request correlation logging system.
     *
     * @return the logging configuration properties
     */
    public LoggingProperties getLogging() {
        return logging;
    }
}
