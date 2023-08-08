package io.davorpatech.fwk.web.request.correlation;

import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the {@link RequestCorrelationFilter} class.
 */
class RequestCorrelationFilterTest
{
    private RequestCorrelationFilter instance;

    private RequestCorrelationGenerator generator;

    private List<RequestCorrelationInterceptor> interceptors;

    private RequestCorrelationProperties properties;

    @BeforeEach
    public void setUp() throws Exception
    {
        generator = new DefaultRequestCorrelationGenerator();
        interceptors = new ArrayList<>();
        properties = new RequestCorrelationProperties();
        instance = new RequestCorrelationFilter(generator, interceptors, properties);
    }

    @Test
    void shouldInitiateBothCorrelationIds() throws IOException, ServletException
    {
        // given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        properties.setEnableSessions(true);

        // when
        instance.doFilter(request, response, chain);

        // then
        assertNotNull(request.getAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME));
        assertNotNull(((HttpServletRequest) chain.getRequest()).getHeader(properties.getSessionHeaderName()));
        assertNotNull(((HttpServletResponse) chain.getResponse()).getHeader(properties.getSessionHeaderName()));
        assertNotNull(((HttpServletRequest) chain.getRequest()).getHeader(properties.getRequestHeaderName()));
        assertNotNull(((HttpServletResponse) chain.getResponse()).getHeader(properties.getRequestHeaderName()));
    }

    @Test
    void shouldOnlyInitiateCorrelationRequestId() throws IOException, ServletException
    {
        // given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        properties.setEnableSessions(false);

        // when
        instance.doFilter(request, response, chain);

        // then
        assertNotNull(request.getAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME));
        assertNull(((HttpServletRequest) chain.getRequest()).getHeader(properties.getSessionHeaderName()));
        assertNull(((HttpServletResponse) chain.getResponse()).getHeader(properties.getSessionHeaderName()));
        assertNotNull(((HttpServletRequest) chain.getRequest()).getHeader(properties.getRequestHeaderName()));
        assertNotNull(((HttpServletResponse) chain.getResponse()).getHeader(properties.getRequestHeaderName()));
    }

    @Test
    void shouldUseExistingCorrelationIds() throws IOException, ServletException
    {
        // given
        final String sessionId = UUID.randomUUID().toString();
        final String requestId = UUID.randomUUID().toString();

        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        request.addHeader(properties.getSessionHeaderName(), sessionId);
        request.addHeader(properties.getRequestHeaderName(), requestId);

        properties.setEnableSessions(true);

        // when
        instance.doFilter(request, response, chain);

        // then
        final Object requestCorrelation = request.getAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME);
        assertNotNull(requestCorrelation);
        assertEquals(sessionId, ((RequestCorrelation) requestCorrelation).getSessionId());
        assertEquals(requestId, ((RequestCorrelation) requestCorrelation).getRequestId());

        String headerValue;
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(properties.getSessionHeaderName());
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(properties.getSessionHeaderName());
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(properties.getRequestHeaderName());
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(properties.getRequestHeaderName());
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
    }

    @Test
    void shouldUseExistingCorrelationIdsWithoutSession() throws IOException, ServletException
    {
        // given
        properties.setEnableSessions(false);

        final String sessionId = UUID.randomUUID().toString();
        final String requestId = UUID.randomUUID().toString();

        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        request.addHeader(properties.getSessionHeaderName(), sessionId);
        request.addHeader(properties.getRequestHeaderName(), requestId);

        // when
        instance.doFilter(request, response, chain);

        // then
        final Object requestCorrelation = request.getAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME);
        assertNotNull(requestCorrelation);
        assertEquals(sessionId, ((RequestCorrelation) requestCorrelation).getSessionId());
        assertEquals(requestId, ((RequestCorrelation) requestCorrelation).getRequestId());

        String headerValue;
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(properties.getSessionHeaderName());
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(properties.getSessionHeaderName());
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(properties.getRequestHeaderName());
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(properties.getRequestHeaderName());
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
    }

    @Test
    void shouldUseCustomHeader() throws IOException, ServletException
    {
        // given
        properties.setEnableSessions(true);

        final String sessionHeaderName = "X-SessionTraceId";
        final String requestHeaderName = "X-RequestTraceId";
        properties.setSessionHeaderName(sessionHeaderName);
        properties.setRequestHeaderName(requestHeaderName);

        final String sessionId = UUID.randomUUID().toString();
        final String requestId = UUID.randomUUID().toString();

        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        request.addHeader(sessionHeaderName, sessionId);
        request.addHeader(requestHeaderName, requestId);

        // when
        instance.doFilter(request, response, chain);

        // then
        final Object requestCorrelation = request.getAttribute(RequestCorrelationConstants.ATTRIBUTE_NAME);
        assertNotNull(requestCorrelation);
        assertEquals(sessionId, ((RequestCorrelation) requestCorrelation).getSessionId());
        assertEquals(requestId, ((RequestCorrelation) requestCorrelation).getRequestId());

        String headerValue;
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(sessionHeaderName);
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(sessionHeaderName);
        assertNotNull(headerValue);
        assertEquals(sessionId, headerValue);
        headerValue = ((HttpServletRequest) chain.getRequest()).getHeader(requestHeaderName);
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
        headerValue = ((HttpServletResponse) chain.getResponse()).getHeader(requestHeaderName);
        assertNotNull(headerValue);
        assertEquals(requestId, headerValue);
    }

    @Test
    void shouldInvokeInterceptor() throws IOException, ServletException
    {
        // given
        properties.setEnableSessions(true);

        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();

        final RequestCorrelationInterceptor interceptor = mock(RequestCorrelationInterceptor.class);
        interceptors.add(interceptor);

        // when
        instance.doFilter(request, response, chain);

        // then
        final RequestCorrelation correlationIds = (RequestCorrelation) request.getAttribute(
                RequestCorrelationConstants.ATTRIBUTE_NAME);
        assertNotNull(correlationIds);

        verify(interceptor).afterCorrelationSet(correlationIds);
        verify(interceptor).cleanUp(correlationIds);
    }

    @Test
    void shouldRegisterAdditionalHeadersInRequestWrapper()
    {
        // given
        final String HEADER_NAME = "X-Test-Header";
        final String HEADER_VALUE = "TEST_HEADER";

        final MockHttpServletRequest request = spy(new MockHttpServletRequest());
        RequestCorrelationFilter.CorrelatedHttpServletRequest correlatedRequest =
                new RequestCorrelationFilter.CorrelatedHttpServletRequest(request);

        // when
        correlatedRequest.setHeader(HEADER_NAME, HEADER_VALUE);
        List<String> headers = Collections.list(correlatedRequest.getHeaders(HEADER_NAME));

        // then
        assertEquals(1, headers.size());
        verify(request, never()).getHeaders(HEADER_NAME);
    }

    @Test
    void shouldRegisterAdditionalHeadersInNativeRequest()
    {
        // given
        final String HEADER_NAME = "X-Test-Header";
        final String HEADER_VALUE = "TEST_HEADER";

        final MockHttpServletRequest request = spy(new MockHttpServletRequest());
        RequestCorrelationFilter.CorrelatedHttpServletRequest correlatedRequest =
                new RequestCorrelationFilter.CorrelatedHttpServletRequest(request);

        // when
        request.addHeader(HEADER_NAME, HEADER_VALUE);
        List<String> headers = Collections.list(correlatedRequest.getHeaders(HEADER_NAME));

        // then
        assertEquals(1, headers.size());
        verify(request).getHeaders(HEADER_NAME);
    }

    @Test
    void shouldListAllAdditionalHeaderNames()
    {
        // given
        final String HEADER_NAME = "X-Test-Header";
        final String HEADER_VALUE = "TEST_HEADER";

        final MockHttpServletRequest request = spy(new MockHttpServletRequest());
        RequestCorrelationFilter.CorrelatedHttpServletRequest correlatedRequest =
                new RequestCorrelationFilter.CorrelatedHttpServletRequest(request);

        // when
        request.addHeader(HEADER_NAME, HEADER_VALUE);
        correlatedRequest.setHeader(HEADER_NAME, HEADER_VALUE);
        List<String> headerNames = Collections.list(correlatedRequest.getHeaderNames());

        // then
        assertEquals(1, headerNames.size());
        verify(request).getHeaderNames();
    }
}
