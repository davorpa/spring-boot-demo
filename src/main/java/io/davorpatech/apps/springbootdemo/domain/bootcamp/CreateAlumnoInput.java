package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateAlumnoInput extends BaseValueObject
{
    private static final long serialVersionUID = -3511682225584425317L;

    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private final String nid;

    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private final String fullname;

    public CreateAlumnoInput(final String nid, final String fullname) {
        super();
        this.nid = nid;
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAlumnoInput other = (CreateAlumnoInput) o;
        return Objects.equals(nid, other.nid) && Objects.equals(fullname, other.fullname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nid, fullname);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("nid='%s', fullname='%s'", nid, fullname);
    }

    public String getNid() {
        return nid;
    }

    public String getFullname() {
        return fullname;
    }
}
