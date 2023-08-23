package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAlumnoInput extends BaseValueObject
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
    protected String defineObjAttrs() {
        return String.format("id=%s, fullname='%s'", id, fullname);
    }

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
