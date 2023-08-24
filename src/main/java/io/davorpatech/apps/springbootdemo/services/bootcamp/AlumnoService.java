package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateAlumnoInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindAlumnosInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateAlumnoInput;
import io.davorpatech.fwk.model.PagedResult;
import org.springframework.lang.NonNull;

import javax.validation.Valid;

public interface AlumnoService // NOSONAR
{

    PagedResult<AlumnoDTO> findAll(
            final @NonNull @Valid FindAlumnosInput query);

    @NonNull
    AlumnoDTO findById(
            final @NonNull Long id);

    @NonNull
    AlumnoDTO findByNid(
            final @NonNull String nid);

    @NonNull
    AlumnoDTO create(
            final @NonNull @Valid CreateAlumnoInput input);

    @NonNull
    AlumnoDTO update(
            final @NonNull @Valid UpdateAlumnoInput input);

    void deleteById(
            final @NonNull Long id);
}
