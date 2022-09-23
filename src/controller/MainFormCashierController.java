package controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class MainFormCashierController {

    public AnchorPane mainFormAnchor;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView btnBack;
    public ImageView imgCustomer;
    public ImageView imgOrder;
    public ImageView imgSearchOrder;

    public void initialize(){
        btnBack.setOnMouseClicked(event -> {
            try {
                Stage stage = (Stage)mainFormAnchor.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
                stage.centerOnScreen();
            }catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void backMouseEntered(MouseEvent event) {
        ImageView icon = (ImageView) event.getSource();
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
        scaleT.setToX(1.2);
        scaleT.setToY(1.2);
        scaleT.play();

        DropShadow glow = new DropShadow();
        glow.setColor(Color.CORNFLOWERBLUE);
        glow.setWidth(20);
        glow.setHeight(20);
        glow.setRadius(20);
        icon.setEffect(glow);
    }

    public void backMouseExited(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    public void imgMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch(icon.getId()){
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Order");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgSearchOrder":
                    lblMenu.setText("Search Order");
                    lblDescription.setText("Click here if you want to search order");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }

    }

    public void imgMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    public void imgMouseClicked(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()){
                case "imgCustomer" :
                    setUi("manage-customer-form");
                    break;
                case "imgOrder" :
                    setUi("place-order-form");
                    break;
                case "imgSearchOrder" :
                    setUi("search-order-form");
                    break;

            }
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)mainFormAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
