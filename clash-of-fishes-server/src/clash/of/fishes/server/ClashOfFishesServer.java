package clash.of.fishes.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Server that keeps track of all the existing Game Lobbies for the Clash of Fishes game.
 * @author Hein
 */
public class ClashOfFishesServer extends Application{

    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clash/of/fishes/server/ui/LobbyServerGUI.fxml"));
        root = (Parent) loader.load();
        primaryStage.setTitle("Admin lobby control center");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
}
