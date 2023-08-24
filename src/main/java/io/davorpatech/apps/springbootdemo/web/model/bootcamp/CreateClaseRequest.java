package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseConstants;
import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateClaseRequest extends BaseValueObject
{
    private static final long serialVersionUID = -6636045911506618994L;

    @NotBlank
    @Size(min = ClaseConstants.CODE_MINLEN, max = ClaseConstants.CODE_MAXLEN)
    @Pattern(regexp = ClaseConstants.CODE_REGEX)
    private final String codigo;

    @NotBlank
    @Size(min = ClaseConstants.NAME_MINLEN, max = ClaseConstants.NAME_MAXLEN)
    private final String nombre;

    public CreateClaseRequest(final String codigo, final String nombre) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateClaseRequest other = (CreateClaseRequest) o;
        return Objects.equals(codigo, other.codigo) && Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("codigo='%s', nombre='%s'", codigo, nombre);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
