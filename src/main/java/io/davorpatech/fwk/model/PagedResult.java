package io.davorpatech.fwk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.Collection;

public class PagedResult<T extends ValueObject>
{
    public final Collection<T> data;

    public final long totalElements;

    public final int pageNumber;

    public final int totalPages;

    @JsonProperty("isFirst")
    public final boolean first;
    @JsonProperty("isLast")
    public final boolean last;
    @JsonProperty("hasNext")
    public final boolean hasNext;
    @JsonProperty("hasPrevious")
    public final boolean hasPrevious;

    public PagedResult( // NOSONAR
            final @NonNull Collection<T> data,
            final long totalElements,
            final int pageNumber, final int totalPages,
            final boolean first,
            final boolean last,
            final boolean hasNext,
            final boolean hasPrevious)
    {
        this.data = data;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public Collection<T> getData() {
        return data;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }
}
