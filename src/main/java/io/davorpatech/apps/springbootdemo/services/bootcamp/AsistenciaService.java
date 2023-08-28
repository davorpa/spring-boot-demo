package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AsistenciaDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateAsistenciaInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindAsistenciasInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateAsistenciaInput;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Asistencia;
import io.davorpatech.fwk.model.PagedResult;
import org.springframework.lang.NonNull;

import javax.validation.Valid;

/**
 * Servicio de básicos para la entidad de dominio {@link Asistencia}.
 */
public interface AsistenciaService // NOSONAR
{

    PagedResult<AsistenciaDTO> findAll(
            final @NonNull @Valid FindAsistenciasInput query);

    @NonNull
    AsistenciaDTO findById(
            final @NonNull Long id);

    @NonNull
    AsistenciaDTO create(
            final @NonNull @Valid CreateAsistenciaInput input);

    @NonNull
    AsistenciaDTO update(
            final @NonNull @Valid UpdateAsistenciaInput input);

    void deleteById(
            final @NonNull Long id);
}
