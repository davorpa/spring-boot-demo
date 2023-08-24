package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateClaseInput extends BaseValueObject
{
    private static final long serialVersionUID = 3944033294390548808L;

    @NotBlank
    @Size(min = ClaseConstants.CODE_MINLEN, max = ClaseConstants.CODE_MAXLEN)
    @Pattern(regexp = ClaseConstants.CODE_REGEX)
    private final String codigo;

    @NotBlank
    @Size(min = ClaseConstants.NAME_MINLEN, max = ClaseConstants.NAME_MAXLEN)
    private final String nombre;

    public CreateClaseInput(final String codigo, final String nombre) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateClaseInput other = (CreateClaseInput) o;
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
