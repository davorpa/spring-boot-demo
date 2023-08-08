package io.davorpatech.fwk.web.request.correlation;

import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * Request Correlation IDs generation abstraction, allows to implement
 * different strategies for session/request ids generation.
 */
public interface RequestCorrelationGenerator
{
    /**
     * Generates the session id.
     *
     * @param request the request object
     * @return the generated session id
     */
    String generateSessionId(
            final @NonNull HttpServletRequest request);

    /**
     * Generates the request id.
     *
     * @param request the request object
     * @return the generated request id
     */
    String generateRequestId(
            final @NonNull HttpServletRequest request);
}
