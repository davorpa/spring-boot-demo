package io.davorpatech.fwk.web.request.correlation;

/**
 * Base immutable implementation of {@link RequestCorrelation}.
 */
public class DefaultRequestCorrelation implements RequestCorrelation
{
    /**
     * The actual session correlation id.
     */
    private final String sessionId;

    /**
     * The actual request correlation id.
     */
    private final String requestId;

    /**
     * Creates new instance of {@link DefaultRequestCorrelation} class.
     *
     * @param sessionId the session id
     * @param requestId the request id
     */
    public DefaultRequestCorrelation(
            final String sessionId,
            final String requestId)
    {
        this.sessionId = sessionId;
        this.requestId = requestId;
    }

    @Override
    public String getRequestId()
    {
        return requestId;
    }

    @Override
    public String getSessionId()
    {
        return sessionId;
    }
}
