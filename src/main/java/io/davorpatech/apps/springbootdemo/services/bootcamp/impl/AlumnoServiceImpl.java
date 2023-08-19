package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.AlumnoRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.fwk.service.AbstractCrudEntityService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación del servicio de básicos para la entidad {@link Alumno}.
 * <p>
 * Se delega su operativa en el correspondiente
 * {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}.
 */
@Service
public class AlumnoServiceImpl
        extends AbstractCrudEntityService<Alumno, Long>
        implements AlumnoService
{
    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(
            final AlumnoRepository alumnoRepository)
    {
        this.alumnoRepository = Objects.requireNonNull(
                alumnoRepository, "alumnoRepository must not be null!");
    }

    @Override
    protected AlumnoRepository getRepository()
    {
        return this.alumnoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findByNid(
            final @NonNull String nid)
    {
        return alumnoRepository.findByNid(nid);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByNid(
            final @NonNull String nid)
    {
        return alumnoRepository.existsByNid(nid);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alumno> findAllByNid(
            final @NonNull Iterable<String> nids)
    {
        return alumnoRepository.findAllByNid(nids);
    }

    @Transactional
    @Override
    public void deleteByNid(
            final @NonNull String nid)
    {
        alumnoRepository.deleteByNid(nid);
    }

    @Transactional
    @Override
    public void deleteAllByNid(
            final @NonNull Iterable<String> nids)
    {
        alumnoRepository.deleteAllByNid(nids);
    }
}
