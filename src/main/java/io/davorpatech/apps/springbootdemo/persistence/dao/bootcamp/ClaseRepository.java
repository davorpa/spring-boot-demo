package io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseDTO;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de datos para la entidad de dominio {@link Clase}.
 */
@Repository
@Transactional(readOnly = true)
public interface ClaseRepository extends JpaRepository<Clase, Long>
{
    @Query("SELECT new io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseDTO(e.id, e.codigo, e.nombre) " +
            " FROM #{#entityName} e")
    Page<ClaseDTO> findAllAsDto(final Pageable pageable);

    Optional<Clase> findByCodigo(
            @NonNull String codigo);

    boolean existsByCodigo(
            @NonNull String codigo);

    @Query("SELECT e FROM #{#entityName} e WHERE e.codigo IN ?1")
    List<Clase> findAllByCodigo(
            @NonNull Iterable<String> codigos);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.codigo = ?1")
    void deleteByCodigo(
            @NonNull String codigo);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.codigo IN ?1")
    void deleteAllByCodigo(
            @NonNull Iterable<String> codigos);
}
