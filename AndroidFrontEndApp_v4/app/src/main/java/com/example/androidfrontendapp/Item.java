package com.example.androidfrontendapp;

public class Item {

    private String imageUrl;
    private String Creator;
    private int Likes;

    public Item(String imageUrl, String Creator, int Likes)
    {
        this.Creator = Creator;
        this.imageUrl = imageUrl;
        this.Likes = Likes;
    }

    public String getImageUrl()
    {
        return this.imageUrl;
    }

    public String getCreator()
    {
        return this.Creator;
    }

    public int getLikes()
    {
        return this.Likes;
    }
}
