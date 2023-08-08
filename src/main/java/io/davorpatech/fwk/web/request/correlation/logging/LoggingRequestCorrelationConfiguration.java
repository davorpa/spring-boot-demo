package io.davorpatech.fwk.web.request.correlation.logging;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelationInterceptor;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * Adds a {@link RequestCorrelationInterceptor} for propagating
 * the request correlation IDs across the Mapped Diagnostic
 * Context (MDC) logging system.
 *
 * @see RequestCorrelationInterceptor
 * @see io.davorpatech.fwk.web.request.correlation.RequestCorrelationFilter RequestCorrelationFilter
 */
@Configuration
@ConditionalOnClass(MDC.class)
@ConditionalOnProperty(value = "request.correlation.logging.enabled", matchIfMissing = true)
public class LoggingRequestCorrelationConfiguration
{
    @Bean
    public RequestCorrelationInterceptor loggingRequestCorrelationInterceptor(
            final @NonNull RequestCorrelationProperties properties)
    {
        return new Slf4jMdcRequestCorrelationInterceptor(properties);
    }
}
