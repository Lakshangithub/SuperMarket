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

public class MainFormAdministratorController {
    public Label lblMenu;
    public Label lblDescription;
    public ImageView btnBack;
    public ImageView imgItem;
    public AnchorPane administratorAnchor;
    public ImageView imgMovableItem;
    public ImageView imgLeastItem;
    public ImageView imgIncome;

    public void initialize(){
        btnBack.setOnMouseClicked(event -> {
            try {
                Stage stage = (Stage) administratorAnchor.getScene().getWindow();
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

    public void imgMouseClicked(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()){
                case "imgItem" :
                    setUi("manage-item-form");
                    break;
                case "imgIncome" :
                    setUi("income-report");
                    break;
                case "imgMovableItem" :
                    setUi("most-movable-item");
                    break;
                case "imgLeastItem" :
                    setUi("least-movable-item");
                    break;
            }
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)administratorAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void imgMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch(icon.getId()){
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgIncome":
                    lblMenu.setText("Income Reports");
                    lblDescription.setText("Click to view Daily,Monthly,Annual Income reports");
                    break;
                case "imgMovableItem":
                    lblMenu.setText("Most Movable Items");
                    lblDescription.setText("Click to view Movable Items");
                    break;
                case "imgLeastItem":
                    lblMenu.setText("Least Movable Items");
                    lblDescription.setText("Click to view Least Items");
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
}
