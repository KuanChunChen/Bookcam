package com.lhh.apst.advancedpagerslidingtabstrip;


public class SpecialBook { // VO- Value Object
    private int id;
    private int image;
    private String name;
    private String news;
    private String location;

    public SpecialBook() {
        super();
    }

    public SpecialBook(int id, int image, String name,String news,String location) {
        super();
        this.id = id;
        this.image = image;
        this.name = name;
        this.news=news;
        this.location=location;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}