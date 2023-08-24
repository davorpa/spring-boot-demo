package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.model.PagedResult;
import io.davorpatech.fwk.service.ServiceCommonSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
@Validated
public class AlumnoServiceImpl
        extends ServiceCommonSupport
        implements AlumnoService
{
    private final AlumnoRepository alumnoRepository;

    /**
     * Constructs a new {@link AlumnoServiceImpl} with the given arguments.
     *
     * @param alumnoRepository the alumno repository, never {@code null}
     */
    public AlumnoServiceImpl(final AlumnoRepository alumnoRepository) {
        Assert.notNull(alumnoRepository, "AlumnoRepository must not be null!");
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public PagedResult<AlumnoDTO> findAll(
            final @Valid FindAlumnosInput query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        int pageNumber = query.getPageNumber() > 0 ? query.getPageNumber() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNumber, query.getPageSize(), sort);
        Page<AlumnoDTO> page = alumnoRepository.findAll(pageable)
                .map(this::mapEntityToDto);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
            );
    }

    @Override
    public AlumnoDTO findById(
            final Long id) {
        return alumnoRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(AlumnoConstants.DOMAIN_NAME, id));
    }

    @Override
    public AlumnoDTO findByNid(
            final String nid) {
        return alumnoRepository.findByNid(nid)
                .map(this::mapEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(AlumnoConstants.DOMAIN_NAME, nid));
    }

    @Override
    @Transactional
    public AlumnoDTO create(
            final @Valid CreateAlumnoInput input) {
        // map create DTO to entity
        Alumno entity = new Alumno();
        entity.setNid(input.getNid());
        entity.setFullname(input.getFullname());
        // save/persist
        entity = alumnoRepository.save(entity);
        // map persisted entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public AlumnoDTO update(
            final @Valid UpdateAlumnoInput input) {
        Long id = input.getId();
        // find record which operate with
        Alumno entity = alumnoRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(AlumnoConstants.DOMAIN_NAME, id));
        // transfer each update DTO field to entity record
        entity.setNid(input.getNid());
        entity.setFullname(input.getFullname());
        // save/merge
        entity = alumnoRepository.save(entity);
        // map merged entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(
            final Long id) {
        Alumno entity = alumnoRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(AlumnoConstants.DOMAIN_NAME, id));
        alumnoRepository.delete(entity);
    }

    @NonNull
    protected AlumnoDTO mapEntityToDto(
            final @NonNull Alumno entity)
    {
        return new AlumnoDTO(
                entity.getId(),
                entity.getNid(),
                entity.getFullname()
        );
    }
}
