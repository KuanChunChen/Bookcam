package com.lhh.apst.fragments;


public class Member { // VO- Value Object  裡面有什麼內容
    private int id;
    private int image;
    private String name;

    public Member() {
        super();
    }

    public Member( int image, String name) {
        super();
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
