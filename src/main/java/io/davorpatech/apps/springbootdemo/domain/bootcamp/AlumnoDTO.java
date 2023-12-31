package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.util.Objects;

public class AlumnoDTO // NOSONAR
        extends BaseValueObject // NOSONAR
        implements Identifiable<Long> // NOSONAR
{
    private static final long serialVersionUID = -7715819929617487003L;

    private final Long id;

    private final String nid;

    private final String fullname;

    private final String email;

    public AlumnoDTO(Long id, String nid, String fullname, String email) {
        super();
        this.id = id;
        this.nid = nid;
        this.fullname = fullname;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlumnoDTO other = (AlumnoDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, nid='%s', fullname='%s', email='%s'",
                id, nid, fullname, email);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getNid() {
        return nid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
