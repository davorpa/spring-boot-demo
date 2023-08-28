package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.time.LocalDate;
import java.util.Objects;

public class AsistenciaDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    private static final long serialVersionUID = -2142068147869461029L;

    private final Long id;

    private final ClaseDTO clase;

    private final AlumnoDTO alumno;

    private final LocalDate fecha;

    private final boolean asiste;

    public AsistenciaDTO(
            final Long id,
            final ClaseDTO clase, final AlumnoDTO alumno, final LocalDate fecha,
            final boolean asiste) {
        super();
        this.id = id;
        this.clase = clase;
        this.alumno = alumno;
        this.fecha = fecha;
        this.asiste = asiste;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsistenciaDTO other = (AsistenciaDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, clase_id=%s, alumno_id=%s, fecha='%s', asiste=%s",
                id, getClaseId(), getAlummoId(), fecha, asiste);
    }

    @Override
    public Long getId() {
        return id;
    }

    public ClaseDTO getClase() {
        return clase;
    }

    Long getClaseId() {
        ClaseDTO target = getClase();
        return target == null ? null : target.getId();
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    Long getAlummoId() {
        AlumnoDTO target = getAlumno();
        return target == null ? null : target.getId();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isAsiste() {
        return asiste;
    }
}
