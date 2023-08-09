package io.davorpatech.apps.springbootdemo.persistence.dao.peopledir.impl;

import io.davorpatech.apps.springbootdemo.persistence.dao.peopledir.PeopleDao;
import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryPeopleDao implements PeopleDao
{
    // DB repository mock
    private final Map<Long, Person> repository = new ConcurrentHashMap<>();

    // DB id sequence mock
    private final AtomicLong sequence = new AtomicLong(0L);

    public InMemoryPeopleDao()
    {
        persist(new Person("            ", LocalDate.of(1983, 7, 18)));
        persist(new Person("David Lawson", LocalDate.of(1983, 5, 24)));
        persist(new Person("Lola Farrel", LocalDate.of(1950, 4, 30)));
        persist(new Person("Isabel Alonso", LocalDate.of(1972, 6, 6)));
        persist(new Person("Facundo Loureiro", LocalDate.of(1992, 7, 5)));
        persist(new Person("Israel Alonso", LocalDate.of(1980, 3, 10)));
        persist(new Person("Leonardo", LocalDate.of(1985, 5, 13)));
    }

    @Override
    public List<Person> findAll()
    {
        return repository.values().stream().collect(Collectors.toList());
    }

    @Override
    public Person read(
            final @NonNull Long id)
    {
        return repository.get(id);
    }

    @Override
    public Person persist(
            final @NonNull Person person)
    {
        long id = sequence.incrementAndGet();
        person.setId(id);
        repository.put(id, person);
        return person;
    }
}
