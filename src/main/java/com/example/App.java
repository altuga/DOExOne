package com.example;

import com.example.legacy.Author;
import com.example.legacy.AuthorData;
import com.example.legacy.NameCalculation;
import com.example.legacy.AuthorCalculation;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, Maven Project!");
        
        // Original approach
        Author obj = new Author("Isaac", "Asimov", 500);
        System.out.println("Full name: " + obj.fullName());
        System.out.println("Is prolific: " + obj.isProlific());
        
        System.out.println("\n--- Lean Data-Oriented Programming Approach ---");
        
        // Pure data objects
        AuthorData authorData = new AuthorData("Isaac", "Asimov", 500);
        

        // Behavior functions work with any compatible data
        System.out.println("Author full name: " + NameCalculation.fullName(authorData));
       

        // Domain-specific calculations
        System.out.println("Author is prolific: " + AuthorCalculation.isProlific(authorData));
        System.out.println("Author productivity: " + AuthorCalculation.getProductivityLevel(authorData));
        
        // More examples
        AuthorData beginnigAuthor = new AuthorData("Jane", "Smith", 3);
        System.out.println("\nBeginner author: " + NameCalculation.fullName(beginnigAuthor));
        System.out.println("Productivity: " + AuthorCalculation.getProductivityLevel(beginnigAuthor));
    }
}