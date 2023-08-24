package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoConstants;
import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateAlumnoRequest extends BaseValueObject
{
    private static final long serialVersionUID = -490324873282114180L;

    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private final String nid;

    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private final String fullname;

    public CreateAlumnoRequest(final String nid, final String fullname) {
        super();
        this.nid = nid;
        this.fullname = fullname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAlumnoRequest other = (CreateAlumnoRequest) o;
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
