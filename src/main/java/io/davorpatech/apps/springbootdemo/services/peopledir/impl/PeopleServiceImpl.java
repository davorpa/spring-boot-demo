package io.davorpatech.apps.springbootdemo.services.peopledir.impl;

import io.davorpatech.apps.springbootdemo.persistence.dao.peopledir.PeopleDao;
import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import io.davorpatech.apps.springbootdemo.services.peopledir.PeopleService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService
{
    private final PeopleDao peopleDao;

    public PeopleServiceImpl(final PeopleDao peopleDao)
    {
        this.peopleDao = Objects.requireNonNull(
                peopleDao, "peopleDao must not be null!");
    }

    @Override
    public List<Person> findAll()
    {
        return peopleDao.findAll();
    }

    @Override
    public List<Person> findAllBySurnameInitialAndAge(
            final @Nullable String initial,
            final @Nullable Long age)
    {
        Predicate<Person> predicate = p -> true;
        if (initial != null) {
            predicate = predicate.and(p -> {
                final String surname = p.extractSurname();
                if (initial.isBlank())
                    return surname == null || surname.isBlank();
                return  surname != null &&
                        surname.trim().toLowerCase().startsWith(initial.trim().toLowerCase());
            });
        }
        if (age != null) {
            predicate = predicate.and(p -> Long.compare(age, p.getAge()) == 0);
        }
        return peopleDao.findAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Person create(
            final Person person)
    {
        return peopleDao.persist(person);
    }
}
