package com.example.today;

/**
 * Created by Sourav on 3/1/2017.
 */

public class ListModel2 {
    private int id;
    private String title;
    private String shortdesc;
    private double price;
    private String image;

    public ListModel2(int id, String title, String shortdesc, double price, String image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;

        this.price = price;
        this.image = image;
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

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}