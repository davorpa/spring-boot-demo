package io.davorpatech.apps.springbootdemo.web.controller.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AsistenciaDTO;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.CreateAsistenciaInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.FindAsistenciasInput;
import io.davorpatech.apps.springbootdemo.domain.bootcamp.UpdateAsistenciaInput;
import io.davorpatech.apps.springbootdemo.services.bootcamp.AsistenciaService;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.CreateAsistenciaRequest;
import io.davorpatech.apps.springbootdemo.web.model.bootcamp.UpdateAsistenciaRequest;
import io.davorpatech.fwk.exception.NoMatchingRelatedFieldsException;
import io.davorpatech.fwk.model.PagedResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/bootcamp/asistencias")
class AsistenciaController
{
    private final AsistenciaService asistenciaService;

    /**
     * Constructs a new {@link AsistenciaController} with the given arguments.
     *
     * @param asistenciaService the asistencia service, never {@code null}
     */
    AsistenciaController(
            final AsistenciaService asistenciaService)
    {
        this.asistenciaService = Objects.requireNonNull(
                asistenciaService, "AsistenciaService must not be null!");
    }

    @GetMapping
    PagedResult<AsistenciaDTO> findAll(
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "clase.id", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "alumno.id", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "fecha", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            final @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindAsistenciasInput query = new FindAsistenciasInput(
                forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
                forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
                pageable.getSort()
        );
        return asistenciaService.findAll(query);
    }

    @GetMapping("/{id}")
    ResponseEntity<AsistenciaDTO> retrieveById(
            final @PathVariable("id") Long id)
    {
        AsistenciaDTO dto = asistenciaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    ResponseEntity<AsistenciaDTO> create(
            final @RequestBody @Validated CreateAsistenciaRequest request)
    {
        CreateAsistenciaInput input = new CreateAsistenciaInput(
                request.getClaseId(), request.getAlumnoId(), request.getFecha(),
                request.isAsiste());
        AsistenciaDTO dto = asistenciaService.create(input);

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
    ResponseEntity<AsistenciaDTO> update(
            final @PathVariable("id") Long id,
            final @RequestBody @Validated UpdateAsistenciaRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                    "update.id", id, "update.request.id", request.getId());
        }
        UpdateAsistenciaInput input = new UpdateAsistenciaInput(id,
                request.isAsiste());
        AsistenciaDTO dto = asistenciaService.update(input);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            final @PathVariable("id") Long id)
    {
        asistenciaService.deleteById(id);
    }
}
