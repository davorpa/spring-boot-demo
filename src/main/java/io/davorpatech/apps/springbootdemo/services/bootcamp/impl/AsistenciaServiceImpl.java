package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AsistenciaRepository;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Asistencia;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AsistenciaService;
import io.davorpatech.fwk.exception.NoSuchForeignalEntityException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class AsistenciaServiceImpl // NOSONAR // NOSONAR
        extends JpaBasedDataService< // NOSONAR
                    AsistenciaRepository, // NOSONAR
                    Long, Asistencia, AsistenciaDTO, // NOSONAR
                    FindAsistenciasInput, CreateAsistenciaInput, UpdateAsistenciaInput> // NOSONAR
        implements AsistenciaService // NOSONAR
{
    private final ClaseRepository claseRepository;

    private final AlumnoRepository alumnoRepository;

    /**
     * Constructs a new {@link AsistenciaServiceImpl} with the given arguments.
     *
     * @param asistenciaRepository the asistencia repository, never {@code null}
     * @param claseRepository      the clase repository, never {@code null}
     * @param alumnoRepository     the alumno repository, never {@code null}
     */
    public AsistenciaServiceImpl(
            final AsistenciaRepository asistenciaRepository,
            final ClaseRepository claseRepository,
            final AlumnoRepository alumnoRepository)
    {
        super(asistenciaRepository, AsistenciaConstants.DOMAIN_NAME);
        Assert.notNull(claseRepository, "ClaseRepository must not be null!");
        Assert.notNull(alumnoRepository, "AlumnoRepository must not be null!");
        this.claseRepository = claseRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    protected Sort getDefaultFindSort() {
        return Sort.by(
                Sort.Order.asc("clase.id"),
                Sort.Order.asc("alumno.id"),
                Sort.Order.asc("fecha"));
    }

    @Override
    protected AsistenciaDTO convertEntityToDto(final Asistencia entity) {
        Clase clase = entity.getClase();
        Alumno alumno = entity.getAlumno();
        return new AsistenciaDTO(
                entity.getId(),
                new ClaseDTO(clase.getId(), clase.getCodigo(), clase.getNombre()),
                new AlumnoDTO(alumno.getId(), alumno.getNid(), alumno.getFullname(), alumno.getEmail()),
                entity.getFecha(),
                entity.isAsiste()
        );
    }

    @Override
    protected Asistencia convertCreateToEntity(final CreateAsistenciaInput input) {
        Long claseId = input.getClaseId();
        Long alumnoId = input.getAlumnoId();
        Asistencia entity = new Asistencia();
        entity.setClase(claseRepository.findById(claseId)
                .orElseThrow(NoSuchForeignalEntityException.creater(ClaseConstants.DOMAIN_NAME, claseId)));
        entity.setAlumno(alumnoRepository.findById(alumnoId)
                .orElseThrow(NoSuchForeignalEntityException.creater(AlumnoConstants.DOMAIN_NAME, alumnoId)));
        entity.setFecha(input.getFecha());
        entity.setAsiste(input.isAsiste());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            final Asistencia entity, final UpdateAsistenciaInput input) {
        entity.setAsiste(input.isAsiste());
    }
}
