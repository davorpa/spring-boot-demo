package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.service.data.AbstractDataService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@Validated
public class ClaseServiceImpl // NOSONAR
        extends AbstractDataService<Long, Clase, ClaseDTO, FindClasesInput, CreateClaseInput, UpdateClaseInput> // NOSONAR
        implements ClaseService // NOSONAR
{
    private final ClaseRepository claseRepository;

    /**
     * Constructs a new {@link ClaseServiceImpl} with the given arguments.
     *
     * @param claseRepository the clase repository, never {@code null}
     */
    public ClaseServiceImpl(
            final ClaseRepository claseRepository)
    {
        this.claseRepository = Objects.requireNonNull(
                claseRepository, "ClaseRepository must not be null!");
    }

    @Override
    public String getDomainName() {
        return ClaseConstants.DOMAIN_NAME;
    }

    @Override
    protected JpaRepository<Clase, Long> getRepository() {
        return claseRepository;
    }

    @Override
    protected Sort getDefaultFindSort() {
        return Sort.by(Sort.Order.asc("id"));
    }

    @Override
    protected ClaseDTO convertEntityToDto(final Clase entity) {
        return new ClaseDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre()
        );
    }

    @Override
    protected Clase convertCreateToEntity(final CreateClaseInput input) {
        Clase entity = new Clase();
        entity.setCodigo(input.getCodigo());
        entity.setNombre(input.getNombre());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            final Clase entity, final UpdateClaseInput input) {
        entity.setCodigo(input.getCodigo());
        entity.setNombre(input.getNombre());
    }

    @Override
    public ClaseDTO findByCodigo(
            final String codigo) {
        return claseRepository.findByCodigo(codigo)
                .map(this::convertEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(getDomainName(), codigo));
    }
}
