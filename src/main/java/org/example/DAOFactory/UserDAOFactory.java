package org.example.DAOFactory;

import org.example.DAO.DAOImpl.UserDAOImpl;
import org.example.DAO.UserDAO;

public class UserDAOFactory {
    private static UserDAO userDAO;

    private UserDAOFactory(){}

    public static UserDAO getUserDAO() {
        if(userDAO == null)
            userDAO = new UserDAOImpl();
        return userDAO;
    }

}
