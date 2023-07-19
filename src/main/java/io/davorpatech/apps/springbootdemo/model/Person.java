package io.davorpatech.apps.springbootdemo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Person {
    public Person(String name, LocalDate birthdate) {
        setName(name);
        setBirthdate(birthdate);
    }

    private String name;
    private LocalDate birthdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return String.format("Person{name='%s', birthdate=%s, age=%s'}'", getName(), getBirthdate(), getAge());
    }
}
