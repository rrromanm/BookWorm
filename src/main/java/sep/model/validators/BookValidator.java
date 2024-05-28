package sep.model.validators;

import java.util.Calendar;

/**
 * The BookValidator class provides methods to validate various details of a book,
 * such as ISBN, publication year, and page count.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class BookValidator {

    /**
     * Validates the format of the ISBN.
     *
     * @param isbn The ISBN to be validated
     * @return true if the ISBN is not null and matches the pattern of 13 digits, false otherwise
     */
    public static boolean validateISBN(String isbn) {
        return isbn != null && isbn.matches("\\d{13}");
    }

    /**
     * Validates the publication year of a book.
     *
     * @param yearStr The year of publication to be validated as a string
     * @return true if the publication year is within a reasonable range (from the year 1000 to the current year), false otherwise
     */
    public static boolean validateYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return year >= 1000 && year <= currentYear;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the page count of a book.
     *
     * @param pageCountStr The page count of the book to be validated as a string
     * @return true if the page count is a positive integer, false otherwise
     */
    public static boolean validatePageCount(String pageCountStr) {
        try {
            int pageCount = Integer.parseInt(pageCountStr);
            return pageCount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates all details of a book, including ISBN, publication year, and page count.
     *
     * @param isbn       The ISBN of the book
     * @param year       The publication year of the book
     * @param pageCount  The page count of the book
     * @return true if all details of the book are valid, false otherwise
     */
    public static boolean validateBookDetails(String isbn, String year, String pageCount) {
        return validateISBN(isbn) && validateYear(year) && validatePageCount(pageCount);
    }
}

