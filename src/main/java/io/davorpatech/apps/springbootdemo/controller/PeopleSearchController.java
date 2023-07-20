package io.davorpatech.apps.springbootdemo.controller;

import io.davorpatech.apps.springbootdemo.model.Person;
import io.davorpatech.apps.springbootdemo.service.IPeopleService;
import io.davorpatech.apps.springbootdemo.service.PeopleService;
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
public class PeopleSearchController
{
    private IPeopleService peopleService = new PeopleService();

    @GetMapping
    public List<Person> listAll() {
        return peopleService.findAll();
    }

    @GetMapping("search")
    public List<Person> search(
            @RequestParam(required = false) String initial,
            @RequestParam(required = false) Long age) {
        return peopleService.findBySurnameInitialAndAge(initial, age);
    }
}
