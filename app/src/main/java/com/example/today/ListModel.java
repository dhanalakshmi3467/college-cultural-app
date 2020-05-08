package com.example.today;

/**
 * Created by Sourav on 3/1/2017.
 */

public class ListModel {
    private int id;
    private String title;
    private String shortdesc;
    private double price;
    private String image;

    public ListModel(int id, String title) {
        this.id = id;
        this.title = title;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}