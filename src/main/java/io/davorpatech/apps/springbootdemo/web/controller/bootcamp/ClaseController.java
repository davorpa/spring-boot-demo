package io.davorpatech.apps.springbootdemo.web.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.ClaseDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateClaseInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindClasesInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateClaseInput;
import io.davorpatech.apps.springbootdemo.services.bootcamp.ClaseService;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.CreateClaseRequest;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.UpdateClaseRequest;
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
@RequestMapping("/bootcamp/clases")
@Validated
class ClaseController
{
    private final ClaseService claseService;

    /**
     * Constructs a new {@link ClaseController} with the given arguments.
     *
     * @param claseService the clase service, never {@code null}
     */
    ClaseController(
            final ClaseService claseService)
    {
        this.claseService = Objects.requireNonNull(
                claseService, "ClaseService must not be null!");
    }

    @GetMapping
    PagedResult<ClaseDTO> findAll(
            final @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
            final @RequestParam(name = "size", defaultValue = "100") Integer pageSize)
    {
        FindClasesInput query = new FindClasesInput(pageNumber, pageSize);
        return claseService.findAll(query);
    }

    @GetMapping("/{id}")
    ResponseEntity<ClaseDTO> retrieveById(
            final @PathVariable("id") Long id)
    {
        ClaseDTO dto = claseService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/code.{code}")
    ResponseEntity<ClaseDTO> retrieveByCodigo(
            final @PathVariable("code") String code)
    {
        ClaseDTO dto = claseService.findByCodigo(code);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    ResponseEntity<ClaseDTO> create(
            final @RequestBody @Validated CreateClaseRequest request)
    {
        CreateClaseInput input = new CreateClaseInput(request.getCodigo(), request.getNombre());
        ClaseDTO dto = claseService.create(input);

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
    ResponseEntity<ClaseDTO> update(
            final @PathVariable("id") Long id,
            final @RequestBody @Validated UpdateClaseRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                    "update.id", id, "update.request.id", request.getId());
        }
        UpdateClaseInput input = new UpdateClaseInput(id,
                request.getCodigo(), request.getNombre());
        ClaseDTO dto = claseService.update(input);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            final @PathVariable("id") Long id)
    {
        claseService.deleteById(id);
    }
}
