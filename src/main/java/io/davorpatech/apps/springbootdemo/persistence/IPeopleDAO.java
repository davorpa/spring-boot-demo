package io.davorpatech.apps.springbootdemo.persistence;

import io.davorpatech.apps.springbootdemo.model.Person;

import java.util.List;

public interface IPeopleDAO {
    List<Person> findAll();

    Person persist(Person person);
}
