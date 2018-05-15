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
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.NoResultException;

/**
 *
 * @author jdharri
 */
public class LoginController implements Initializable {

    @FXML
    private Text loginLable;
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button loginButton;
    Locale currentLocale;
    private final UserDAO userDao = new UserDAO();
    private String authFailed = "";

    /**
     * This method is where the labels are set based on locale
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Locale.setDefault(Locale.FRANCE);
        currentLocale = Locale.getDefault();
        //if locale is france
        if (currentLocale.equals(Locale.FRANCE)) {
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
            loginLable.setText("Connexion du programmateur");
            loginUsername.setPromptText("Nom d'utilisateur");
            loginPassword.setPromptText("Mot de passe");
            loginButton.setText("S'identifier");
            authFailed = "L'authentification a échoué : vérifiez votre nom d'utilisateur et votre mot de passe. ";
        }
        //if locale is us
        if (currentLocale.equals(Locale.US)) {
            loginLable.setText("Scheduler Login");
            loginUsername.setPromptText("Username");
            loginPassword.setPromptText("Password");
            loginButton.setText("Login");
            authFailed = "Authentication failed: check your user name and password.";
        }
        loginPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    loginButton.fire();

                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "problem calling login method with enter key press", ex);
                }
            }
        });

    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void Login(Event event) throws Exception {

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
                loginLable.setText(authFailed);
            }
        } catch (NoResultException ex) {
            pw.printf("Failed authentication attempt for username: %s, at: %s\n", loginUsername.getText(), Instant.now());
            loginLable.setText(authFailed);
        }
        pw.close();
    }

}
