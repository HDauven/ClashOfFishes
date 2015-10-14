package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.ui.LoginController;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bram on 1/10/15.
 */
public class DatabaseStorage implements Storage {

    private DatabaseConnector databaseConnector;
    
    public DatabaseStorage() {
        databaseConnector = new DatabaseConnector();
    }
    
    @Override
    public Boolean addUser(String username, String confirmedPassword, String email)
    {
        Boolean userAdded = false;
        
        CallableStatement registerUserStatement = this.databaseConnector.getStatement(Statement.REGISTER_USER);
        try {
            registerUserStatement.setString(1, username);
            registerUserStatement.setString(2, confirmedPassword);
            registerUserStatement.setString(3, email);
            registerUserStatement.registerOutParameter(4, java.sql.Types.BOOLEAN);
            registerUserStatement.execute();
            userAdded = registerUserStatement.getBoolean(4);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return userAdded;
        }
    }

    @Override
    public User getUser(String username)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User logIn(String userIdentifier, String password)
    {
        int result = 0;
        String email = null;
        CallableStatement loginStatement = this.databaseConnector.getStatement(Statement.LOGIN);
        try {
            loginStatement.setString(1, userIdentifier);
            loginStatement.setString(2, password);
            loginStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            loginStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
            loginStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            loginStatement.execute();
            result = loginStatement.getInt(3);
            userIdentifier = loginStatement.getString(4);
            email = loginStatement.getString(5);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result > 0) {
            return new User(userIdentifier, email, 0);
        }
        return null;
    }
}
