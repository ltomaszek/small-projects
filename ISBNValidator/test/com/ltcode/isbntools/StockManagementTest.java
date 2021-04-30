package com.ltcode.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    @Test
    public void testCanGetACorrectLocatorCode() {

        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    /**
     * Test if the databaseService.lookup() method was called
     */
    public void databaseIsUsedWhenDataIsPresent() {
        final String ISBN = "0140177396";
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup(ISBN)).thenReturn(new Book(ISBN, "not important", "not important"));
        when(webService.lookup(ISBN)).thenReturn(new Book(ISBN, "not important", "not important"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String locatorCode = stockManager.getLocatorCode(ISBN);

        // checks if the lookup method on databaseService was called exactly 1 time
        verify(databaseService, times(1)).lookup(ISBN);
        // verify that the lookup method on the webService was never called
        verify(webService, never()).lookup(anyString());
    }

    @Test
    /**
     * Test if the webService.lookup() method was called
     */
    public void webServiceIsUsedWhenDataIsNotPresentInDatabase() {
        final String ISBN = "0140177396";
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup(ISBN)).thenReturn(null);
        when(webService.lookup(ISBN)).thenReturn(new Book(ISBN, "not important", "not important"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String locatorCode = stockManager.getLocatorCode(ISBN);

        // checks if the lookup method on databaseService was called exactly 1 time
        verify(databaseService).lookup(ISBN);   // by default times(1)
        // verify that the lookup method on the webService was called 1 time - because the data does not exists in local database
        verify(webService, times(1)).lookup(ISBN);
    }
}
