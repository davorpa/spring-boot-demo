package io.davorpatech.apps.springbootdemo.services.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PeopleService
{
    List<Person> findAll();

    List<Person> findAllBySurnameInitialAndAge(
            @Nullable String initial,
            @Nullable Long age);

    Person create(
            @NonNull Person person);
}
