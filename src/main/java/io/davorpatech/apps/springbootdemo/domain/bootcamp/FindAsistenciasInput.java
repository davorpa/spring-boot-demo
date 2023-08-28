package io.davorpatech.apps.springbootdemo.domain.bootcamp;

import io.davorpatech.fwk.model.BaseValueObject;

import java.util.Objects;

public class FindAsistenciasInput extends BaseValueObject // NOSONAR
{
    private static final long serialVersionUID = -375698255318579169L;

    private final int pageNumber;

    private final int pageSize;

    public FindAsistenciasInput(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindAsistenciasInput other = (FindAsistenciasInput) o;
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
