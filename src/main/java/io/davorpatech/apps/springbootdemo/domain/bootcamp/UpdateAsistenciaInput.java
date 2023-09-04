package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;

import java.util.Objects;

public class UpdateAsistenciaInput // NOSONAR
        extends BaseUpdateInputCmd<Long> // NOSONAR
{
    private static final long serialVersionUID = -411376987675761392L;

    private final boolean asiste;

    public UpdateAsistenciaInput(final Long id, final boolean asiste) {
        super(id);
        this.asiste = asiste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateAsistenciaInput other = (UpdateAsistenciaInput) o;
        return asiste == other.asiste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), asiste);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, asiste=%s", super.defineObjAttrs(), asiste);
    }

    public boolean isAsiste() {
        return asiste;
    }
}
