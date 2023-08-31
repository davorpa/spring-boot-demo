package io.davorpatech.fwk.service.entity;

import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.model.Entitier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Base implementation that delegates the most important CRUD operations
 * for a specific data type to the JPA repository on duty.
 *
 * @param <T> component type representing the domain entity
 * @param <ID> component type of the field that uniquely identifies said entity
 *
 * @see #getRepository()
 */
public abstract class AbstractCrudEntityService<T extends Entitier<ID>, ID extends Serializable> // NOSONAR
        extends AbstractReadableEntityService<T, ID> // NOSONAR
        implements CrudEntityService<T, ID> // NOSONAR
{
    @Transactional
    @Override
    public <S extends T> S create(
            final @NotNull @Valid S entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public <S extends T> S update(
            final @NotNull @Valid S entity) {
        Objects.requireNonNull(entity.getId(), "Updated identifier must be non null");
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public void delete(
            final @NotNull @Valid T entity) {
        getRepository().delete(entity);
    }

    @Transactional
    @Override
    public void deleteById(
            final @NotNull ID id) {
        try {
            getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            // Translate dao exception to domain exception
            throw new NoSuchEntityException(getEntityClass(), id);
        }
    }
}
