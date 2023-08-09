package io.davorpatech.apps.springbootdemo.web.controller.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import io.davorpatech.apps.springbootdemo.services.peopledir.PeopleService;
import io.davorpatech.fwk.validation.groups.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/people")
@Validated
public class PeopleController
{
    private final PeopleService peopleService;

    public PeopleController(final PeopleService peopleService)
    {
        this.peopleService = Objects.requireNonNull(
                peopleService, "peopleService must not be null!");
    }

    @GetMapping
    public List<Person> listAll()
    {
        return peopleService.findAll();
    }

    @GetMapping("/search")
    public List<Person> search(
            final @RequestParam(required = false) String initial,
            final @RequestParam(required = false) Long age)
    {
        return peopleService.findAllBySurnameInitialAndAge(initial, age);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> read(
            final @PathVariable("id") Long id)
    {
        Person person = peopleService.read(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> create(
            final @RequestBody @Validated({ Default.class, OnCreate.class }) @Valid Person person)
    {
        Person createdPerson = peopleService.create(person);

        // Build 201 CREATED response with REST body and location url
        return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdPerson.getId())
                        .toUri())
                .body(createdPerson);
    }
}
