package org.example.DAOFactory;

import org.example.DAO.BookDAO;
import org.example.DAO.DAOImpl.BookDAOImpl;

public class BookDAOFactory {
    private static BookDAO bookDAO;

    private BookDAOFactory(){}

    public static BookDAO getBookDAO() {
        if(bookDAO == null)
            bookDAO = new BookDAOImpl();
        return bookDAO;
    }
}
