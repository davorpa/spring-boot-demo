package io.davorpatech.apps.springbootdemo.persistence.model.bootcamp;

import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnDelete;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity
@Table(
        name = "ASISTENCIA",
        schema = "BOOTCAMP",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_bootcamp_asistencia_clase_alumno_fecha",
                        columnNames = {"clase_id", "alumno_id", "fecha"}
                )
        }
)
public class Asistencia // NOSONAR
        extends BaseEntity<Long> // NOSONAR
        implements AuditAccessor // NOSONAR
{
    private static final long serialVersionUID = 1557607792781748416L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bootcamp_asistencia_generator")
    @SequenceGenerator(
            name = "bootcamp_asistencia_generator", sequenceName = "bootcamp_asistencia_seq",
            initialValue = 1, allocationSize = 50)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class, OnDelete.class })
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "clase_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "FK_bootcamp_asistencia_clase_id"))
    @Valid
    private Clase clase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "alumno_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "FK_bootcamp_asistencia_alumno_id"))
    @Valid
    private Alumno alumno;

    @Column(name = "fecha", nullable = false, updatable = false)
    @NotNull
    @PastOrPresent
    private LocalDate fecha;

    @Column(name = "asiste", nullable = false)
    private boolean asiste = false;

    @Embedded
    private final Audit audit = new Audit();

    public Asistencia() {
        super();
    }

    public Asistencia(final Clase clase, final Alumno alumno, final LocalDate fecha, final boolean asiste) {
        super();
        setClase(clase);
        setAlumno(alumno);
        setFecha(fecha);
        setAsiste(asiste);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asistencia other = (Asistencia) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, clase_id=%s, alumno_id=%s, fecha='%s', asiste=%s",
                id, getClaseId(), getAlummoId(), fecha, asiste);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Clase getClase() {
        return clase;
    }

    public Long getClaseId() {
        Clase target = getClase();
        return target == null ? null : target.getId();
    }

    public void setClase(final Clase clase) {
        this.clase = Objects.requireNonNull(clase, "clase must not be null!");
    }

    void unsetClase() {
        this.clase = null;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Long getAlummoId() {
        Alumno target = getAlumno();
        return target == null ? null : target.getId();
    }

    public void setAlumno(final Alumno alumno) {
        this.alumno = Objects.requireNonNull(alumno, "alumno must not be null!");
    }

    void unsetAlumno() {
        this.alumno = null;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDate fecha) {
        this.fecha = Objects.requireNonNull(fecha, "fecha must not be null!");
    }

    public boolean isAsiste() {
        return asiste;
    }

    public void setAsiste(final boolean asiste) {
        this.asiste = asiste;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
