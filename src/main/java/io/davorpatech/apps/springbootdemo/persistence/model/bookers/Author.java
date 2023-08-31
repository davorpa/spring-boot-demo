package io.davorpatech.apps.springbootdemo.persistence.model.bookers;

import io.davorpatech.apps.springbootdemo.domain.bookers.AuthorConstants;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "AUTHOR", schema = "BOOKERS")
public class Author // NOSONAR
        extends BaseEntity<Long> // NOSONAR
{
    private static final long serialVersionUID = 2108924380920999284L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookers_author_generator")
    @SequenceGenerator(name = "bookers_author_generator", sequenceName = "bookers_author_seq")
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "fullname", length = AuthorConstants.FULLNAME_MAXLEN, nullable = false)
    @NotBlank
    @Size(min = AuthorConstants.FULLNAME_MINLEN, max = AuthorConstants.FULLNAME_MAXLEN)
    private String fullname;

    @Column(name = "email", length = AuthorConstants.EMAIL_MAXLEN, nullable = true)
    @Size(min = AuthorConstants.EMAIL_MINLEN, max = AuthorConstants.EMAIL_MAXLEN)
    @Email
    private String email;

    @Column(name = "birthdate", nullable = true)
    private LocalDate birthdate;

    @Column(name = "country", length = AuthorConstants.COUNTRY_MAXLEN, nullable = true)
    @Size(min = AuthorConstants.COUNTRY_MINLEN, max = AuthorConstants.COUNTRY_MAXLEN)
    private String country;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @OrderBy("id ASC")
    Set<@Valid Book> books = new LinkedHashSet<>(); // NOSONAR

    public Author() {
        super();
    }

    public Author(final String fullname, final String email, final LocalDate birthdate, final String country) {
        super();
        setFullname(fullname);
        setEmail(email);
        setBirthdate(birthdate);
        setCountry(country);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, fullname='%s', email='%s', birthdate='%s', country='%s'",
                id, fullname, email, birthdate, country);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Book> getBooks() {
        return Set.copyOf(books);
    }

    public void setBooks(Set<Book> books) {
        this.books = Objects.requireNonNull(books, "books must not be null!");
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.authors.add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.authors.remove(this);
    }
}
