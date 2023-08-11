package io.davorpatech.apps.springbootdemo.web.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Alumno;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/bootcamp/alumnos")
@Validated
public class AlumnoController
{
    private AlumnoService alumnoService;

    public AlumnoController(final AlumnoService alumnoService)
    {
        this.alumnoService = Objects.requireNonNull(
                alumnoService, "alumnoService must not be null!");
    }

    @GetMapping
    public List<Alumno> list()
    {
        // TODO: Apply Entity-2-Dto conversion
        return alumnoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> retrieveById(
            final @PathVariable("id") Long id)
    {
        Optional<Alumno> entity = alumnoService.findById(id);
        // TODO: Apply Entity-2-Dto conversion
        return entity
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/nid.{id}")
    public ResponseEntity<Alumno> retrieveByNid(
            final @PathVariable("id") String nid)
    {
        Optional<Alumno> entity = alumnoService.findByNid(nid);
        // TODO: Apply Entity-2-Dto conversion
        return entity
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Alumno> create(
            final @RequestBody @Validated({ Default.class, OnCreate.class }) @Valid Alumno body)
    {
        // TODO: Apply Dto-2-Entity conversion
        Alumno entity = alumnoService.create(body);
        // TODO: Apply Entity-2-Dto conversion

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI createdResourceLocationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity
                .created(createdResourceLocationUri)
                .body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> update(
            final @PathVariable("id") Long id,
            final @RequestBody @Validated({ Default.class, OnUpdate.class }) @Valid Alumno body)
    {
        if (body.hasId() && !Objects.equals(id, body.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(
                    "update.id (%s) not matches update.body.id (%s)",
                    id, body.getId()));
        }
        // TODO: Apply Dto-2-Entity conversion
        Alumno entity = alumnoService.update(body);
        // TODO: Apply Entity-2-Dto conversion
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            final @PathVariable("id") Long id)
    {
        alumnoService.deleteById(id);
    }
}
