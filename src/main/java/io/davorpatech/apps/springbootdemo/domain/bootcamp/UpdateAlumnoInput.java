package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UpdateAlumnoInput extends BaseValueObject implements Identifiable<Long>
{
    private static final long serialVersionUID = 8804797051751480022L;

    @NotNull
    private final Long id;

    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private final String nid;

    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private final String fullname;

    public UpdateAlumnoInput(final Long id, final String nid, final String fullname) {
        super();
        this.id = id;
        this.nid = nid;
        this.fullname = fullname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAlumnoInput other = (UpdateAlumnoInput) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, nid='%s', fullname='%s'",
                id, nid, fullname);
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
}
