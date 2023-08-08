package io.davorpatech.fwk.web.request.correlation;

import org.springframework.lang.NonNull;

/**
 * An interceptor that can be used to provide additional behavior
 * related to the generation of Request Correlation IDs performed
 * during the lifecycle of a {@link RequestCorrelationFilter}
 * component.
 *
 * @see RequestCorrelationFilter
 */
public interface RequestCorrelationInterceptor
{
    /**
     * Callback method called whenever the correlation id has been assigned
     * for the current request.
     * <p>
     * No matter whether it has set from the request header or a new id has
     * been generated for incoming request.
     *
     * @param requestCorrelation the holder for request correlation ids,
     *                           never {@code null}
     */
    void afterCorrelationSet(final @NonNull RequestCorrelation requestCorrelation);

    /**
     * Callback method called always just after filter chain has completed.
     *
     * @param requestCorrelation the holder for request correlation ids,
     *                           never {@code null}
     */
    void cleanUp(final @NonNull RequestCorrelation requestCorrelation);

}
