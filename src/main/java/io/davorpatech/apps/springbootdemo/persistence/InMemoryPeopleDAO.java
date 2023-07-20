package io.davorpatech.apps.springbootdemo.persistence;

import io.davorpatech.apps.springbootdemo.model.Person;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class InMemoryPeopleDAO implements IPeopleDAO
{
    private final List<Person> people = new LinkedList<>();

    public InMemoryPeopleDAO() {
        people.add(new Person("            ", LocalDate.of(1983, 7, 18)));
        people.add(new Person("David Lawson", LocalDate.of(1983, 5, 24)));
        people.add(new Person("Lola Farrel", LocalDate.of(1950, 4, 30)));
        people.add(new Person("Isabel Alonso", LocalDate.of(1972, 6, 6)));
        people.add(new Person("Facundo Loureiro", LocalDate.of(1992, 7, 5)));
        people.add(new Person("Israel Alonso", LocalDate.of(1980, 3, 10)));
    }

    @Override
    public List<Person> findAll() {
        return List.copyOf(people);
    }
}
