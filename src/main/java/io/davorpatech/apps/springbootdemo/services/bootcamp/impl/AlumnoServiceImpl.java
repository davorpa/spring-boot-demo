package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.service.data.AbstractDataService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional(readOnly = true)
@Validated
public class AlumnoServiceImpl // NOSONAR
        extends AbstractDataService<Long, Alumno, AlumnoDTO, FindAlumnosInput, CreateAlumnoInput, UpdateAlumnoInput> // NOSONAR
        implements AlumnoService // NOSONAR
{
    private final AlumnoRepository alumnoRepository;

    /**
     * Constructs a new {@link AlumnoServiceImpl} with the given arguments.
     *
     * @param alumnoRepository the alumno repository, never {@code null}
     */
    public AlumnoServiceImpl(final AlumnoRepository alumnoRepository) {
        super();
        Assert.notNull(alumnoRepository, "AlumnoRepository must not be null!");
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public String getDomainName() {
        return AlumnoConstants.DOMAIN_NAME;
    }

    @Override
    protected JpaRepository<Alumno, Long> getRepository() {
        return alumnoRepository;
    }

    @Override
    protected Sort getDefaultFindSort() {
        return Sort.by(Sort.Order.asc("id"));
    }

    @Override
    protected AlumnoDTO convertEntityToDto(final Alumno entity) {
        return new AlumnoDTO(
                entity.getId(),
                entity.getNid(),
                entity.getFullname(),
                entity.getEmail()
        );
    }

    @Override
    protected Alumno convertCreateToEntity(final CreateAlumnoInput input) {
        Alumno entity = new Alumno();
        entity.setNid(input.getNid());
        entity.setFullname(input.getFullname());
        entity.setEmail(input.getEmail());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            final Alumno entity, final UpdateAlumnoInput input) {
        entity.setNid(input.getNid());
        entity.setFullname(input.getFullname());
        entity.setEmail(input.getEmail());
    }

    @Override
    public AlumnoDTO findByNid(
            final String nid) {
        return alumnoRepository.findByNid(nid)
                .map(this::convertEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(getDomainName(), nid));
    }
}
