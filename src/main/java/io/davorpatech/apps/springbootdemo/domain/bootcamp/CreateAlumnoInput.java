package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateAlumnoInput // NOSONAR
        extends BaseValueObject // NOSONAR
        implements CreateInputCmd // NOSONAR
{
    private static final long serialVersionUID = -3511682225584425317L;

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

    public CreateAlumnoInput(final String nid, final String fullname, final String email) {
        super();
        this.nid = nid;
        this.fullname = fullname;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAlumnoInput other = (CreateAlumnoInput) o;
        return Objects.equals(nid, other.nid) &&
                Objects.equals(fullname, other.fullname) &&
                Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nid, fullname, email);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("nid='%s', fullname='%s', email='%s'", nid, fullname, email);
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
