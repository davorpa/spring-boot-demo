package io.davorpatech.apps.springbootdemo.web.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateAlumnoInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindAlumnosInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateAlumnoInput;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AlumnoService;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.CreateAlumnoRequest;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.UpdateAlumnoRequest;
import io.davorpatech.fwk.exception.NoMatchingRelatedFieldsException;
import io.davorpatech.fwk.model.PagedResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/bootcamp/alumnos")
class AlumnoController
{
    private final AlumnoService alumnoService;

    /**
     * Constructs a new {@link AlumnoController} with the given arguments.
     *
     * @param alumnoService the alumno service, never {@code null}
     */
    AlumnoController(
            final AlumnoService alumnoService)
    {
        this.alumnoService = Objects.requireNonNull(
                alumnoService, "AlumnoService must not be null!");
    }

    @GetMapping
    PagedResult<AlumnoDTO> findAll(
            final @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
            final @RequestParam(name = "size", defaultValue = "100") Integer pageSize)
    {
        FindAlumnosInput query = new FindAlumnosInput(pageNumber, pageSize);
        return alumnoService.findAll(query);
    }

    @GetMapping("/{id}")
    ResponseEntity<AlumnoDTO> retrieveById(
            final @PathVariable("id") Long id)
    {
        AlumnoDTO dto = alumnoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/nid.{id}")
    ResponseEntity<AlumnoDTO> retrieveByNid(
            final @PathVariable("id") String nid)
    {
        AlumnoDTO dto = alumnoService.findByNid(nid);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    ResponseEntity<AlumnoDTO> create(
            final @RequestBody @Validated CreateAlumnoRequest request)
    {
        CreateAlumnoInput input = new CreateAlumnoInput(request.getNid(), request.getFullname());
        AlumnoDTO dto = alumnoService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    @PutMapping("/{id}")
    ResponseEntity<AlumnoDTO> update(
            final @PathVariable("id") Long id,
            final @RequestBody @Validated UpdateAlumnoRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                    "update.id", id, "update.request.id", request.getId());
        }
        UpdateAlumnoInput input = new UpdateAlumnoInput(id,
                request.getNid(), request.getFullname());
        AlumnoDTO dto = alumnoService.update(input);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            final @PathVariable("id") Long id)
    {
        alumnoService.deleteById(id);
    }
}
