package io.davorpatech.fwk.web.request.correlation.config;

import io.davorpatech.fwk.web.request.correlation.DefaultRequestCorrelationGenerator;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationFilter;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationGenerator;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Configures the request correlation filter.
 *
 * @see EnableRequestCorrelation
 */
@Configuration
@EnableConfigurationProperties(RequestCorrelationProperties.class)
public class RequestCorrelationConfiguration
{
    @Autowired(required = false)
    private List<RequestCorrelationInterceptor> interceptors = new ArrayList<>();

    @Bean
    @ConditionalOnMissingBean(RequestCorrelationGenerator.class)
    public RequestCorrelationGenerator requestCorrelationGenerator()
    {
        return new DefaultRequestCorrelationGenerator();
    }

    @Bean
    public RequestCorrelationFilter requestCorrelationFilter(
            final @NonNull RequestCorrelationGenerator generator,
            final @NonNull RequestCorrelationProperties properties)
    {
        return new RequestCorrelationFilter(generator, interceptors, properties);
    }

    @Bean
    public FilterRegistrationBean<RequestCorrelationFilter> requestCorrelationFilterBean(
            final @NonNull RequestCorrelationFilter correlationFilter,
            final @NonNull RequestCorrelationProperties properties)
    {
        final FilterRegistrationBean<RequestCorrelationFilter> filterRegistration =
                new FilterRegistrationBean<>();
        filterRegistration.setFilter(correlationFilter);
        filterRegistration.setMatchAfter(false);
        filterRegistration.setDispatcherTypes(EnumSet.of(
                DispatcherType.REQUEST,
                DispatcherType.FORWARD,
                DispatcherType.ASYNC));
        filterRegistration.setAsyncSupported(true);
        int filterOrder = properties.getFilterOrderFrom().offset + properties.getFilterOrder();
        filterRegistration.setOrder(filterOrder);
        return filterRegistration;
    }
}
