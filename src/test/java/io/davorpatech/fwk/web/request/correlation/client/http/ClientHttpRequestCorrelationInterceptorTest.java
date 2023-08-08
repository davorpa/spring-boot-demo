package io.davorpatech.fwk.web.request.correlation.client.http;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelationConstants;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationTestUtils;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the {@link ClientHttpRequestCorrelationInterceptor} class.
 */
class ClientHttpRequestCorrelationInterceptorTest
{
    private static final String SESSION_ID = "TEST_SESSION_ID";
    private static final String REQUEST_ID = "TEST_REQUEST_ID";

    private RequestCorrelationProperties properties;
    private ClientHttpRequestCorrelationInterceptor instance;

    @BeforeEach
    public void setUp() throws Exception
    {
        properties = new RequestCorrelationProperties();
        instance = new ClientHttpRequestCorrelationInterceptor(properties);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldSetHeaders() throws IOException
    {
        // given
        RequestCorrelationTestUtils.setCorrelatingIds(SESSION_ID, REQUEST_ID);

        final HttpRequest request = mock(HttpRequest.class);
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        final byte[] body = new byte[0];

        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // when
        instance.intercept(request, body, execution);

        // then
        assertTrue(request.getHeaders().containsKey(properties.getSessionHeaderName()));
        assertEquals(SESSION_ID, request.getHeaders().getFirst(properties.getSessionHeaderName()));
        assertTrue(request.getHeaders().containsKey(properties.getRequestHeaderName()));
        assertEquals(REQUEST_ID, request.getHeaders().getFirst(properties.getRequestHeaderName()));
        verify(execution).execute(request, body);
    }

    @Test
    void shouldSetCustomHeaders() throws IOException
    {
        // given
        final String customSessionHeader = "My-Session";
        final String customRequestHeader = "My-Request";
        properties.setSessionHeaderName(customSessionHeader);
        properties.setRequestHeaderName(customRequestHeader);
        RequestCorrelationTestUtils.setCorrelatingIds(SESSION_ID, REQUEST_ID);

        final HttpRequest request = mock(HttpRequest.class);
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        final byte[] body = new byte[0];

        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // when
        instance.intercept(request, body, execution);

        // then
        assertFalse(request.getHeaders().containsKey(RequestCorrelationConstants.SESSION_HEADER_NAME));
        assertTrue(request.getHeaders().containsKey(customSessionHeader));
        assertEquals(SESSION_ID, request.getHeaders().getFirst(customSessionHeader));
        assertFalse(request.getHeaders().containsKey(RequestCorrelationConstants.REQUEST_HEADER_NAME));
        assertTrue(request.getHeaders().containsKey(customRequestHeader));
        assertEquals(REQUEST_ID, request.getHeaders().getFirst(customRequestHeader));
        verify(execution).execute(request, body);
    }


    @Test
    void shouldNotSetHeaders() throws IOException
    {
        // given
        final HttpRequest request = mock(HttpRequest.class);
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        final byte[] body = new byte[0];

        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // when
        instance.intercept(request, body, execution);

        // then
        assertFalse(request.getHeaders().containsKey(properties.getSessionHeaderName()));
        assertFalse(request.getHeaders().containsKey(properties.getRequestHeaderName()));
        verify(execution).execute(request, body);
    }
}
