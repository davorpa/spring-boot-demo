package io.davorpatech.apps.springbootdemo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Person {
    private String fullname;
    private String surnames;

    private LocalDate birthdate;

    public Person(String fullname, LocalDate birthdate) {
        setFullname(fullname);
        setBirthdate(birthdate);
    }

    public String getFullname() {
        return fullname;
    }

    public String extractSurname() {
        if (fullname == null) return null;
        String name = getFullname().trim();
        int index = name.indexOf(" ");
        return index < 0 ? name : name.substring(index);
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(getBirthdate(), LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format(
                "Person{fullname='%s', birthdate=%s, age=%s'}'",
                getFullname(), getBirthdate(), getAge());
    }
}
