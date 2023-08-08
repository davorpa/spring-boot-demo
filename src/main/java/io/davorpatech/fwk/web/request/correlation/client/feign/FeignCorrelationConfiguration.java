package io.davorpatech.fwk.web.request.correlation.client.feign;

import feign.Feign;
import feign.RequestInterceptor;
import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * Adds Feign's {@link RequestInterceptor} for
 * propagating the request correlation IDs across all Feign clients.
 *
 * @see FeignRequestCorrelationInterceptor
 */
@Configuration
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty(value = "request.correlation.client.feign.enabled", matchIfMissing = true)
public class FeignCorrelationConfiguration
{
    @Bean
    public RequestInterceptor feignRequestCorrelationInterceptor(
            final @NonNull RequestCorrelationProperties properties)
    {
        return new FeignRequestCorrelationInterceptor(properties);
    }
}
