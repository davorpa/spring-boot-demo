package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UpdateAlumnoRequest extends BaseValueObject implements Identifiable<Long>
{
    private static final long serialVersionUID = -595675544814312717L;

    private final Long id;

    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private final String nid;

    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private final String fullname;

    @Size(max = AlumnoConstants.EMAIL_MAXLEN)
    @Email
    private final String email;

    public UpdateAlumnoRequest(final Long id, final String nid, final String fullname, final String email) {
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
        UpdateAlumnoRequest other = (UpdateAlumnoRequest) o;
        return Objects.equals(id, other.id) &&
                Objects.equals(nid, other.nid) &&
                Objects.equals(fullname, other.fullname) &&
                Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nid, fullname, email);
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
