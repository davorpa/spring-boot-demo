package io.davorpatech.fwk.web.request.correlation;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * An convenient utility class for request correlations.
 */
public abstract class RequestCorrelationTestUtils
{
    /**
     * Sets the request correlation holding object.
     *
     * @param requestCorrelation the request correlation to set
     * @throws IllegalArgumentException if {@code requestCorrelation} is {@code null}
     */
    public static void setRequestCorrelation(
            final @NonNull RequestCorrelation requestCorrelation)
    {
        Assert.notNull(requestCorrelation, "Parameter 'requestCorrelation' must not be null!");
        RequestContextHolder.getRequestAttributes().setAttribute(
                RequestCorrelationConstants.ATTRIBUTE_NAME,
                requestCorrelation,
                RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Sets the request correlation IDs.
     *
     * @param sessionId the session id to set
     * @param requestId the request id to set
     */
    public static void setCorrelatingIds(
            final @Nullable String sessionId,
            final @Nullable String requestId)
    {
        setRequestCorrelation(new DefaultRequestCorrelation(sessionId, requestId));
    }
}
