package io.davorpatech.fwk.web.request.correlation;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test the {@link DefaultRequestCorrelationGenerator} class
 */
class DefaultRequestCorrelationGeneratorTest
{
    @Test
    void shouldGenerateSessionId()
    {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        final String sessionId = new DefaultRequestCorrelationGenerator().generateSessionId(request);

        // then
        assertNotNull(sessionId);
    }

    @Test
    void shouldGenerateRequestId()
    {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        final String requestId = new DefaultRequestCorrelationGenerator().generateRequestId(request);

        // then
        assertNotNull(requestId);
    }
}
