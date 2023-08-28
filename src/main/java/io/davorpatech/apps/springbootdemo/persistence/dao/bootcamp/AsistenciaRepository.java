package io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp;

import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de datos para la entidad de dominio {@link Asistencia}.
 */
@Repository
@Transactional(readOnly = true)
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long>
{
    @Query("SELECT e FROM #{#entityName} e " +
            " WHERE e.clase.id = ?1 AND e.alumno.id = ?2 " +
            " ORDER BY e.clase.nombre ASC, e.alumno.fullname ASC")
    List<Asistencia> findAllBy(
            @NonNull Long claseId,
            @NonNull Long alumnoId);

    @Query("SELECT e FROM #{#entityName} e " +
            " WHERE e.clase.codigo = ?1 AND e.alumno.nid = ?2 " +
            " ORDER BY e.clase.nombre ASC, e.alumno.fullname ASC")
    List<Asistencia> findAllBy(
            @NonNull String claseCodigo,
            @NonNull String alumnoNid);

    @Query("SELECT e FROM #{#entityName} e WHERE e.clase.id = ?1 AND e.alumno.id = ?2 AND e.fecha = ?3")
    Optional<Asistencia> findById(
            @NonNull Long claseId,
            @NonNull Long alumnoId,
            @NonNull LocalDate fecha);

    @Query("SELECT count(e.id) = 1 FROM #{#entityName} e WHERE e.clase.id = ?1 AND e.alumno.id = ?2 AND e.fecha = ?3")
    boolean existsById(
            @NonNull Long claseId,
            @NonNull Long alumnoId,
            @NonNull LocalDate fecha);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.clase.id = ?1 AND e.alumno.id = ?2 AND e.fecha = ?3")
    void deleteById(
            @NonNull Long claseId,
            @NonNull Long alumnoId,
            @NonNull LocalDate fecha);

    @Query("SELECT e FROM #{#entityName} e WHERE e.clase.codigo = ?1 AND e.alumno.nid = ?2 AND e.fecha = ?3")
    Optional<Asistencia> findById(
            @NonNull String claseCodigo,
            @NonNull String alumnoNid,
            @NonNull LocalDate fecha);

    @Query("SELECT count(e.id) = 1 FROM #{#entityName} e WHERE e.clase.codigo = ?1 AND e.alumno.nid = ?2 AND e.fecha = ?3")
    boolean existsById(
            @NonNull String claseCodigo,
            @NonNull String alumnoNid,
            @NonNull LocalDate fecha);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.clase.codigo = ?1 AND e.alumno.nid = ?2 AND e.fecha = ?3")
    void deleteById(
            @NonNull String claseCodigo,
            @NonNull String alumnoNid,
            @NonNull LocalDate fecha);
}
