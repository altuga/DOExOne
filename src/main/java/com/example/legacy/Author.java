package com.example.legacy;

public class Author {
    private String firstName;
    private String lastName;
    private int books;
    
    public Author(String firstName, String lastName, int books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }
    
    public String fullName() {
        return this.firstName + " " + this.lastName;
    }
    
    public boolean isProlific() {
        return this.books > 100;
    }
}
