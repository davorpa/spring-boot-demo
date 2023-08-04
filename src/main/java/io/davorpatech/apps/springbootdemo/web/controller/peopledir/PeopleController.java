package io.davorpatech.apps.springbootdemo.web.controller.peopledir;

import io.davorpatech.apps.springbootdemo.persistence.model.peopledir.Person;
import io.davorpatech.apps.springbootdemo.services.peopledir.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("people")
public class PeopleController
{
    private PeopleService peopleService;

    public PeopleController(final PeopleService peopleService) {
        this.peopleService = Objects.requireNonNull(
                peopleService, "peopleService must not be null!");
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
