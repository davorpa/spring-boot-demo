package io.davorpatech.apps.springbootdemo.services.bootcamp.impl;

import io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp.ClaseRepository;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.fwk.service.AbstractCrudEntityService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación del servicio de básicos para la entidad {@link Clase}.
 * <p>
 * Se delega su operativa en el correspondiente
 * {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}.
 */
@Service
public class ClaseServiceImpl
        extends AbstractCrudEntityService<Clase, Long>
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
    protected ClaseRepository getRepository()
    {
        return this.claseRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Clase> findByCodigo(
            final @NonNull String codigo)
    {
        return claseRepository.findByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByCodigo(
            final @NonNull String codigo)
    {
        return claseRepository.existsByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Clase> findAllByCodigo(
            final @NonNull Iterable<String> codigos)
    {
        return claseRepository.findAllByCodigo(codigos);
    }

    @Transactional
    @Override
    public void deleteByCodigo(
            final @NonNull String codigo)
    {
        claseRepository.deleteByCodigo(codigo);
    }

    @Transactional
    @Override
    public void deleteAllByCodigo(
            final @NonNull Iterable<String> codigos)
    {
        claseRepository.deleteAllByCodigo(codigos);
    }
}
