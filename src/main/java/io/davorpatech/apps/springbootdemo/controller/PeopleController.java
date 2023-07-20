package io.davorpatech.apps.springbootdemo.controller;

import io.davorpatech.apps.springbootdemo.model.Person;
import io.davorpatech.apps.springbootdemo.service.IPeopleService;
import io.davorpatech.apps.springbootdemo.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("people")
public class PeopleController
{
    private IPeopleService peopleService;

    public PeopleController(
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

    @PostMapping
    public ResponseEntity<Person> create(
            @RequestBody Person person) {
        person = peopleService.create(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
        /**
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(person.getId())
                        .toUri())
                .body(person);
         */
    }
}
