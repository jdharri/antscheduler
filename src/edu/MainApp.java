package edu;

import edu.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the scheduler JavaFX app
 * @author jdharri
 */
public class MainApp extends Application {

    /**
     * Current user logged into the app
     */
    public static User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/fxml/login.fxml"));
        Scene scene = new Scene(root, 600, 550);
        stage.setTitle("Scheduler");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @return
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param currentUser
     */
    public static void setCurrentUser(User currentUser) {
        MainApp.currentUser = currentUser;
    }

}
