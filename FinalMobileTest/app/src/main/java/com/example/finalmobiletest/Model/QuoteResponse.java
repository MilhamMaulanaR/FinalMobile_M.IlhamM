package com.example.finalmobiletest.Model;

import com.example.finalmobiletest.Activity.Quote;

import java.util.List;

public class QuoteResponse {
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
