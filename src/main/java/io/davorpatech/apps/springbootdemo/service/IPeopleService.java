package io.davorpatech.apps.springbootdemo.service;

import io.davorpatech.apps.springbootdemo.model.Person;
import io.davorpatech.apps.springbootdemo.persistence.IPeopleDAO;
import io.davorpatech.apps.springbootdemo.persistence.InMemoryPeopleDAO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IPeopleService
{
    List<Person> findAll();

    List<Person> findBySurnameInitialAndAge(String initial, Long age);
}
