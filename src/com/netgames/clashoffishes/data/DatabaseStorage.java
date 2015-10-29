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
import java.util.List;
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
        finally
        {
            return userAdded;
        }
    }

    public User logIn(String userIdentifier, String password)
    {
        int result = 0;
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
            result = loginStatement.getInt(3);
            userIdentifier = loginStatement.getString(4);
            email = loginStatement.getString(5);

            while (resultSet.next())
            {
                //newscore
                GameMode mode = GameMode.valueOf(resultSet.getString(1));
                highscores.put(mode, resultSet.getInt(2));
            }

        }
        catch (SQLException ex)
        {
            //TODO Betere exception afhandeling
            System.out.println("Fout met verbinden database");
        }
        if (result > 0)
        {
            //TODO Scores toevoegen aan user
            User u = new User(userIdentifier, email);
            return u;
        }
        return null;
    }

    public List<Highscore> getAllUserHighscoresForGameMode(GameMode gameMode)
    {

        List<Highscore> UserHighscores = new ArrayList<>();
        CallableStatement highscoreStatement = this.databaseConnector.getStatement(Statement.GET_ALL_USER_HIGHSCORES);
        try
        {
            highscoreStatement.setString(1, gameMode.toString());
            ResultSet resultSet = highscoreStatement.executeQuery();
            while (resultSet.next())
            {
                Highscore newHighscore = new Highscore(GameMode.valueOf(resultSet.getString(2)), resultSet.getString(1), resultSet.getInt(3));
                System.out.println(newHighscore.toString());
                UserHighscores.add(newHighscore);
            }
        }
        catch (SQLException ex)
        {
            //TODO Betere exception afhandeling
            System.out.println("Fout met verbinden database");
        }

        return UserHighscores;
    }

    //TODO Nog niet geimplementeerd is deze nog wel nodig????
    @Override
    public User getUser(String username)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //TO DO Method om highscores op te slaan
    public void updateHighScores(User u)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
