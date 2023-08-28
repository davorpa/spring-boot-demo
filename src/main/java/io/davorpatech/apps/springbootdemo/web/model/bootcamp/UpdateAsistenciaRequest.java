package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.util.Objects;

public class UpdateAsistenciaRequest // NOSONAR
        extends BaseValueObject // NOSONAR
        implements Identifiable<Long> // NOSONAR
{
    private static final long serialVersionUID = -3544145648835037137L;

    private final Long id;

    private final boolean asiste;

    public UpdateAsistenciaRequest(
            final Long id,
            final boolean asiste) {
        super();
        this.id = id;
        this.asiste = asiste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAsistenciaRequest other = (UpdateAsistenciaRequest) o;
        return asiste == other.asiste &&
                Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, asiste);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, asiste=%s", id, asiste);
    }

    @Override
    public Long getId() {
        return id;
    }

    public boolean isAsiste() {
        return asiste;
    }
}
