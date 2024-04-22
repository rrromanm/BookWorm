package com.example.sep2.model;

//TODO user?, responsible JAKUB MATEUSZ ABRAMEK

public interface State {
    void available(Book book);
    void borrow(Book book);
    void returnBook(Book book);
    void reserve(Book book);
    void wishlist(Book book);
}
