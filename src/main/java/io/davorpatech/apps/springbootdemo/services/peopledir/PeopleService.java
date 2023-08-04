package io.davorpatech.apps.springbootdemo.services.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;

import java.util.List;

public interface PeopleService
{
    List<Person> findAll();

    List<Person> findAllBySurnameInitialAndAge(String initial, Long age);

    Person create(Person person);
}
