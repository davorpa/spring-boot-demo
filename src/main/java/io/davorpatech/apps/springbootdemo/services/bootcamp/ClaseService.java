package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateClaseInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindClasesInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateClaseInput;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.fwk.model.PagedResult;
import org.springframework.lang.NonNull;

import javax.validation.Valid;

/**
 * Servicio de básicos para la entidad de dominio {@link Clase}.
 */
public interface ClaseService // NOSONAR
{

    PagedResult<ClaseDTO> findAll(
            final @NonNull @Valid FindClasesInput query);

    @NonNull
    ClaseDTO findById(
            final @NonNull Long id);

    @NonNull
    ClaseDTO findByCodigo(
            final @NonNull String codigo);

    @NonNull
    ClaseDTO create(
            final @NonNull @Valid CreateClaseInput input);

    @NonNull
    ClaseDTO update(
            final @NonNull @Valid UpdateClaseInput input);

    void deleteById(
            final @NonNull Long id);
}
