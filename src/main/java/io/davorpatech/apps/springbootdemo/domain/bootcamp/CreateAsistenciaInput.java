package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

public class CreateAsistenciaInput // NOSONAR
        extends BaseValueObject // NOSONAR
        implements CreateInputCmd // NOSONAR
{
    private static final long serialVersionUID = 5305261415324207966L;

    @NotNull
    private final Long claseId;

    @NotNull
    private final Long alumnoId;

    @NotNull
    @PastOrPresent
    private final LocalDate fecha;

    private final boolean asiste;

    public CreateAsistenciaInput(
            final Long claseId, final Long alumnoId, final LocalDate fecha,
            final boolean asiste) {
        super();
        this.claseId = claseId;
        this.alumnoId = alumnoId;
        this.fecha = fecha;
        this.asiste = asiste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAsistenciaInput other = (CreateAsistenciaInput) o;
        return asiste == other.asiste &&
                Objects.equals(claseId, other.claseId) &&
                Objects.equals(alumnoId, other.alumnoId) &&
                Objects.equals(fecha, other.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claseId, alumnoId, fecha, asiste);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("claseId=%s, alumnoId=%, fecha='%s', asiste=%s",
                claseId, alumnoId, fecha, asiste);
    }

    public Long getClaseId() {
        return claseId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isAsiste() {
        return asiste;
    }
}
