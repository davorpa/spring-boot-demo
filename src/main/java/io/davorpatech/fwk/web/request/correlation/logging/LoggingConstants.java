package io.davorpatech.fwk.web.request.correlation.logging;

/**
 * Lists the constants used by the request correlation component
 * (logging context feature).
 */
public interface LoggingConstants // NOSONAR
{
    /**
     * The default key value used to propagate the session correlation id
     * into the MDC logging context.
     */
    String SESSION_MDC_KEY = "httpSessionId";

    /**
     * The default key value used to propagate the request correlation id
     * into the MDC logging context.
     */
    String REQUEST_MDC_KEY = "httpRequestId";
}
