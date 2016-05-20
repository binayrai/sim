package com.elite.dto;

import java.util.Collections;
import java.util.List;

public class PagedResultDTO<T> {
    private List<T> elements;
    private long totalCount;

    public PagedResultDTO(final List<T> elements, final long totalCount) {
        this.elements = Collections.unmodifiableList(elements);
        this.totalCount = totalCount;
    }

    public List<T> getElements() {
        return elements;
    }

    public long getTotalCount() {
        return totalCount;
    }
}
