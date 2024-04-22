package com.example.sep2.model;

import java.util.ArrayList;

public class BookList {
    private ArrayList<Book> books;

    public BookList() {
        books = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    @Override
    public String toString() {
        String string = "";

        for (Book book : books) {
            string += book.toString() + "\n";
        }
        return string;
    }
}
