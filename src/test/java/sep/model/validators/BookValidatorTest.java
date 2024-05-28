package sep.model.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorTest {

    @Test
    public void inputtingThirteenNumberIsbnIsValid() {
        assertTrue(BookValidator.validateISBN("9781234567890"));
    }

    @Test
    public void inputtingTwelveNumberIsbnIsNotValid() {
        assertFalse(BookValidator.validateISBN("978123456789"));
    }

    @Test
    public void inputtingFourteenNumberIsbnIsNotValid() {
        assertFalse(BookValidator.validateISBN("97812345678901"));
    }

    @Test
    public void inputtingLettersIntoIsbnIsNotValid(){
        assertFalse(BookValidator.validateISBN("abc1234567890"));
    }

    @Test
    void inputtingNullIntoIsbnIsNotValid()
    {
        assertFalse(BookValidator.validateISBN(null));
    }

    @Test
    public void inputtingTwoThousandIsValid() {
        assertTrue(BookValidator.validateYear("2000"));
    }

    @Test
    public void inputtingFourThousandIsNotValid() {
        assertFalse(BookValidator.validateYear("4000"));
    }

    @Test
    public void inputtingNineHundredNinetyNineIsNotValid() {
        assertFalse(BookValidator.validateYear("999"));
    }

    @Test
    public void inputtingLettersIsNotValid()
    {
        assertFalse(BookValidator.validateYear("abcd"));
    }

    @Test
    public void inputtingHundredIntoPageCountIsValid()
    {
        assertTrue(BookValidator.validatePageCount("100"));
    }

    @Test
    public void inputtingZeroIntoPageCountIsNotValid()
    {
        assertFalse(BookValidator.validatePageCount("0"));
    }

    @Test
    public void inputtingOneIntoPageCountIsValid()
    {
        assertTrue(BookValidator.validatePageCount("1"));
    }

    @Test
    public void inputtingMinusOneIntoPageCountIsNotValid()
    {
        assertFalse(BookValidator.validatePageCount("-1"));
    }

    @Test
    public void inputtingLettersIntoPageCountIsNotValid()
    {
        assertFalse(BookValidator.validatePageCount("abcd"));
    }

    @Test
    public void inputtingNullIntoPageCountIsNotValid()
    {
        assertFalse(BookValidator.validatePageCount(null));
    }

    @Test
    public void testValidateBookDetails() {
        assertTrue(BookValidator.validateBookDetails("9781234567890", "2000", "300"));
        assertFalse(BookValidator.validateBookDetails("978123456789", "2000", "300"));
        assertFalse(BookValidator.validateBookDetails("9781234567890", "999", "300"));
        assertFalse(BookValidator.validateBookDetails("9781234567890", "2000", "0"));
        assertFalse(BookValidator.validateBookDetails("978123456789", "999", "0"));
    }
}