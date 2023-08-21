package io.davorpatech.apps.springbootdemo.web.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.persistence.model.bootcamp.Clase;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.fwk.exception.NoMatchingRelatedFieldsException;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/bootcamp/clases")
@Validated
public class ClaseController
{
    private final ClaseService claseService;

    /**
     * Constructs a new {@link ClaseController} with the given arguments.
     *
     * @param claseService the clase service, never {@code null}
     */
    public ClaseController(
            final ClaseService claseService)
    {
        this.claseService = Objects.requireNonNull(
                claseService, "ClaseService must not be null!");
    }

    @GetMapping
    public List<Clase> list()
    {
        // TODO: Apply Entity-2-Dto conversion
        return claseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> retrieveById(
            final @PathVariable("id") Long id)
    {
        Optional<Clase> entity = claseService.findById(id);
        // TODO: Apply Entity-2-Dto conversion
        return entity
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/code.{code}")
    public ResponseEntity<Clase> retrieveByCodigo(
            final @PathVariable("code") String code)
    {
        Optional<Clase> entity = claseService.findByCodigo(code);
        // TODO: Apply Entity-2-Dto conversion
        return entity
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Clase> create(
            final @RequestBody @Validated({ Default.class, OnCreate.class }) @Valid Clase body)
    {
        // TODO: Apply Dto-2-Entity conversion
        Clase entity = claseService.create(body);
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
    public ResponseEntity<Clase> update(
            final @PathVariable("id") Long id,
            final @RequestBody @Validated({ Default.class, OnUpdate.class }) @Valid Clase body)
    {
        if (body.hasId() && !Objects.equals(id, body.getId())) {
            throw new NoMatchingRelatedFieldsException(
                    "update.id", id, "update.body.id", body.getId());
        }
        // TODO: Apply Dto-2-Entity conversion
        Clase entity = claseService.update(body);
        // TODO: Apply Entity-2-Dto conversion
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            final @PathVariable("id") Long id)
    {
        claseService.deleteById(id);
    }
}
