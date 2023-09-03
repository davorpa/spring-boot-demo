package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.service.data.AbstractDataService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ClaseServiceImpl // NOSONAR
        extends AbstractDataService< // NOSONAR
            ClaseRepository, // NOSONAR
            Long, Clase, ClaseDTO, // NOSONAR
            FindClasesInput, CreateClaseInput, UpdateClaseInput> // NOSONAR
        implements ClaseService // NOSONAR
{
    /**
     * Constructs a new {@link ClaseServiceImpl} with the given arguments.
     *
     * @param claseRepository the clase repository, never {@code null}
     */
    public ClaseServiceImpl(
            final ClaseRepository claseRepository)
    {
        super(claseRepository, ClaseConstants.DOMAIN_NAME);
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
        return repository.findByCodigo(codigo)
                .map(this::convertEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(domainName, codigo));
    }
}
