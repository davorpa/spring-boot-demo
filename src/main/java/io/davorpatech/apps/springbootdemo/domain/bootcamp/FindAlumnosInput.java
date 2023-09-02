package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.FindInputCmd;

import java.util.Objects;

public class FindAlumnosInput // NOSONAR
        extends BaseValueObject // NOSONAR
        implements FindInputCmd // NOSONAR
{
    private static final long serialVersionUID = -1005702181697836040L;

    private final int pageNumber;

    private final int pageSize;

    public FindAlumnosInput(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindAlumnosInput other = (FindAlumnosInput) o;
        return pageNumber == other.pageNumber && pageSize == other.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("pageNumber=%s, pageSize=%s", pageNumber, pageSize);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
