
package edu.controllers;

import edu.MainApp;
import edu.dao.UserDAO;
import edu.model.User;
import java.net.URL;
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
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

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

    public void Login(ActionEvent event) throws Exception {

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/edu/fxml/TabPane.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      

       User user = userDao.findByUserName(loginUsername.getText());
        if (loginPassword.getText().equals(user.getPassword())) {

            loginLable.setText("authentication success");
            MainApp.setCurrentUser(user);
            window.setScene(mainViewScene);
            window.show();
        } else {
            System.out.println("username entered:" + loginUsername.getText());
            System.out.println("should have been username: " + user.getUserName());
            System.out.println("Password: " + loginPassword.getText());
            System.out.println("shoudl have been password: " + user.getPassword());
            loginLable.setText("Authentication failed");
        }

      

    }

}
