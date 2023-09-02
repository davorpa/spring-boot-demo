package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.ValueObject;

/**
 * Input Command DTO (Data Transfer Object) that defines the contract of
 * those data objects used to find a domain business entity of a concrete
 * kind.
 */
public interface FindInputCmd extends ValueObject
{
    /**
     * Returns the number of the current page (zero-index based).
     *
     * @return the number of the current page
     */
    int getPageNumber();

    /**
     * Returns the page size; thus is, the number of items to be returned.
     *
     * <p>A size less than one is interpreted by the correspondent find service
     * as a non-paged search.
     *
     * @return the page size
     */
    int getPageSize();
}
