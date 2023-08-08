package io.davorpatech.fwk.web.request.correlation.config;

import org.springframework.core.Ordered;

/**
 * This enum defines valid offsets for the filter order.
 *
 * The actual filter order will be the stated order plus the
 * chosen offset.
 *
 * For example, a filter order of 102 and an offset of
 * HIGHEST_PRECEDENCE will result in a filter that is 102 after
 * the highest precedence filter.
 */
public enum FilterOrderOffset
{
    HIGHEST_PRECEDENCE(Ordered.HIGHEST_PRECEDENCE),
    LOWEST_PRECEDENCE(Ordered.LOWEST_PRECEDENCE),
    ZERO(0);

    public final int offset;

    FilterOrderOffset(final int offset)
    {
        this.offset = offset;
    }
}
