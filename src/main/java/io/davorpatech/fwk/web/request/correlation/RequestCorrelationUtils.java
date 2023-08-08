package io.davorpatech.fwk.web.request.correlation;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * A utility class for retrieving the currently bound request correlation ids.
 */
public abstract class RequestCorrelationUtils
{
    private RequestCorrelationUtils()
    {
        throw new AssertionError("No RequestCorrelationUtils instances for you!");
    }

    /**
     * Retrieves the current request correlation holder, if present.
     *
     * @return the request correlation or {@code null}
     */
    public static RequestCorrelation getRequestCorrelation()
    {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            Object correlation = requestAttributes.getAttribute(
                    RequestCorrelationConstants.ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);

            if (correlation instanceof RequestCorrelation) {
                return (RequestCorrelation) correlation;
            }
        }
        return null;
    }

    /**
     * Retrieves the current session correlation id, if present.
     *
     * @return the session correlation id or {@code null}
     * @see #getRequestCorrelation()
     */
    public static String getCurrentSessionId()
    {
        final RequestCorrelation requestCorrelation = getRequestCorrelation();
        if (requestCorrelation != null) {
            return requestCorrelation.getSessionId();
        }
        return null;
    }

    /**
     * Retrieves the current request correlation id, if present.
     *
     * @return the request correlation id or {@code null}
     * @see #getRequestCorrelation()
     */
    public static String getCurrentRequestId()
    {
        final RequestCorrelation requestCorrelation = getRequestCorrelation();
        if (requestCorrelation != null) {
            return requestCorrelation.getRequestId();
        }
        return null;
    }
}
