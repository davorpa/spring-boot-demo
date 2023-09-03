package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.model.PagedResult;
import io.davorpatech.fwk.service.ServiceCommonSupport;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@Validated
public class ClaseServiceImpl
        extends ServiceCommonSupport
        implements ClaseService
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
    public PagedResult<ClaseDTO> findAll(
            final @Valid FindClasesInput query) {
        int pageSize = query.getPageSize();
        int pageNumber = query.getPageNumber();
        final Sort sort = query.getSort();
        // do search using resolved find all arguments
        final Page<ClaseDTO> page;
        if (pageSize > 0) { // paged search
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            page = claseRepository.findAll(pageable)
                    .map(this::mapEntityToDto);
        } else { // unpaged search
            page = new PageImpl<>(claseRepository.findAll(sort))
                    .map(this::mapEntityToDto);
        }
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Override
    public ClaseDTO findById(
            final Long id) {
        return claseRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(ClaseConstants.DOMAIN_NAME, id));
    }

    @Override
    public ClaseDTO findByCodigo(
            final String codigo) {
        return claseRepository.findByCodigo(codigo)
                .map(this::mapEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(AlumnoConstants.DOMAIN_NAME, codigo));
    }

    @Override
    @Transactional
    public ClaseDTO create(
            final @Valid CreateClaseInput input) {
        // map create DTO to entity
        Clase entity = new Clase();
        entity.setCodigo(input.getCodigo());
        entity.setNombre(input.getNombre());
        // save/persist
        entity = claseRepository.save(entity);
        // map persisted entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public ClaseDTO update(
            final @Valid UpdateClaseInput input) {
        Long id = input.getId();
        // find record which operate with
        Clase entity = claseRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(ClaseConstants.DOMAIN_NAME, id));
        // transfer each update DTO field to entity record
        entity.setCodigo(input.getCodigo());
        entity.setNombre(input.getNombre());
        // save/merge
        entity = claseRepository.save(entity);
        // map merged entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(
            final Long id) {
        Clase entity = claseRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(ClaseConstants.DOMAIN_NAME, id));
        claseRepository.delete(entity);
    }

    @NonNull
    protected ClaseDTO mapEntityToDto(
            final @NonNull Clase entity)
    {
        return new ClaseDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre()
        );
    }
}
