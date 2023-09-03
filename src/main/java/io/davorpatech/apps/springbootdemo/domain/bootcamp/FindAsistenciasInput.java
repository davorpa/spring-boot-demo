package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.FindInputCmd;
import io.davorpatech.fwk.model.commands.Sortable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class FindAsistenciasInput // NOSONAR
        extends BaseValueObject // NOSONAR
        implements FindInputCmd, Sortable // NOSONAR
{
    private static final long serialVersionUID = -375698255318579169L;

    private final int pageNumber;

    private final int pageSize;

    private final Sort sort;

    public FindAsistenciasInput(int pageNumber, int pageSize, Sort sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindAsistenciasInput other = (FindAsistenciasInput) o;
        return pageNumber == other.pageNumber &&
                pageSize == other.pageSize &&
                Objects.equals(sort, other.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, sort);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("pageNumber=%s, pageSize=%s, sort=%s", pageNumber, pageSize, sort);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }
}
