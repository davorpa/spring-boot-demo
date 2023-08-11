package io.davorpatech.apps.springbootdemo.web.error;

import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorCode;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
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

import javax.servlet.RequestDispatcher;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom implementation of {@link org.springframework.boot.web.servlet.error.ErrorAttributes}
 * for this application.
 *
 * <p>Provides the following attributes when possible:
 * <ul>
 * <li>{@code timestamp} - The time that the errors were extracted.
 * <li>{@code correlation} - The unique request correlation.
 * ({@link io.davorpatech.fwk.web.request.correlation.RequestCorrelation}) that
 * identifies current HTTP request (if configured).
 * <li>{@code status} - The status code.
 * <li>{@code error.reason} - The error reason.
 * <li>{@code error.exception} - The class name of the root exception (if configured).
 * <li>{@code error.message} - The exception message (if configured).
 * <li>{@code error.errors} - Any {@link org.springframework.validation.ObjectError}s.
 * from a {@link org.springframework.validation.BindingResult} exception (if configured
 * and provided by the exception).
 * <li>{@code error.trace} - The exception stack trace (if configured).
 * <li>{@code error.code} - An optional property indicating the human-friendly error
 * code provided by any exception marked with {@link io.davorpatech.fwk.model.ErrorCode}.
 * <li>{@code error.domain} - An optional property indicating the human-friendly domain
 * entity name provided by any exception marked with {@link io.davorpatech.fwk.model.ErrorDomain}.
 * <li>{@code error.identifier} - An optional property with the value/s that identify
 * the domain entity which the error is related for. Provided by any exception marked.
 * with {@link io.davorpatech.fwk.model.Identifiable}.
 * <li>{@code path} - The URL path when the exception was raised.
 * </ul>
 *
 * @see ErrorAttributes
 * @see DefaultErrorAttributes
 */
@Component
public class AppErrorAttributes extends DefaultErrorAttributes
{
    protected static final String ERROR_KEY = "error";

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
        errorAttributes.putAll(reorganizeErrorAttributes(
                super.getErrorAttributes(webRequest, options)));
        completeErrorDetail(errorAttributes, webRequest);
        addPath(errorAttributes, webRequest);
        return errorAttributes;
    }

    protected Map<String, Object> reorganizeErrorAttributes(
            final @NonNull Map<String, Object> errorAttributes)
    {
        final Map<String, Object> attributes;

        // ensure grouped in a "error" node
        final Object previousErrorNode = errorAttributes.get(ERROR_KEY);
        if (previousErrorNode instanceof Map) {
            attributes = (Map<String, Object>) previousErrorNode;
        } else {
            attributes = new LinkedHashMap<>(5);
            attributes.put("reason", String.valueOf(previousErrorNode));
        }

        // Move attributes
        // exception type, message, binding errors, stack trace
        for (String nodeKey : List.of("exception", "message", "errors", "trace")) {
            Object value = errorAttributes.get(nodeKey);
            if (value == null) continue;
            attributes.put(nodeKey, value); // if present
            errorAttributes.remove(nodeKey);
        }

        // rearrange. ensure grouped in a "error" node
        errorAttributes.put(ERROR_KEY, attributes);

        return errorAttributes;
    }

    protected void addPath(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull RequestAttributes requestAttributes)
    {
        String path = getAttribute(requestAttributes, RequestDispatcher.ERROR_REQUEST_URI);
        if (path == null) return;
        errorAttributes.put("path", path);
    }

    protected void completeErrorDetail(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull WebRequest webRequest)
    {
        Map<String, Object> attributes = (Map<String, Object>) errorAttributes
                // initialize as Map if currently absent
                .computeIfAbsent(ERROR_KEY, key -> new LinkedHashMap<>(3));

        // complete "error" node info with the different extension points
        Throwable error = getError(webRequest);
        if (error instanceof ErrorCode) {
            final String errorCode = ((ErrorCode) error).getErrorCode();
            attributes.put("code", errorCode);
        }
        if (error instanceof ErrorDomain) {
            final String domain = ((ErrorDomain) error).getDomain();
            attributes.put("domain", domain);
        }
        if (error instanceof Identifiable) {
            final Object modelId = ((Identifiable) error).getId();
            attributes.put("identifier", modelId);
        }
        if (error instanceof AdditionalArgumentsPopulator) {
            final Map<String, Object> arguments = new LinkedHashMap<>();
            ((AdditionalArgumentsPopulator) error).populate(
                    environment, arguments);
            attributes.put("arguments", arguments);
        }
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
