package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.*;
import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AlumnoServiceImpl // NOSONAR
        extends JpaBasedDataService< // NOSONAR
                    AlumnoRepository, // NOSONAR
                    Long, Alumno, AlumnoDTO, // NOSONAR
                    FindAlumnosInput, CreateAlumnoInput, UpdateAlumnoInput> // NOSONAR
        implements AlumnoService // NOSONAR
{
    /**
     * Constructs a new {@link AlumnoServiceImpl} with the given arguments.
     *
     * @param alumnoRepository the alumno repository, never {@code null}
     */
    public AlumnoServiceImpl(
            final AlumnoRepository alumnoRepository) {
        super(alumnoRepository, AlumnoConstants.DOMAIN_NAME);
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
        return repository.findByNid(nid)
                .map(this::convertEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(domainName, nid));
    }
}
