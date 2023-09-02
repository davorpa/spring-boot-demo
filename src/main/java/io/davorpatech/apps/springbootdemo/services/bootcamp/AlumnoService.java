package io.davorpatech.apps.springbootdemo.services.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateAlumnoInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindAlumnosInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateAlumnoInput;
import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.fwk.service.data.DataService;
import org.springframework.lang.NonNull;

public interface AlumnoService // NOSONAR
        extends DataService<Long, Alumno, AlumnoDTO, FindAlumnosInput, CreateAlumnoInput, UpdateAlumnoInput>
{
    @NonNull
    AlumnoDTO findByNid(
            final @NonNull String nid);
}
