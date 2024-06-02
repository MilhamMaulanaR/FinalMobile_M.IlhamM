package com.example.finalmobiletest.Model;

public class Bookmark {
    private String title;
    private String author;
    private String category;
    private int id;
    private int quoteId;
    private int userId;

    public String getTitle() {
        return title;
    }

    public Bookmark(int id, String title, String author, String category, int quoteId, int userId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.quoteId = quoteId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

