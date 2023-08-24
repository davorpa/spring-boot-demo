package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UpdateClaseRequest extends BaseValueObject implements Identifiable<Long>
{
    private static final long serialVersionUID = -6050576281216406586L;

    private final Long id;

    @NotBlank
    @Size(min = ClaseConstants.CODE_MINLEN, max = ClaseConstants.CODE_MAXLEN)
    @Pattern(regexp = ClaseConstants.CODE_REGEX)
    private final String codigo;

    @NotBlank
    @Size(min = ClaseConstants.NAME_MINLEN, max = ClaseConstants.NAME_MAXLEN)
    private final String nombre;

    public UpdateClaseRequest(final Long id, final String codigo, final String nombre) {
        super();
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateClaseRequest other = (UpdateClaseRequest) o;
        return Objects.equals(id, other.id) &&
                Objects.equals(codigo, other.codigo) &&
                Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nombre);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, codigo='%s', nombre='%s'",
                id, codigo, nombre);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
