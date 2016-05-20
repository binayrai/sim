package com.elite.dto;


import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {

    private int totalPages;
    private int currentPage;

    private PageDTO firstPage;
    private PageDTO prevPage;
    private List<PageDTO> pages;
    private PageDTO nextPage;
    private PageDTO lastPage;

    private long totalRows;
    public PaginationDTO(int pageSize, long totalRows, int currentPage, final String path, final String queryString) {
        this.currentPage = currentPage;
        this.totalRows= totalRows;
        int beginPage = Math.max(1, currentPage - 5);
        totalPages = (int) Math.ceil(((double) totalRows / pageSize));
        int endPage = Math.min(beginPage + 10, totalPages);
        pages = new ArrayList<>();
        if (totalRows > 0) {
            if (currentPage != 1) {
                firstPage = new PageDTO(1, getPagedUrl(path, 1, queryString));
                prevPage = new PageDTO(currentPage - 1, getPagedUrl(path, currentPage - 1, queryString));
            }
            for (int i = beginPage; i <= endPage; i++) {
                pages.add(new PageDTO(i, getPagedUrl(path, i, queryString)));
            }
            if (currentPage != totalPages) {
                nextPage = new PageDTO(currentPage + 1, getPagedUrl(path, currentPage + 1, queryString));
                lastPage = new PageDTO(totalPages, getPagedUrl(path, totalPages, queryString));
            }
        }
    }

    private String getPagedUrl(String path, int pageNumber, String queryString) {
        String url = path.replace("{pageNumber}", pageNumber + "");
        if (queryString == null) {
            return url;
        }
        return url + "?" + queryString;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PageDTO> getPages() {
        return pages;
    }

    public PageDTO getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(PageDTO firstPage) {
        this.firstPage = firstPage;
    }

    public PageDTO getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(PageDTO prevPage) {
        this.prevPage = prevPage;
    }

    public PageDTO getNextPage() {
        return nextPage;
    }

    public void setNextPage(PageDTO nextPage) {
        this.nextPage = nextPage;
    }

    public PageDTO getLastPage() {
        return lastPage;
    }

    public void setLastPage(PageDTO lastPage) {
        this.lastPage = lastPage;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }
}
