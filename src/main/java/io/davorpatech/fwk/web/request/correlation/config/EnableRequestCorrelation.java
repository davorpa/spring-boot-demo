package io.davorpatech.fwk.web.request.correlation.config;

import io.davorpatech.fwk.web.request.correlation.client.feign.FeignCorrelationConfiguration;
import io.davorpatech.fwk.web.request.correlation.client.http.ClientHttpCorrelationConfiguration;
import io.davorpatech.fwk.web.request.correlation.errorattr.CorrelationErrorAttributeConfiguration;
import io.davorpatech.fwk.web.request.correlation.logging.LoggingRequestCorrelationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables automatic request correlation by assigning per each request
 * unique identifier that afterward is being propagated through
 * {@code 'X-Request-Id'} and {@code 'X-Session-Id'} headers or,
 * if enabled, to the logging system too.
 *
 * <p>
 * By default, the identifiers will be generated using random {@code UUID}.
 *
 * <p>
 * The header will be automatically propagated through any Spring configured
 * {@link org.springframework.web.client.RestTemplate RestTemplate} beans,
 * Feign clients or MDC logging system.
 *
 * @see io.davorpatech.fwk.web.request.correlation.RequestCorrelation
 *      RequestCorrelation
 * @see RequestCorrelationConfiguration
 * @see io.davorpatech.fwk.web.request.correlation.RequestCorrelationGenerator
 *      RequestCorrelationGenerator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
        RequestCorrelationConfiguration.class,
        ClientHttpCorrelationConfiguration.class,
        FeignCorrelationConfiguration.class,
        LoggingRequestCorrelationConfiguration.class,
        CorrelationErrorAttributeConfiguration.class
})
public @interface EnableRequestCorrelation
{

}
