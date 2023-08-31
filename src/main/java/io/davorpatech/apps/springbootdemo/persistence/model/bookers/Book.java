package io.davorpatech.apps.springbootdemo.persistence.model.bookers;

import io.davorpatech.apps.springbootdemo.domain.bookers.BookConstants;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "BOOK",
        schema = "BOOKERS",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_bookers_book_isbn",
                        columnNames = {"isbn"}
                )
        }
)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class Book // NOSONAR
        extends BaseEntity<Long> // NOSONAR
{
    private static final long serialVersionUID = -4048102166882221156L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookers_book_generator")
    @SequenceGenerator(name = "bookers_book_generator", sequenceName = "bookers_book_seq")
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @NaturalId(mutable = true)
    @Column(name = "isbn", length = BookConstants.ISBN_MAXLEN, nullable = false)
    @Size(min = BookConstants.ISBN_MINLEN, max = BookConstants.ISBN_MAXLEN)
    @ISBN
    private String isbn;

    @Column(name = "title", length = BookConstants.TITLE_MAXLEN, nullable = false)
    @NotBlank
    @Size(min = BookConstants.TITLE_MINLEN, max = BookConstants.TITLE_MAXLEN)
    private String title;

    @Column(name = "synopsis", length = BookConstants.SYNOPSIS_MAXLEN, nullable = true)
    @Size(min = BookConstants.SYNOPSIS_MINLEN, max = BookConstants.SYNOPSIS_MAXLEN)
    private String synopsis;

    @Column(name = "`year`", nullable = false) // backticked due to "year" is a SQL reserved word
    @NotNull
    private Integer year;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "BOOK_AUTHORSHIP",
            schema = "BOOKERS",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    foreignKey = @ForeignKey(name = "FK_bookers_book_authorship_book_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    foreignKey = @ForeignKey(name = "FK_bookers_book_authorship_author_id")
            )
    )
    @OrderBy("id ASC")
    Set<@Valid Author> authors = new LinkedHashSet<>(); // NOSONAR

    public Book() {
        super();
    }

    public Book(final String isbn, final String title, final String synopsis, final Integer year) {
        super();
        setIsbn(isbn);
        setTitle(title);
        setSynopsis(synopsis);
        setYear(year);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, isbn='%s', title='%s', year=%s",
                id, isbn, title, year);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Set<Author> getAuthors() {
        return Set.copyOf(authors);
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = Objects.requireNonNull(authors, "authors must not be null!");
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.books.add(this);
    }

    public void removeBook(Author author) {
        this.authors.remove(author);
        author.books.remove(this);
    }
}
