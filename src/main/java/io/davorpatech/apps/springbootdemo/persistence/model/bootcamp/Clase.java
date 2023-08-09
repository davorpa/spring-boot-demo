package io.davorpatech.apps.springbootdemo.persistence.model.bootcamp;

import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnDelete;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "CLASE", schema = "BOOTCAMP")
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class Clase extends BaseEntity<Long> // NOSONAR
{
    private static final long serialVersionUID = 4541825044455915092L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bootcamp_clase_generator")
    @SequenceGenerator(
            name = "bootcamp_clase_generator", sequenceName = "bootcamp_clase_seq",
            initialValue = 1, allocationSize = 50)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class, OnDelete.class })
    private Long id;

    @NaturalId(mutable = false)
    @Column(name = "codigo", length = 50, nullable = false, unique = true, updatable = false)
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[A-Z0-9\\-]+")
    private String codigo;

    @Column(name = "nombre", length = 255, nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String nombre;

    @OneToMany(mappedBy = "clase", orphanRemoval = true)
    @OrderBy("alumno.id ASC, fecha ASC")
    private Set<@Valid Asistencia> asistencias = new LinkedHashSet<>();

    public Clase()
    {
        super();
    }

    public Clase(final String codigo, final String nombre)
    {
        super();
        setCodigo(codigo);
        setNombre(nombre);
    }

    @Override
    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = Objects.requireNonNull(id, "id must not be null!");
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(final String codigo)
    {
        this.codigo = Objects.requireNonNull(codigo, "codigo must not be null!");
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(final String nombre)
    {
        this.nombre = Objects.requireNonNull(nombre, "nombre must not be null!");
    }

    public Set<Asistencia> getAsistencias()
    {
        return Set.copyOf(asistencias);
    }

    public void setAsistencias(final Set<Asistencia> asistencias)
    {
        this.asistencias = Objects.requireNonNull(asistencias, "asistencias must not be null!");
    }

    public void addAsistencia(final Asistencia asistencia)
    {
        Objects.requireNonNull(asistencia, "asistencia to add must not be null!");
        asistencias.add(asistencia);
        asistencia.setClase(this);
    }

    public void removeAsistencia(final Asistencia asistencia)
    {
        Objects.requireNonNull(asistencia, "asistencia to remove must not be null!");
        asistencias.remove(asistencia);
        asistencia.unsetClase();
    }

    public Set<Alumno> getAlumnos()
    {
        return this.asistencias
                .stream()
                .map(Asistencia::getAlumno)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase other = (Clase) o;
        return Objects.equals(codigo, other.codigo);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(codigo);
    }

    @Override
    protected String defineObjAttrs()
    {
        return String.format("id=%s, codigo='%s', nombre='%s', asistencias=%s, alumnos=%s",
                id, codigo, nombre, asistencias.size(), getAlumnos().size());
    }
}
