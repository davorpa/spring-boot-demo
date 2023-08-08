package io.davorpatech.fwk.web.request.correlation;

import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Base implementation of {@link RequestCorrelationGenerator}.
 *
 * Uses {@link UUID#randomUUID()} for generating new requests ids
 * and the {@link HttpSession}'s id for new session ids.
 */
public class DefaultRequestCorrelationGenerator implements RequestCorrelationGenerator
{
    /**
     * Generates new session id from the {@link HttpSession}'s id.
     *
     * @return the session id
     */
    @Override
    public String generateSessionId(
            final @NonNull HttpServletRequest request)
    {
        final HttpSession session = request.getSession();
        return session.getId();
    }

    /**
     * Generates new request id as random UUID.
     *
     * @return a random UUID
     */
    @Override
    public String generateRequestId(
            final @NonNull HttpServletRequest request)
    {
        return UUID.randomUUID().toString();
    }
}
