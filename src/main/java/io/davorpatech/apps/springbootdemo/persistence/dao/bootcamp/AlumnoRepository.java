package io.davorpatech.apps.springbootdemo.persistence.dao.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repositorio de datos para la entidad de dominio {@link Alumno}.
 */
@Repository
@Transactional(readOnly = true)
public interface AlumnoRepository extends JpaRepository<Alumno, Long>
{
    @Query("SELECT new io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO(e.id, e.nid, e.fullname) " +
            " FROM #{#entityName} e")
    Page<AlumnoDTO> findAllAsDto(final Pageable pageable);

    Optional<Alumno> findByNid(
            @NonNull String nid);
}
