package io.davorpatech.apps.springbootdemo.persistence.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoConstants;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
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
@Table(name = "ALUMNO", schema = "BOOTCAMP")
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class Alumno extends BaseEntity<Long> // NOSONAR
{
    private static final long serialVersionUID = 7421101598367640587L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bootcamp_alumno_generator")
    @SequenceGenerator(
            name = "bootcamp_alumno_generator", sequenceName = "bootcamp_alumno_seq",
            initialValue = 1, allocationSize = 50)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @NaturalId(mutable = false)
    @Column(name = "nid", length = AlumnoConstants.NID_MAXLEN, nullable = false, updatable = false)
    @NotBlank
    @Size(min = AlumnoConstants.NID_MINLEN, max = AlumnoConstants.NID_MAXLEN)
    @Pattern(regexp = AlumnoConstants.NID_REGEX)
    private String nid;

    @Column(name = "fullname", length = AlumnoConstants.FULLNAME_MAXLEN, nullable = false)
    @NotBlank
    @Size(min = AlumnoConstants.FULLNAME_MINLEN, max = AlumnoConstants.FULLNAME_MAXLEN)
    private String fullname;

    @OneToMany(mappedBy = "alumno", orphanRemoval = true)
    @OrderBy("clase.id ASC, fecha ASC")
    private Set<@Valid Asistencia> asistencias = new LinkedHashSet<>();

    public Alumno()
    {
        super();
    }

    public Alumno(final String nid, final String fullname) {
        super();
        setNid(nid);
        setFullname(fullname);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno other = (Alumno) o;
        return Objects.equals(nid, other.nid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nid);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, nid='%s', fullname='%s', asistencias=%s, clases=%s",
                id, nid, fullname, asistencias.size(), getClases().size());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(final String nid) {
        this.nid = Objects.requireNonNull(nid, "nid must not be null!");
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public Set<Asistencia> getAsistencias() {
        return Set.copyOf(asistencias);
    }

    public void setAsistencias(
            final Set<Asistencia> asistencias) {
        this.asistencias = Objects.requireNonNull(asistencias, "asistencias must not be null!");
    }

    public void addAsistencia(
            final Asistencia asistencia) {
        Objects.requireNonNull(asistencia, "asistencia to add must not be null!");
        asistencias.add(asistencia);
        asistencia.setAlumno(this);
    }

    public void removeAsistencia(
            final Asistencia asistencia) {
        Objects.requireNonNull(asistencia, "asistencia to remove must not be null!");
        asistencias.remove(asistencia);
        asistencia.unsetAlumno();
    }

    public Set<Clase> getClases() {
        return this.asistencias
                .stream()
                .map(Asistencia::getClase)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
