package edu.controllers;

import edu.MainApp;
import edu.dao.UserDAO;
import edu.model.User;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.NoResultException;

/**
 *
 * @author jdharri
 */
public class LoginController implements Initializable {

    @FXML
    private Label loginLable;
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button loginButton;
    Locale currentLocale;
    private final UserDAO userDao = new UserDAO();

    /**
     * This method is where the labels are set based on locale
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentLocale = Locale.getDefault();
        //   currentLocale = Locale.FRANCE;
        //if locale is france
        if (currentLocale.equals(Locale.FRANCE)) {
            loginLable.setText("Connexion du programmateur");
            loginUsername.setPromptText("Nom d'utilisateur");
            loginPassword.setPromptText("Mot de passe");
            loginButton.setText("S'identifier");
        }
        //if locale is us
        if (currentLocale.equals(Locale.US)) {
            loginLable.setText("Scheduler Login");
            loginUsername.setPromptText("Username");
            loginPassword.setPromptText("Password");
            loginButton.setText("Login");
        }

    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void Login(ActionEvent event) throws Exception {

        FileWriter fw = new FileWriter("audit.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        try {
            User user = userDao.findByUserName(loginUsername.getText());
            if (loginPassword.getText().equals(user.getPassword())) {
                pw.printf("User: %s, logged into the system at: %s\n", loginUsername.getText(), Instant.now());
                loginLable.setText("authentication success");
                MainApp.setCurrentUser(user);
                Parent mainViewParent = FXMLLoader.load(getClass().getResource("/edu/fxml/TabPane.fxml"));
                Scene mainViewScene = new Scene(mainViewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(mainViewScene);
               
                window.show();
            } else {
                pw.printf("Failed authentication attempt for username: %s, at: %s\n", loginUsername.getText(), Instant.now());
                loginLable.setText("Authentication failed");
            }
        } catch (NoResultException ex) {
            pw.printf("Failed authentication attempt for username: %s, at: %s\n", loginUsername.getText(), Instant.now());
            loginLable.setText("Authentication failed");
        }
        pw.close();
    }

}
