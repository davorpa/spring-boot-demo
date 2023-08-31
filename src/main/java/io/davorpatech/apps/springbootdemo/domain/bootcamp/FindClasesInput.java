package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import java.util.Objects;

public class FindClasesInput extends BaseValueObject
{
    private static final long serialVersionUID = -4836130497940231133L;

    private final int pageNumber;

    private final int pageSize;

    public FindClasesInput(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindClasesInput other = (FindClasesInput) o;
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

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}