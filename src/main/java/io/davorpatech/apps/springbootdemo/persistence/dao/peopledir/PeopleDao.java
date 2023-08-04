package io.davorpatech.apps.springbootdemo.persistence.dao.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PeopleDao
{
    List<Person> findAll();

    Person persist(
            @NonNull Person person);
}
