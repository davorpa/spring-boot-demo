package io.davorpatech.apps.springbootdemo.controller;

import io.davorpatech.apps.springbootdemo.model.Person;
import io.davorpatech.apps.springbootdemo.service.IPeopleService;
import io.davorpatech.apps.springbootdemo.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("people")
public class PeopleSearchController
{
    private IPeopleService peopleService;

    public PeopleSearchController(
            final @Autowired PeopleService peopleService) {
        this.peopleService = Objects.requireNonNull(
                peopleService, "peopleService must not be null");
    }

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
