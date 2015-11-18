package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.Highscore;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.ui.LoginController;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bram on 1/10/15.
 */
public class DatabaseStorage implements Storage
{

    private DatabaseConnector databaseConnector;

    public DatabaseStorage()
    {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public Boolean addUser(String username, String confirmedPassword, String email)
    {
        Boolean userAdded = false;

        CallableStatement registerUserStatement = this.databaseConnector.getStatement(Statement.REGISTER_USER);
        try
        {
            registerUserStatement.setString(1, username);
            registerUserStatement.setString(2, confirmedPassword);
            registerUserStatement.setString(3, email);
            registerUserStatement.registerOutParameter(4, java.sql.Types.BOOLEAN);
            registerUserStatement.execute();
            userAdded = registerUserStatement.getBoolean(4);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userAdded;
    }

    public User logIn(String userIdentifier, String password)
    {
        User u = null;
        int id = 0;
        String email = null;
        HashMap<GameMode, Integer> highscores = new HashMap<>();
        CallableStatement loginStatement = this.databaseConnector.getStatement(Statement.LOGIN);

        try
        {
            loginStatement.setString(1, userIdentifier);
            loginStatement.setString(2, password);
            loginStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            loginStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
            loginStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            ResultSet resultSet = loginStatement.executeQuery();
            id = loginStatement.getInt(3);
            userIdentifier = loginStatement.getString(4);
            email = loginStatement.getString(5);
            /*
             while (resultSet.next())
             {
             //newscore
             GameMode mode = GameMode.valueOf(resultSet.getString(1));
             highscores.put(mode, resultSet.getInt(2));
             }
             */
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (id > 0)
        {
            u = new User(id, userIdentifier, email);
            this.getScores(u);
        }
        return u;
    }

    public ArrayList<Highscore> getAllUserHighscoresForGameMode(GameMode gameMode)
    {

        ArrayList<Highscore> UserHighscores = new ArrayList<>();
        CallableStatement highscoreStatement = this.databaseConnector.getStatement(Statement.GET_ALL_USER_HIGHSCORES);
        try
        {
            highscoreStatement.setString(1, gameMode.toString());
            ResultSet resultSet = highscoreStatement.executeQuery();
            while (resultSet.next())
            {
                Highscore newHighscore = new Highscore(GameMode.valueOf(resultSet.getString(2)), resultSet.getString(1), resultSet.getInt(3));
                UserHighscores.add(newHighscore);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return UserHighscores;
    }

    @Override
    public User getUser(String username)
    {
        CallableStatement cStmt = this.databaseConnector.getStatement(Statement.GET_USER);
        int id = 0;
        String email = "";
        try
        {
            cStmt.setString(1, username);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            cStmt.execute();
            id = cStmt.getInt(2);
            email = cStmt.getString(3);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(id == 0){
            return null;
        }
        return new User(id, username, email);
    }

    //TO DO Method om highscores op te slaan
    public void updateHighScores(User u)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getScores(User u)
    {
        CallableStatement cStmt = this.databaseConnector.getStatement(Statement.GET_SCORES);
        try
        {
            cStmt.setInt(1, u.getId());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("User_ID");
                GameMode gameMode = null;
                int modeInt = rs.getInt("Gamemode_ID");
                for (GameMode mode : GameMode.values())
                {
                    if (mode.getValue() == modeInt)
                    {
                        gameMode = mode;
                    }
                }
                int score = rs.getInt("Score");
                u.setHighScore(gameMode, score);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void removeUser(int id)
    {
        CallableStatement cStmt = this.databaseConnector.getStatement(Statement.REMOVE_USER);
        try
        {
            cStmt.setInt(1, id);
            cStmt.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public boolean hasConnection()
    {
        return databaseConnector.hasConnection();
    }
}
