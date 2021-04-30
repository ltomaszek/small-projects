package com.ltcode.isbntools;

public class StockManager {

    private ExternalISBNDataService webService;
    private ExternalISBNDataService databaseService;

    public ExternalISBNDataService setWebService(ExternalISBNDataService service) {
        return this.webService = service;
    }

    public ExternalISBNDataService setDatabaseService(ExternalISBNDataService service) {
        return this.databaseService = service;
    }

    public String getLocatorCode(String isbn) {
        Book book = databaseService.lookup(isbn);
        if (book == null)
            book = webService.lookup(isbn);

        StringBuilder locator = new StringBuilder();
        locator
                .append(isbn.substring(isbn.length() - 4))
                .append(book.getAuthor().charAt(0))
                .append(book.getTitle().split(" ").length);
        return locator.toString();
    }
}
