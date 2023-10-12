package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class BookItem {
    @Id

    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;

    private double sum;

    public BookItem(Customer customer) {
        this.customer = customer;
    }

    public BookItem(int id, String title, String author, double price, int quantity, double sum) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    @ManyToOne
    private Customer customer;
}
