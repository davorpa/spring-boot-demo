package io.davorpatech.apps.springbootdemo.persistence.model.bootcamp;

import io.davorpatech.apps.springbootdemo.domain.bootcamp.AlumnoConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity
@Table(
        name = "ALUMNO",
        schema = "BOOTCAMP",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_bootcamp_alumno_nid",
                        columnNames = {"nid"}
                )
        }
)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class Alumno // NOSONAR
        extends BaseEntity<Long> // NOSONAR
        implements AuditAccessor // NOSONAR
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

    @Column(name = "email", length = AlumnoConstants.EMAIL_MAXLEN, nullable = true)
    @Size(max = AlumnoConstants.EMAIL_MAXLEN)
    @Email
    private String email;

    @Embedded
    private final Audit audit = new Audit();

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("clase.id ASC, fecha ASC")
    private Set<@Valid Asistencia> asistencias = new LinkedHashSet<>();

    public Alumno()
    {
        super();
    }

    public Alumno(final String nid, final String fullname, final String email) {
        super();
        setNid(nid);
        setFullname(fullname);
        setEmail(email);
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
        return String.format("id=%s, nid='%s', fullname='%s', email='%s', asistencias=%s, clases=%s",
                id, nid, fullname, email, asistencias.size(), getClases().size());
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
        this.nid = nid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public Audit getAudit() {
        return audit;
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
