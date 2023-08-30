package io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
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
 * Repositorio de datos para la entidad de dominio {@link Alumno}.
 */
@Repository
@Transactional(readOnly = true)
public interface AlumnoRepository extends JpaRepository<Alumno, Long>
{
    @Query("SELECT new io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO(e.id, e.nid, e.fullname, e.email) " +
            " FROM #{#entityName} e")
    Page<AlumnoDTO> findAllAsDto(final Pageable pageable);

    Optional<Alumno> findByNid(
            @NonNull String nid);

    boolean existsByNid(
            @NonNull String nid);

    @Query("SELECT e FROM #{#entityName} e WHERE e.nid IN ?1")
    List<Alumno> findAllByNid(
            @NonNull Iterable<String> nids);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.nid = ?1")
    void deleteByNid(
            @NonNull String nid);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.nid IN ?1")
    void deleteAllByNid(
            @NonNull Iterable<String> nids);
}
