package io.davorpatech.apps.springbootdemo.persistence.dao.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;

import java.util.List;

public interface PeopleDao {
    List<Person> findAll();

    Person persist(Person person);
}
