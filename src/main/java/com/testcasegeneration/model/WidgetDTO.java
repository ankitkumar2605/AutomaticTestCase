package com.testcasegeneration.model;


import java.util.ArrayList;
import java.util.List;

public class WidgetDTO {
    private String name;
    private List<String> tagValues = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTagValues() {
        return tagValues;
    }

    public void setTagValues(List<String> tagValues) {
        this.tagValues = tagValues;
    }
}
