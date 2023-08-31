package io.davorpatech.fwk.service.entity;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Contract that generically defines the most important read operations
 * for a specific data type.
 *
 * <ul>
 * <li>Retrieve all
 * <li>Retrieve all by some ids
 * <li>Retrieve one by id
 * <li>Check existence by id
 * <li>Count records
 * </ul>
 *
 * @param <T> component type that represents the data object
 * @param <ID> component type identifying that data object
 */
public interface ServiceReadOps<T, ID> // NOSONAR
{
    /**
     * Gets all records representing this domain data.
     *
     * @return a collection with all elements, never {@code null}.
     */
    List<T> findAll();

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     *
     * <p>If some or all ids are not found, no entities are returned for these IDs.
     * <p>Note that the order of elements in the result is not guaranteed.
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values
     * @return guaranteed to be not {@literal null}. The size can be equal or less
     *         than the number of given {@literal ids}
     */
    public List<T> findAllById(
            final @NotNull Iterable<ID> ids);

    /**
     * Gets a concrete record given it identifier.
     *
     * @param id the identifier used to query by detail, never {@code null}.
     * @return never {@code null}, {@literal Optional.empty()} if a record doesn't exist.
     */
    Optional<T> findById(final @NotNull ID id);

    /**
     * Check if exists a concrete record given it identifier.
     *
     * @param id the identifier used to check existence, never {@code null}.
     * @return @code {@code true} if exists.
     */
    boolean existsById(final @NotNull ID id);


    /**
     * Counts the number of records available.
     *
     * @return the number of items in the collection
     */
    default long countAll() {
        final AtomicLong counter = new AtomicLong(0L);
        findAll().stream().forEach(t -> counter.incrementAndGet());
        return counter.get();
    }
}
