package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AsistenciaRepository;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Asistencia;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AsistenciaService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.exception.NoSuchForeignalEntityException;
import io.davorpatech.fwk.model.PagedResult;
import io.davorpatech.fwk.service.ServiceCommonSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@Validated
public class AsistenciaServiceImpl // NOSONAR
        extends ServiceCommonSupport // NOSONAR
        implements AsistenciaService // NOSONAR
{
    private final AsistenciaRepository asistenciaRepository;

    private final ClaseRepository claseRepository;

    private final AlumnoRepository alumnoRepository;

    /**
     * Constructs a new {@link AsistenciaServiceImpl} with the given arguments.
     *
     * @param asistenciaRepository the asistencia repository, never {@code null}
     * @param claseRepository      the clase repository, never {@code null}
     * @param alumnoRepository      the alumno repository, never {@code null}
     */
    public AsistenciaServiceImpl(
            final AsistenciaRepository asistenciaRepository,
            final ClaseRepository claseRepository,
            final AlumnoRepository alumnoRepository)
    {
        this.asistenciaRepository = Objects.requireNonNull(
                asistenciaRepository, "AsistenciaRepository must not be null!");
        this.claseRepository = Objects.requireNonNull(
                claseRepository, "ClaseRepository must not be null!");
        this.alumnoRepository = Objects.requireNonNull(
                alumnoRepository, "AlumnoRepository must not be null!");
    }

    @Override
    public PagedResult<AsistenciaDTO> findAll(
            final @Valid FindAsistenciasInput query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "clase.id", "alumno.id", "fecha");
        int pageNumber = query.getPageNumber() > 0 ? query.getPageNumber() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNumber, query.getPageSize(), sort);
        Page<AsistenciaDTO> page = asistenciaRepository.findAll(pageable)
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
    public AsistenciaDTO findById(
            final Long id) {
        return asistenciaRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(AsistenciaConstants.DOMAIN_NAME, id));
    }

    @Override
    @Transactional
    public AsistenciaDTO create(
            final @Valid CreateAsistenciaInput input) {
        Long claseId = input.getClaseId();
        Long alumnoId = input.getAlumnoId();
        // map create DTO to entity
        Asistencia entity = new Asistencia();
        entity.setClase(claseRepository.findById(claseId)
                .orElseThrow(NoSuchForeignalEntityException.creater(ClaseConstants.DOMAIN_NAME, claseId)));
        entity.setAlumno(alumnoRepository.findById(alumnoId)
                .orElseThrow(NoSuchForeignalEntityException.creater(AlumnoConstants.DOMAIN_NAME, alumnoId)));
        entity.setFecha(input.getFecha());
        entity.setAsiste(input.isAsiste());
        // save/persist
        entity = asistenciaRepository.save(entity);
        // map persisted entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public AsistenciaDTO update(
            final @Valid UpdateAsistenciaInput input) {
        Long id = input.getId();
        // find record which operate with
        Asistencia entity = asistenciaRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(AsistenciaConstants.DOMAIN_NAME, id));
        // transfer each update DTO field to entity record
        entity.setAsiste(input.isAsiste());
        // save/merge
        entity = asistenciaRepository.save(entity);
        // map merged entity to dto
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(
            final Long id) {
        Asistencia entity = asistenciaRepository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(AsistenciaConstants.DOMAIN_NAME, id));
        asistenciaRepository.delete(entity);
    }

    @NonNull
    protected AsistenciaDTO mapEntityToDto(
            final @NonNull Asistencia entity)
    {
        Clase clase = entity.getClase();
        Alumno alumno = entity.getAlumno();
        return new AsistenciaDTO(
                entity.getId(),
                new ClaseDTO(clase.getId(), clase.getCodigo(), clase.getNombre()),
                new AlumnoDTO(alumno.getId(), alumno.getNid(), alumno.getFullname()),
                entity.getFecha(),
                entity.isAsiste()
        );
    }
}
