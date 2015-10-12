package com.netgames.clashoffishes.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Stef
 */
public class DatabaseConnector
{

    private SortedMap<Statement, CallableStatement> statements = new TreeMap<>();

    public DatabaseConnector() {
        this.createStatements();
    }
    
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://stefp.nl/philips1_db?noAccessToProcedureBodies=true", "philips1_user", "Hallo123");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
        return connection;
    }

    public SortedMap<Statement, CallableStatement> getStatements() {
        return statements;
    }

    public void createStatements()
    {
        try {
            Connection connection = this.getConnection();
            
            statements.put(Statement.LOGIN, connection.prepareCall("{call spLogin(?, ?, ?)}"));
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public CallableStatement getStatement(Statement statement)
    {
        return statements.get(statement);
    }
}
