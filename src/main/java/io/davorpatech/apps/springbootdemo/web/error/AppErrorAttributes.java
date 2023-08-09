package io.davorpatech.apps.springbootdemo.web.error;

import io.davorpatech.fwk.web.request.correlation.RequestCorrelation;
import io.davorpatech.fwk.web.request.correlation.RequestCorrelationConstants;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom impleentation of {@link org.springframework.boot.web.servlet.error.ErrorAttributes}
 * for this application.
 *
 * <p>Provides the following attributes when possible:
 * <ul>
 * <li>{@code timestamp} - The time that the errors were extracted
 * <li>{@code correlation} - The unique request correlation that identifies current
 * HTTP request (if configured)
 * <li>{@code status} - The status code
 * <li>{@code error} - The error reason
 * <li>{@code exception} - The class name of the root exception (if configured)
 * <li>{@code message} - The exception message (if configured)
 * <li>{@code errors} - Any {@link org.springframework.validation.ObjectError}s from a
 * {@link org.springframework.validation.BindingResult} exception (if configured)
 * <li>{@code trace} - The exception stack trace (if configured)
 * <li>{@code path} - The URL path when the exception was raised
 * </ul>
 *
 * @see ErrorAttributes
 * @see DefaultErrorAttributes
 */
@Component
public class AppErrorAttributes extends DefaultErrorAttributes
{
    protected final Environment environment;

    public AppErrorAttributes(
            final Environment environment) {
        Assert.notNull(environment, "Parameter 'environment' must not be null!");
        this.environment = environment;
    }


    @Override
    public Map<String, Object> getErrorAttributes(
            final WebRequest webRequest,
            final ErrorAttributeOptions options)
    {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        if (Boolean.TRUE.equals(environment.getProperty(
                "server.error.include-request-correlation",
                Boolean.class, Boolean.TRUE))) {
            addRequestCorrelation(errorAttributes, webRequest);
        }
        errorAttributes.putAll(super.getErrorAttributes(webRequest, options));
        // TODO enrich errorAttributes map with more relevant info
        return errorAttributes;
    }

    protected void addRequestCorrelation(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull RequestAttributes requestAttributes)
    {
        RequestCorrelation requestCorrelation = getAttribute(
                requestAttributes, RequestCorrelationConstants.ATTRIBUTE_NAME);
        if (requestCorrelation == null) return;
        errorAttributes.put("correlation", requestCorrelation);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute( // NOSONAR
            final @NonNull RequestAttributes requestAttributes,
            final @NonNull String name)
    {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
