package io.davorpatech.apps.springbootdemo.service;

import io.davorpatech.apps.springbootdemo.model.Person;
import io.davorpatech.apps.springbootdemo.persistence.IPeopleDAO;
import io.davorpatech.apps.springbootdemo.persistence.InMemoryPeopleDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PeopleService implements IPeopleService
{
    IPeopleDAO peopleDAO = new InMemoryPeopleDAO();

    @Override
    public List<Person> findAll() {
        return peopleDAO.findAll();
    }

    @Override
    public List<Person> findBySurnameInitialAndAge(String initial, Long age) {
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
        return peopleDAO.findAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
