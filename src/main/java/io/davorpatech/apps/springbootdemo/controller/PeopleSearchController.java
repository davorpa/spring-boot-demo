package io.davorpatech.apps.springbootdemo.controller;

import io.davorpatech.apps.springbootdemo.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("people")
public class PeopleSearchController {

    @GetMapping("search")
    public List<Person> search(
            @RequestParam(required = false) String initial,
            @RequestParam(required = false) Long age) {
        Predicate<Person> predicate = p -> true;
        if (initial != null) {
            predicate = predicate.and(p -> p.getName().startsWith(initial));
        }
        if (age != null) {
            predicate = predicate.and(p -> Long.compare(age, p.getAge()) == 0);
        }

        return List.of(
                    new Person("David", LocalDate.of(1983, 5, 24)),
                    new Person("Lola", LocalDate.of(1950, 4, 30)),
                    new Person("Isabel", LocalDate.of(1972, 6, 6)),
                    new Person("Facundo", LocalDate.of(1992, 7, 5)),
                    new Person("Israel", LocalDate.of(1980, 3, 10))
                )
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
