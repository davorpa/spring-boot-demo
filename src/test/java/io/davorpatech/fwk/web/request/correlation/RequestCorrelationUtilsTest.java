package io.davorpatech.fwk.web.request.correlation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests the {@link RequestCorrelationUtils} class.
 */
class RequestCorrelationUtilsTest
{
    @BeforeEach
    public void setUp() throws Exception
    {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldNotRetrieveCorrelatingIds()
    {
        // given
        RequestContextHolder.resetRequestAttributes();

        // when
        final String currentSessionId = RequestCorrelationUtils.getCurrentSessionId();
        final String currentRequestId = RequestCorrelationUtils.getCurrentRequestId();

        // then
        assertNull(currentSessionId);
        assertNull(currentRequestId);
    }

    @Test
    void shouldRetrieveCorrelatingIds()
    {
        // given
        final String sessionId = UUID.randomUUID().toString();
        final String requestId = UUID.randomUUID().toString();
        RequestCorrelationTestUtils.setCorrelatingIds(sessionId, requestId);

        // when
        final String currentSessionId = RequestCorrelationUtils.getCurrentSessionId();
        final String currentRequestId = RequestCorrelationUtils.getCurrentRequestId();

        // then
        assertEquals(sessionId, currentSessionId);
        assertEquals(requestId, currentRequestId);
    }
}
