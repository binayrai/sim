package com.elite.dto;

/**
 * Created by binaychap on 2/24/2016.
 */
public class ParentSearchRequest extends SearchRequest {

    public ParentSearchRequest() {
        super(0, 10);
        this.name = "";
    }

    public ParentSearchRequest(String name, int page, int size) {
        super(page, size);
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
