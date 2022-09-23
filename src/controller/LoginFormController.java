package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class LoginFormController {
    public Button btnLogin;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public AnchorPane loginFormAnchor;
    public Pane cashierPane;
    public Pane administratotPane;
    public TextField txtUsernameAt;
    public PasswordField txtPasswordAt;
    public Button btnLoginAt;
    public Button btnCashier;
    public Button btnAdministrator;

    public void playMouseEntered(MouseEvent event) {
        setButtonEntered(btnLogin);
        setButtonEntered(btnLoginAt);
    }

    public void playMouseExited(MouseEvent event) {
        setButtonExited(btnLogin,event);
        setButtonExited(btnLoginAt,event);
    }
    public void caMouseEntered(MouseEvent event) {
        setButtonEntered(btnCashier);
    }

    public void adMouseEntered(MouseEvent event) {
        setButtonEntered(btnAdministrator);
    }

    public void caMouseExited(MouseEvent event) {
        setButtonExited(btnCashier,event);
    }

    public void adMouseExited(MouseEvent event) {
        setButtonExited(btnAdministrator,event);
    }

    private void setButtonEntered(Button b) {
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(200),b);
        scaleT.setToX(1.2);
        scaleT.setToY(1.2);
        scaleT.play();

        DropShadow glow = new DropShadow();
        glow.setColor(Color.CORNFLOWERBLUE);
        glow.setWidth(20);
        glow.setHeight(20);
        glow.setRadius(20);
        b.setEffect(glow);
    }

    private void setButtonExited(Button b,MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), b);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            ScaleTransition scale = new ScaleTransition(Duration.millis(200), b);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
            b.setEffect(null);

        }
    }


    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String Username = txtUsername.getText();
        String Password = txtPassword.getText();

        if (Username.equals("lakshan") && Password.equals("123")){
            Image image = new Image("/view/assests/LoginImage/check.png");

            Notifications notifications = Notifications.create()
                    .title("Login Success!")
                    .text("You have successful login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();

            Stage stage = (Stage)loginFormAnchor.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Cashier.fxml"))));
            stage.centerOnScreen();

        }else {
            Image image = new Image("/view/assests/LoginImage/close.png");
            Notifications notifications = Notifications.create()
                    .title("Login Failed!")
                    .text("You have Failed login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();
        }
    }

    public void loginOnActionAt(ActionEvent actionEvent) throws IOException {
        String Username = txtUsernameAt.getText();
        String Password = txtPasswordAt.getText();

        if (Username.equals("lakshan") && Password.equals("1234")){
            Image image = new Image("/view/assests/LoginImage/check.png");

            Notifications notifications = Notifications.create()
                    .title("Login Success!")
                    .text("You have successful login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();

            Stage stage = (Stage)loginFormAnchor.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Administrator.fxml"))));
            stage.centerOnScreen();

        }else {
            Image image = new Image("/view/assests/LoginImage/close.png");
            Notifications notifications = Notifications.create()
                    .title("Login Failed!")
                    .text("You have Failed login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();
        }
    }

    public void cashierOnAction(ActionEvent actionEvent) {
        administratotPane.setVisible(false);
    }

    public void AdministratorOnAction(ActionEvent actionEvent) {
        administratotPane.setVisible(true);
    }


}
