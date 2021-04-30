package com.ltcode.isbntools;

public class ValidateISBN {

    private final int SHORT_ISBN_LENGTH = 10;
    private final int LONG_ISBN_LENGTH = 13;
    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;

    public boolean checkISBN(String isbn) {
        if (isbn == null || (isbn.length() != SHORT_ISBN_LENGTH && isbn.length() != LONG_ISBN_LENGTH))
            throw new NumberFormatException("ISBN number must be 10 or 13 digits long");

        if (isbn.length() == SHORT_ISBN_LENGTH)
            return checkShortISBN(isbn);
        else
            return checkLongISBN(isbn);
    }

    private boolean checkShortISBN(String isbn) {
        long isbnNumber = convert(isbn);
        int weight = 1;
        int weightSum = isbn.charAt(isbn.length() - 1) == 'X' ? 10 : 0;
        while (isbnNumber > 0) {
            int digit = (int) (isbnNumber % 10);
            weightSum += digit * weight;
            weight += 1;
            isbnNumber /= 10;
        }
        return weightSum % SHORT_ISBN_MULTIPLIER == 0;
    }

    private boolean checkLongISBN(String isbn) {
        long isbnNumber = convert(isbn);
        int weightSum = 0;
        while (isbnNumber > 0) {
            int digit = (int) (isbnNumber % 10);
            weightSum += digit;
            isbnNumber /= 10;

            digit = (int) (isbnNumber % 10);
            weightSum += digit * 3;
            isbnNumber /= 10;
        }
        return weightSum % LONG_ISBN_MULTIPLIER == 0;
    }

    /**
     * Converts String format of isbn number to long format
     * The ending 'X' is converted to 0, but in the weight sum it is equal to 10
     *
     * @param isbn - String representation of the isbn number
     * @return long representation of the isbn number
     * @throws NumberFormatException
     */
    private long convert(String isbn) throws NumberFormatException {
        try {
            if (isbn.length() == SHORT_ISBN_LENGTH && isbn.charAt(SHORT_ISBN_LENGTH - 1) == 'X') {
                long isbnNumber = Long.parseLong(isbn.substring(0, SHORT_ISBN_LENGTH - 1));
                return isbnNumber * 10;
            } else {
                return Long.parseLong(isbn);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ISBN number can only contain numeric digits. ISBN with 10 digits" +
                    " can contain 'X' at the end");
        }
    }
}
