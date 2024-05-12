package sep.model;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private String title;
    private String author;
    private String publisher;
    private int year;
    private long isbn;
    private int pageCount;
    private int bookId;
    private String genre;

    private State state;
    private Patron borrower;


    public Book(int bookId, String title, String author,int year, String publisher, long isbn, int pageCount, String genre){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.genre = genre;
        this.state = new Available();
        this.borrower = null;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setBorrower(Patron borrower) {
        this.borrower = borrower;
    }

    public Patron getBorrower() {
        return borrower;
    }

    public void borrow(Book book, Patron patron)
    {
        state.borrow(book,patron);
    }
    public void returnBook(Book book, Patron patron)
    {
        state.returnBook(book, patron);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(this.borrower, book.borrower);
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
                ", bookID=" + bookId +
                ", genre='" + genre + '\'' +
                '}';
    }
}
