package io.davorpatech.fwk.web.request.correlation;

/**
 * Lists the constants used by the request correlation component.
 */
public interface RequestCorrelationConstants // NOSONAR
{
    /**
     * The default header name used to propagate the request correlation id.
     */
    String REQUEST_HEADER_NAME = "X-Request-Id";

    /**
     * The default header name used to propagate the session request correlation id.
     */
    String SESSION_HEADER_NAME = "X-Session-Id";

    /**
     * The request attribute name where the request correlation is stored.
     */
    String ATTRIBUTE_NAME = RequestCorrelation.class.getName() +  ".ATTRIBUTE";
}
