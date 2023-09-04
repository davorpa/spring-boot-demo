package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UpdateClaseInput // NOSONAR
        extends BaseUpdateInputCmd<Long> // NOSONAR
{
    private static final long serialVersionUID = 8096151278951746790L;

    @NotBlank
    @Size(min = ClaseConstants.CODE_MINLEN, max = ClaseConstants.CODE_MAXLEN)
    @Pattern(regexp = ClaseConstants.CODE_REGEX)
    private final String codigo;

    @NotBlank
    @Size(min = ClaseConstants.NAME_MINLEN, max = ClaseConstants.NAME_MAXLEN)
    private final String nombre;

    public UpdateClaseInput(final Long id, final String codigo, final String nombre) {
        super(id);
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateClaseInput other = (UpdateClaseInput) o;
        return Objects.equals(codigo, other.codigo) &&
                Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codigo, nombre);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, codigo='%s', nombre='%s'",
                super.defineObjAttrs(), codigo, nombre);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
