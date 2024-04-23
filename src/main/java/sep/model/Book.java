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
    private User borrower;
    private User reservist;


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
        this.borrower = null;
        this.reservist = null;
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

    public void setReservist(User reservist) {
        this.reservist = reservist;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public User getBorrower() {
        return borrower;
    }

    public User getReservist() {
        return reservist;
    }

    @Override
    public boolean equals(Object obj) { // I didn't add the state to the equals method since we dont have the equals method for comparing the states
       if(obj==null || obj.getClass()!=getClass()){
           return false;
       }
       Book other = (Book) obj;
       return other.title.equals(title) && other.author.equals(author) && year == other.year && other.publisher.equals(publisher) &&
        isbn == other.isbn && pageCount == other.pageCount && bookID == other.bookID && genre.equals(other.genre) && borrower.equals(other.borrower) &&
        reservist.equals(other.reservist);
    }
    @Override // toString is missing the info about borrower,reservist and state
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
