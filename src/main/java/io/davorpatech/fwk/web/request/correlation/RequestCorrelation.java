package io.davorpatech.fwk.web.request.correlation;

/**
 * Holder for the request correlation ids.
 */
public interface RequestCorrelation
{
    /**
     * Returns the request correlation id.
     *
     * @return the request correlation id
     */
    String getRequestId();

    /**
     * Returns the session correlation id.
     *
     * @return the session correlation id
     */
    String getSessionId();
}
