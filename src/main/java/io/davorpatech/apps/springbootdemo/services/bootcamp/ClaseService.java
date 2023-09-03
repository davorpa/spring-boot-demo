package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateClaseInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindClasesInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateClaseInput;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.fwk.service.data.DataService;
import org.springframework.lang.NonNull;

public interface ClaseService // NOSONAR
        extends DataService<Long, Clase, ClaseDTO, FindClasesInput, CreateClaseInput, UpdateClaseInput> // NOSONAR
{
    @NonNull
    ClaseDTO findByCodigo(
            final @NonNull String codigo);
}
