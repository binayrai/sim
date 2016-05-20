package com.elite.dto;


public class PageDTO {

    public PageDTO(int pageNo, String url) {
        this.pageNo = pageNo;
        this.url = url;
    }

    private int pageNo;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
