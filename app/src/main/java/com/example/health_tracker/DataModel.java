package com.example.health_tracker;

public class DataModel {


    private String name;
    private int image;
    private int id;
    private Boolean expanded;
    private String url;

    public DataModel() {
    }

    public DataModel(String name, int image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public DataModel(String name, int id) {
        this.name = name;
        this.id = id;
        this.expanded = false;
    }

    public DataModel(String name, String url) {
        this.name = name;
        this.url = url;
        this.expanded = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
