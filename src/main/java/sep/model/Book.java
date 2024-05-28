package sep.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a book with title, author, publisher, year, isbn, page count, id, genre, return date, state and the borrower.
 * Implements Serializable interface to allow the object to be serialized.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class Book implements Serializable {
    private String title;
    private String author;
    private String publisher;
    private int year;
    private long isbn;
    private int pageCount;
    private int bookId;
    private String genre;
    private String returnDate;

    private State state;
    private Patron borrower;


    /**
     * Constructs a new Book with the specified details.
     *
     * @param bookId the unique identifier of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param year the year the book was published
     * @param publisher the publisher of the book
     * @param isbn the ISBN of the book
     * @param pageCount the number of pages in the book
     * @param genre the genre of the book
     */
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
        this.returnDate = null;
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the year of the book.
     *
     * @return the year of the book
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the book.
     *
     * @param year the year of the book
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher the publisher of the book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the isbn of the book.
     *
     * @return the isbn of the book
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * Sets the isbn of the book.
     *
     * @param isbn the isbn of the book
     */
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the page count of the book.
     *
     * @return the page count of the book
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets the page count of the book.
     *
     * @param pageCount the page count of the book
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * Gets the genre of the book.
     *
     * @return the genre of the book
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     *
     * @param genre the genre of the book
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the id of the book.
     *
     * @return the book id
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the id of the book.
     *
     * @param bookId the id of the book
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the state of the book.
     *
     * @return the state of the book
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the state of the book.
     *
     * @param state the state of the book
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Sets the borrower of the book.
     *
     * @param borrower the borrower of the book
     */
    public void setBorrower(Patron borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets the borrower of the book.
     *
     * @return the borrower of the book
     */
    public Patron getBorrower() {
        return borrower;
    }

    /**
     * Gets the return date of the book.
     *
     * @return the return date of the book
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the book.
     *
     * @param returnDate the new return date of the book
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Borrows the book for the specified patron.
     *
     * @param book the book to be borrowed
     * @param patron the patron borrowing the book
     */
    public void borrow(Book book, Patron patron)
    {
        state.borrow(book,patron);
    }

    /**
     * Returns the book from the specified patron.
     *
     * @param book the book to be returned
     * @param patron the patron returning the book
     */
    public void returnBook(Book book, Patron patron)
    {
        state.returnBook(book, patron);
    }

    /**
     * Checks if this book is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the books are equal, false otherwise
     */
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

    /**
     * Returns a string representation of the book, including its title, author, publisher, year, ISBN, page count, and genre.
     *
     * @return a string representation of the book
     */
    @Override
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
