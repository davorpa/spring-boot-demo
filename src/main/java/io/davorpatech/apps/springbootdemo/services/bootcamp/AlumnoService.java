package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.fwk.service.CrudEntityService;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de básicos para la entidad de dominio {@link Alumno}.
 */
public interface AlumnoService // NOSONAR
        extends CrudEntityService<Alumno, Long> // NOSONAR
{
    /**
     * Busca un alumno por su clave natural
     * (documento nacional de indentidad).
     *
     * @param nid número de documento nacional de indentidad del alumno,
     *            nunca {@code null}.
     * @return nunca {@code null}, {@literal Optional.empty()} si el
     *         registro no existe.
     */
    Optional<Alumno> findByNid(
            @NonNull String nid);

    /**
     * Comprueba si un alumno existe dada su clave natural
     * (documento nacional de indentidad).
     *
     * @param nid número de documento nacional de indentidad del alumno,
     *            nunca {@code null}.
     * @return @{@code true} si existe
     */
    boolean existsByNid(
            @NonNull String nid);

    /**
     * Busca todos los alumnos dadas sus claves naturales
     * (documento nacional de indentidad).
     * <p>
     * Los {@literal nid} que no se hayan encontrado no se tendrán en cuenta
     * para el retorno, es decir, el método podrá retornar menos resultados
     * que {@literal nids} de entrada.
     *
     * @param nids números de documento nacional de indentidad de
     *             los alumnos, nunca {@code null}.
     * @return la lista de registros, nunca {@code null}.
     */
    List<Alumno> findAllByNid(
            @NonNull Iterable<String> nids);

    /**
     * Elimina un alumno por su clave natural
     * (documento nacional de indentidad).
     * <p>
     * Si no se encuentra ningún alumno con dicho {@literal nid},
     * el sistema no hace nada.
     *
     * @param nid número de documento nacional de indentidad del alumno,
     *            nunca {@code null}.
     */
    void deleteByNid(
            @NonNull String nid);

    /**
     * Elimina alumnos por su clave natural
     * (documento nacional de indentidad).
     * <p>
     * Si por casualidad no se encuentra el registro identificado por el
     * {@literal nid} proporcionado, la operación se ignora silenciosamente
     * siguiendo flujo normal del sistema.
     *
     * @param nids números de documento nacional de indentidad de los alumnos,
     *            nunca {@code null}.
     */
    void deleteAllByNid(
            @NonNull Iterable<String> nids);
}
