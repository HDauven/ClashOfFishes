package com.netgames.clashoffishes.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Stef Philipsen
 */
public class DatabaseConnector {

    private SortedMap<Statement, CallableStatement> statements = new TreeMap<>();
    private boolean hasConnection;
    Connection connection;

    public DatabaseConnector() {
        hasConnection = false;
        this.createStatements();
    }

    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://185.13.227.197:3306/stefpfq165_data?noAccessToProcedureBodies=true", "stefpfq165_dream", "teamdream");
        }
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
        return connection;
    }

    public SortedMap<Statement, CallableStatement> getStatements() {
        return statements;
    }

    public void createStatements() {
        try {
            if (connection == null) {
                connection = this.getConnection();
            }
            if (connection != null) {
                hasConnection = true;
                statements.put(Statement.LOGIN, connection.prepareCall("{call spLogin(?, ?, ?, ?, ?)}"));
                statements.put(Statement.REGISTER_USER, connection.prepareCall("{call spRegister_User(?, ?, ?, ?)}"));
                statements.put(Statement.GET_ALL_USER_HIGHSCORES, connection.prepareCall("{call spGetAllUserHighscores(?)}"));
                statements.put(Statement.GET_SCORES, connection.prepareCall("call getScores(?)"));
                statements.put(Statement.GET_USER, connection.prepareCall("call getUser(?,?,?)"));
                statements.put(Statement.REMOVE_USER, connection.prepareCall("call removeUser(?)"));
            }

        }
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public CallableStatement getStatement(Statement statement) {
        return statements.get(statement);
    }

    public boolean hasConnection() {
        return this.hasConnection;
    }
}
