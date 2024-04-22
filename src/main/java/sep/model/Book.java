package sep.model;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int year;
    private long isbn;
    private int pageCount;
    private int bookID; //TODO needs id generator
    private String genre;

    private State state;
    private User user;


    public Book(String title, String author,int year, String publisher, long isbn, int pageCount, int bookID, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.bookID = bookID;
        this.genre = genre;

        this.state = new Available();
        this.user = null;
    }

    public String getTitle() {
        return title;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && isbn == book.isbn && pageCount == book.pageCount && bookID == book.bookID && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publisher, year, isbn, pageCount, bookID, genre);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", isbn=" + isbn +
                ", pageCount=" + pageCount +
                ", bookID=" + bookID +
                ", genre='" + genre + '\'' +
                '}';
    }
}
