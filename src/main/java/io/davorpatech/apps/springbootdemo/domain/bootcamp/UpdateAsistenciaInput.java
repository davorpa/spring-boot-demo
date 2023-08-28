package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateAsistenciaInput extends BaseValueObject // NOSONAR
{
    private static final long serialVersionUID = -411376987675761392L;

    @NotNull
    private final Long id;

    private final boolean asiste;

    public UpdateAsistenciaInput(
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
        UpdateAsistenciaInput other = (UpdateAsistenciaInput) o;
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

    public Long getId() {
        return id;
    }

    public boolean isAsiste() {
        return asiste;
    }
}
