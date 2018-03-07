package com.testcasegeneration.model;

import java.io.Serializable;

public class TagValuesCO implements Serializable {
    private String id;
    private String tagValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagValues() {
        return tagValues;
    }

    public void setTagValues(String tagValues) {
        this.tagValues = tagValues;
    }
}
