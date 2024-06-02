package com.example.finalmobiletest.Activity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quote implements Serializable {
    @SerializedName("text")
    private String text;

    @SerializedName("author")
    private String author;

    @SerializedName("category")
    private String category;

    @SerializedName("id")
    private int id;

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
