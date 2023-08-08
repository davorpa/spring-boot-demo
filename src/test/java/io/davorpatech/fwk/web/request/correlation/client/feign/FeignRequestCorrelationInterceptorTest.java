package io.davorpatech.fwk.web.request.correlation.client.feign;

import feign.RequestTemplate;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationConstants;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationTestUtils;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link FeignRequestCorrelationInterceptor} class.
 */
class FeignRequestCorrelationInterceptorTest
{
    private static final String SESSION_ID = "TEST_SESSION_ID";
    private static final String REQUEST_ID = "TEST_REQUEST_ID";

    private RequestCorrelationProperties properties;
    private FeignRequestCorrelationInterceptor instance;

    @BeforeEach
    public void setUp() throws Exception
    {
        properties = new RequestCorrelationProperties();
        instance = new FeignRequestCorrelationInterceptor(properties);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldSetHeaders()
    {
        // given
        RequestCorrelationTestUtils.setCorrelatingIds(SESSION_ID, REQUEST_ID);
        final RequestTemplate request = new RequestTemplate();

        // when
        instance.apply(request);

        // then
        assertTrue(request.headers().containsKey(properties.getSessionHeaderName()));
        assertEquals(1, request.headers().get(properties.getSessionHeaderName()).size());
        assertEquals(SESSION_ID, request.headers().get(properties.getSessionHeaderName()).iterator().next());
        assertTrue(request.headers().containsKey(properties.getRequestHeaderName()));
        assertEquals(1, request.headers().get(properties.getRequestHeaderName()).size());
        assertEquals(REQUEST_ID, request.headers().get(properties.getRequestHeaderName()).iterator().next());
    }

    @Test
    void shouldSetCustomHeaders()
    {
        // given
        final String customSessionHeader = "My-Session";
        final String customRequestHeader = "My-Request";
        properties.setSessionHeaderName(customSessionHeader);
        properties.setRequestHeaderName(customRequestHeader);
        RequestCorrelationTestUtils.setCorrelatingIds(SESSION_ID, REQUEST_ID);
        final RequestTemplate request = new RequestTemplate();

        // when
        instance.apply(request);

        // then
        assertFalse(request.headers().containsKey(RequestCorrelationConstants.SESSION_HEADER_NAME));
        assertTrue(request.headers().containsKey(customSessionHeader));
        assertEquals(1, request.headers().get(customSessionHeader).size());
        assertEquals(SESSION_ID, request.headers().get(customSessionHeader).iterator().next());
        assertFalse(request.headers().containsKey(RequestCorrelationConstants.REQUEST_HEADER_NAME));
        assertTrue(request.headers().containsKey(customRequestHeader));
        assertEquals(1, request.headers().get(customRequestHeader).size());
        assertEquals(REQUEST_ID, request.headers().get(customRequestHeader).iterator().next());
    }

    @Test
    void shouldNotSetHeaders()
    {
        // given
        final RequestTemplate request = new RequestTemplate();

        // when
        instance.apply(request);

        // then
        assertFalse(request.headers().containsKey(properties.getSessionHeaderName()));
        assertFalse(request.headers().containsKey(properties.getRequestHeaderName()));
    }
}
