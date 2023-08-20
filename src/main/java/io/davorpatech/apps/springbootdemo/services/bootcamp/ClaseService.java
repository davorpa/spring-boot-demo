package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.fwk.service.CrudEntityService;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de básicos para la entidad de dominio {@link Clase}.
 */
public interface ClaseService // NOSONAR
        extends CrudEntityService<Clase, Long> // NOSONAR
{
    /**
     * Busca una clase por su clave natural (código).
     *
     * @param codigo código de la clase, nunca {@code null}
     * @return nunca {@code null}, {@literal Optional.empty()} si el registro
     *         no existe
     */
    Optional<Clase> findByCodigo(
            @NonNull String codigo);

    /**
     * Comprueba si una clase existe dada su clave natural (código).
     *
     * @param codigo código de la clase, nunca {@code null}
     * @return @{@code true} si existe
     */
    boolean existsByCodigo(
            @NonNull String codigo);

    /**
     * Busca todos los alumnos dadas sus claves naturales (códigos).
     *
     * <p>Los {@literal codigo} que no se hayan encontrado no se tendrán en cuenta
     * para el retorno, es decir, el método podrá retornar menos resultados que
     * {@literal codigos} de entrada.
     *
     * @param codigos códigos de clase a buscar, nunca {@code null}
     * @return la lista de registros, nunca {@code null}
     */
    List<Clase> findAllByCodigo(
            @NonNull Iterable<String> codigos);

    /**
     * Elimina una clase por su clave natural (código).
     *
     * <p>Si no se encuentra ninguna clase con dicho {@literal codigo},
     * el sistema no hace nada.
     *
     * @param codigo código de clase, nunca {@code null}
     */
    void deleteByCodigo(
            @NonNull String codigo);

    /**
     * Elimina clases por su clave natural (codigo).
     *
     * <p>Si por casualidad no se encuentra el registro identificado por el
     * {@literal codigo} proporcionado, la operación se ignora silenciosamente
     * siguiendo flujo normal del sistema.
     *
     * @param codigos códigos de las clases, nunca {@code null}
     */
    void deleteAllByCodigo(
            @NonNull Iterable<String> codigos);
}
