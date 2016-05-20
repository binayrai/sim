package com.elite.dto;

public class SearchRequest {
    private static final int DEFAULT_ELEMENTS_IN_LIST = 100;
    private static final int MAX_ELEMENTS_IN_LIST = 500;

    private int page;
    private int size;

    public SearchRequest() {
        this(0, DEFAULT_ELEMENTS_IN_LIST);
    }

    public SearchRequest(final int page, final int size) {
        setPage(page);
        setSize(size);
    }

    public final int getPage() {
        return page;
    }

    public final void setPage(final int page) {
        this.page = page;
    }

    public final int getSize() {
        return size;
    }

    public final void setSize(final int size) {
        this.size = Math.min(MAX_ELEMENTS_IN_LIST, size);
    }
}
