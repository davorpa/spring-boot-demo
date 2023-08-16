package io.davorpatech.fwk.web.request.correlation.errorattr;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer;
import io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * Specific {@link Configuration} to register into the Spring bean's context
 * those necessary beans in charge of publish the request correlation ids
 * as error attributes which can be logged or presented to the user.
 *
 * @see ErrorAttributesCustomizer
 * @see CorrelationErrorAttributeAdder
 */
@Configuration
@ConditionalOnBean(ExtensibleErrorAttributes.class)
@ConditionalOnProperty(value = "server.error.customizers.correlation.enabled", matchIfMissing = true)
public class CorrelationErrorAttributeConfiguration
{
    @Bean
    @ConditionalOnMissingBean(value = CorrelationErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    public CorrelationErrorAttributeAdder correlationErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new CorrelationErrorAttributeAdder(customizerProperties);
    }
}
