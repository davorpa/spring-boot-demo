package io.davorpatech.fwk.service.entity;

import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.validation.ValidatedGroups;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnDelete;
import io.davorpatech.fwk.validation.groups.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Contract that generically defines the most important write operations
 * for a specific data type.
 *
 * <ul>
 * <li>Create
 * <li>Update
 * <li>Delete
 * </ul>
 *
 * @param <T> component type that represents the data object
 * @param <ID> component type identifying that data object
 */
public interface ServiceWriteOps<T, ID> // NOSONAR
{
    /**
     * Creates a new record.
     *
     * @param <S> the component type of the data object to persist.
     * @param entity the data object to persist, never {@code null}
     * @return the persisted data object, never {@code null}
     */
    @ValidatedGroups({ OnCreate.class })
    <S extends T> S create(
            final @NotNull @Valid S entity);

    /**
     * Updates an existent record.
     *
     * @param <S> the component type of the data object to merge.
     * @param entity the data object to merge, never {@code null}
     * @return the merged data object, never {@code null}
     */
    @ValidatedGroups({ OnUpdate.class })
    <S extends T> S update(
            final @NotNull @Valid S entity);

    /**
     * Removes an existent record.
     *
     * @param entity the data object to remove, never {@code null}
     */
    @ValidatedGroups({ OnDelete.class })
    void delete(
            final @NotNull @Valid T entity);

    /**
     * Removes an existent record given it identifier.
     *
     * @param id the identifier of record to remove, never {@code null}
     * @throws NoSuchEntityException if the record identified by the given
     *         {@literal id} is not found
     */
    void deleteById(
            final @NotNull ID id);
}
