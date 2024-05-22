package sep.model.validators;

import java.util.Calendar;

public class BookValidator {

    public static boolean validateISBN(String isbn) {
        return isbn != null && isbn.matches("\\d{13}");
    }

    public static boolean validateYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return year >= 1000 && year <= currentYear;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validatePageCount(String pageCountStr) {
        try {
            int pageCount = Integer.parseInt(pageCountStr);
            return pageCount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateBookDetails(String isbn, String year, String pageCount) {
        return validateISBN(isbn) && validateYear(year) && validatePageCount(pageCount);
    }
}

