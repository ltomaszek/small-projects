package com.ltcode.isbntools;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ValidateISBNTest {

    @Test
    public void checkAValidISBNWith10Digits() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0198526636");
        assertTrue(result, "First assertion");
        result = validator.checkISBN("0140177396");
        assertTrue(result, "Second assertion");
    }

    @Test
    public void checkAnInvalidISBNWith10Digits() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("1404491174");
        assertFalse(result);
    }

    @Test
    public void nineDigitISBNIsNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(
                NumberFormatException.class,
                () -> validator.checkISBN("123456789"));
    }

    @Test
    public void nonNumericISBNIsNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(
                NumberFormatException.class,
                () -> validator.checkISBN("blablablad"));
    }

    @Test
    public void ISBNNumberEndingWithAnXIsValid() {
        ValidateISBN validator = new ValidateISBN();
        assertTrue(validator.checkISBN("000000006X"));
    }

    // 13 digits
    @Test
    public void checkAValidISBNWith13Digits() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781566199094");
        assertTrue(result, "First assertion");
        result = validator.checkISBN("9781402894626");
        assertTrue(result, "Second assertion");
    }

    @Test
    public void checkAnInvalidISBNWith13Digits() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781566195933");
        assertFalse(result);
    }

    @Test
    public void nonNumericISBNIsNotAllowed2() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(
                NumberFormatException.class,
                () -> validator.checkISBN("978140x894626"));
        assertThrows(
                NumberFormatException.class,
                () -> validator.checkISBN("!D8140894626"));
    }

    @Test
    public void ISBNNumberEndingWithAnXIsInvalid() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(
                NumberFormatException.class,
                () ->validator.checkISBN("978156619909X"));
    }
}