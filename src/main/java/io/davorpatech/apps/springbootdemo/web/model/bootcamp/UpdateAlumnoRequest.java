package io.davorpatech.apps.springbootdemo.web.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoConstants;
import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAlumnoRequest extends BaseValueObject
{
    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private final String nid;

    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private final String fullname;

    public UpdateAlumnoRequest(final String nid, final String fullname) {
        super();
        this.nid = nid;
        this.fullname = fullname;
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("fullname='%s'", fullname);
    }

    public String getNid() {
        return nid;
    }

    public String getFullname() {
        return fullname;
    }
}
