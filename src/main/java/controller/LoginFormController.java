package controller;

import DAO.custom.RegistrationDAO;
import DAO.custom.impl.RegistrationDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import RegExPatterns.RegExPatterns;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane rootNode;

    RegistrationDAO registrationDAO = new RegistrationDAOImpl();


    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(new ActionEvent());
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String psw=txtPassword.getText();

        try {
            boolean isValid = registrationDAO.isValidUser(userName,psw);
            if (isValid){

                registrationDAO.getUserInfo(userName);
                rootNode.getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                Parent root = loader.load();
                DashboardFormController dashboardFormController  = loader.getController();

                registrationDAO userDto = registrationDAO.getUserInfo(userName);
                dashboardFormController.setUser(userDto);
                stage.setScene(new Scene(root));
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.show();
            }else {
                new Alert(Alert.AlertType.ERROR,"UserName or Password Did Not Matched.\n Try again!!").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
