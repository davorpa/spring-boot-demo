package io.davorpatech.fwk.web.request.correlation.logging;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelation;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link Slf4jMdcRequestCorrelationInterceptor} class.
 */
class Slf4jMdcRequestCorrelationInterceptorTest
{
    private static final String SESSION_ID = "TEST_SESSION_ID";
    private static final String REQUEST_ID = "TEST_REQUEST_ID";

    private RequestCorrelationProperties properties;
    private Slf4jMdcRequestCorrelationInterceptor instance;

    @BeforeEach
    public void setUp() throws Exception
    {
        properties = new RequestCorrelationProperties();
        instance = new Slf4jMdcRequestCorrelationInterceptor(properties);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        MDC.clear();
    }

    @Test
    void shouldAvoidPutMdcKeysIfRequestCorrelationIdsAreMissing()
    {
        // Given
        RequestCorrelation requestCorrelation = mock(RequestCorrelation.class);

        // When
        instance.afterCorrelationSet(requestCorrelation);

        // Then
        String mdcValue;
        mdcValue = MDC.get(properties.getLogging().getSessionMdcKey());
        assertNull(mdcValue);
        mdcValue = MDC.get(properties.getLogging().getRequestMdcKey());
        assertNull(mdcValue);
    }

    @Test
    void shouldPutMdcKeysIfRequestCorrelationIdsArePresent()
    {
        // Given
        RequestCorrelation requestCorrelation = mock(RequestCorrelation.class);
        when(requestCorrelation.getSessionId()).thenReturn(SESSION_ID);
        when(requestCorrelation.getRequestId()).thenReturn(REQUEST_ID);

        // When
        instance.afterCorrelationSet(requestCorrelation);

        // Then
        String mdcValue;
        mdcValue = MDC.get(properties.getLogging().getSessionMdcKey());
        assertNotNull(mdcValue);
        assertEquals(SESSION_ID, mdcValue);
        mdcValue = MDC.get(properties.getLogging().getRequestMdcKey());
        assertNotNull(mdcValue);
        assertEquals(REQUEST_ID, mdcValue);
    }

    @Test
    void shouldCleanupMdcKeys()
    {
        // Given
        RequestCorrelation requestCorrelation = mock(RequestCorrelation.class);
        when(requestCorrelation.getSessionId()).thenReturn(SESSION_ID);
        when(requestCorrelation.getRequestId()).thenReturn(REQUEST_ID);

        // When
        instance.cleanUp(requestCorrelation);

        // Then
        String mdcValue;
        mdcValue = MDC.get(properties.getLogging().getSessionMdcKey());
        assertNull(mdcValue);
        mdcValue = MDC.get(properties.getLogging().getRequestMdcKey());
        assertNull(mdcValue);
    }
}
