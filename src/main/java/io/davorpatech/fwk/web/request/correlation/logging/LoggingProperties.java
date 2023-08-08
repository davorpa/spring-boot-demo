package io.davorpatech.fwk.web.request.correlation.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties}
 * for a request correlation logging system.
 */
@ConfigurationProperties(prefix = "request.correlation.logging", ignoreUnknownFields = true)
public class LoggingProperties
{
    /**
     * When to enable the MDC logging capabilities key for the request correlation.
     *
     * Defaults to {@code true}.
     */
    private boolean enabled = true;

    /**
     * MDC key for the correlation session id.
     *
     * Defaults to {@code httpSessionId}.
     */
    private String sessionMdcKey = LoggingConstants.SESSION_MDC_KEY;

    /**
     * MDC key for the correlation request id.
     *
     * Defaults to {@code httpRequestId}.
     */
    private String requestMdcKey = LoggingConstants.REQUEST_MDC_KEY;

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(
            final boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getSessionMdcKey()
    {
        return sessionMdcKey;
    }

    public void setSessionMdcKey(
            final String sessionMdcKey)
    {
        this.sessionMdcKey = sessionMdcKey;
    }

    public String getRequestMdcKey()
    {
        return requestMdcKey;
    }

    public void setRequestMdcKey(
            final String requestMdcKey)
    {
        this.requestMdcKey = requestMdcKey;
    }
}
