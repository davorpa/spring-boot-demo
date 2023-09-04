package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;

public class FindAsistenciasInput // NOSONAR
        extends BaseSortableFindInputCmd // NOSONAR
{
    private static final long serialVersionUID = -375698255318579169L;

    /**
     * Constructs a new {@link FindAsistenciasInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public FindAsistenciasInput(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
