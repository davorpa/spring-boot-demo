package io.davorpatech.fwk.web.request.correlation.client.http;

import io.davorpatech.fwk.web.request.correlation.config.RequestCorrelationProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Configures any {@link org.springframework.web.client.RestTemplate} bean
 * by adding additional request interceptor that have all the correlation
 * behaviour.
 *
 * @see ClientHttpRequestCorrelationInterceptor
 */
@Configuration
@ConditionalOnClass(InterceptingHttpAccessor.class)
@ConditionalOnProperty(value = "request.correlation.client.http.enabled", matchIfMissing = true)
public class ClientHttpCorrelationConfiguration
{
    @Autowired(required = false)
    private List<InterceptingHttpAccessor> clients = new ArrayList<>();

    @Bean
    public InitializingBean clientsCorrelationInitializer(
            final @NonNull RequestCorrelationProperties properties)
    {
        return () -> {
            if (clients == null || clients.isEmpty()) return;
            // inject the interceptor on each client
            ClientHttpRequestCorrelationInterceptor correlationInterceptor =
                    new ClientHttpRequestCorrelationInterceptor(properties);
            for (InterceptingHttpAccessor client : clients) {
                client.getInterceptors().add(correlationInterceptor);
            }
        };
    }
}
