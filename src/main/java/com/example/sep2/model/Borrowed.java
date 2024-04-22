package com.example.sep2.model;

public class Borrowed implements State {
    @Override
    public void available(Book book) {

    }

    @Override
    public void borrow(Book book) {

    }

    @Override
    public void returnBook(Book book) {
        book.setState(new Available());
    }

    @Override
    public void reserve(Book book) {

    }

    @Override
    public void wishlist(Book book) {

    }
}
