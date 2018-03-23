package com.lhh.apst.advancedpagerslidingtabstrip;

import java.io.Serializable;

//Book(id,name, cover, author,publisher,location,picture,rate,like,sell)
public class Book implements Serializable { // VO- Value Object
    private int id;
    private String name;
    private String cover;
    private String author;
    private String publisher;
    private String location;
    private String picture;
    private int rate;
    private int like;
    private int sell;


    public Book(String name, String cover,String author,String publisher,String location,String picture, int rate, int like,int sell) {
        this(0,name,cover,author,publisher,location, picture,rate,like,sell);
    }

    public Book(int id, String name, String cover,String author,String publisher,String location,String picture, int rate, int like,int sell) {
        this.id = id;
        this.name = name;
        this.cover=cover;
        this.author=author;
        this.publisher=publisher;
        this.location=location;
        this.picture=picture;
        this.rate = rate;
        this.like = like;
        this.sell=sell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }


    public  int getLike(){return like;}

    public void setLike(int like) {
        this.like = like;
    }


    public int getSell(){return sell;}

    public void setSell(int sell) {
        this.sell = sell;
    }


}