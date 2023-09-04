package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;

public class FindClasesInput // NOSONAR
        extends BaseSortableFindInputCmd // NOSONAR
{
    private static final long serialVersionUID = -4836130497940231133L;

    /**
     * Constructs a new {@link FindClasesInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public FindClasesInput(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
