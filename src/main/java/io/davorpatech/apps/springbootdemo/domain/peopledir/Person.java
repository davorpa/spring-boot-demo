package io.davorpatech.apps.springbootdemo.domain.peopledir;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnDelete;
import io.davorpatech.fwk.validation.groups.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Person extends BaseValueObject
{
    private static final long serialVersionUID = -7516723556324606215L;

    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class, OnDelete.class })
    private Long id;

    @NotBlank
    private String fullname;

    @NotNull
    @PastOrPresent
    private LocalDate birthdate;

    public Person(final String fullname, final LocalDate birthdate)
    {
        setFullname(fullname);
        setBirthdate(birthdate);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getFullname()
    {
        return fullname;
    }

    public String extractSurname()
    {
        if (fullname == null) return null;
        String name = getFullname().trim();
        int index = name.indexOf(" ");
        return index < 0 ? name : name.substring(index);
    }

    public void setFullname(final String fullname)
    {
        this.fullname = fullname;
    }

    public LocalDate getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdate)
    {
        this.birthdate = birthdate;
    }

    public long getAge()
    {
        LocalDate date = getBirthdate();
        if (date == null) return -1;
        return ChronoUnit.YEARS.between(date, LocalDate.now());
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs()
    {
        return String.format("id=%s, fullname='%s', birthdate=%s, age=%s",
                id, fullname, birthdate, getAge());
    }
}
