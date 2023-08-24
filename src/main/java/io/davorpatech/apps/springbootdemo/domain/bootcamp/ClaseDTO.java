package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.util.Objects;

public class ClaseDTO extends BaseValueObject implements Identifiable<Long>
{
    private static final long serialVersionUID = -40323473660232495L;

    private final Long id;

    private final String codigo;

    private final String nombre;

    public ClaseDTO(Long id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaseDTO other = (ClaseDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
