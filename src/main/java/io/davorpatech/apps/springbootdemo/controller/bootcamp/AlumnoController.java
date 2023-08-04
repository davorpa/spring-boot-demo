package io.davorpatech.apps.springbootdemo.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.service.bootcamp.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("bootcamp/alumnos")
@Validated
public class AlumnoController
{
    private AlumnoService alumnoService;

    public AlumnoController(final AlumnoService alumnoService) {
        this.alumnoService = Objects.requireNonNull(
                alumnoService, "alumnoService must not be null!");
    }

    @GetMapping
    public List<Alumno> list() {
        return alumnoService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Alumno> retrieveById(
            final @PathVariable("id") Long id) {
        Optional<Alumno> entity = alumnoService.findById(id);
        return entity.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping(value = "nid.{id}")
    public ResponseEntity<Alumno> retrieveByNid(
            final @PathVariable("id") String nid) {
        Optional<Alumno> entity = alumnoService.findByNid(nid);
        return entity.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Alumno> create(
            final @RequestBody @Valid Alumno body) {
        Alumno entity = alumnoService.create(body);
        return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(entity.getId())
                        .toUri())
                .body(entity);
    }

    @PutMapping("{id}")
    public Alumno update(
            final @PathVariable("id") Long id,
            final @RequestBody Alumno body) {
        if (!id.equals(body.getId())) {
            throw new IllegalArgumentException("update.id not matches update.body.id");
        }
        Alumno entity = alumnoService.update(body);
        return entity;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            final @PathVariable("id") Long id) {
        alumnoService.deleteById(id);
    }
}
