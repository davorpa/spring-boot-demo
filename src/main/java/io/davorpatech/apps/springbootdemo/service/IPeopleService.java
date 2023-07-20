package io.davorpatech.apps.springbootdemo.service;

import io.davorpatech.apps.springbootdemo.model.Person;

import java.util.List;

public interface IPeopleService
{
    List<Person> findAll();

    List<Person> findBySurnameInitialAndAge(String initial, Long age);
}
