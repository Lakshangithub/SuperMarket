package controller;

import bo.BOFactory;
import bo.custom.QueryBO;
import dto.OrderDetailDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MostMovableItemController {
    public AnchorPane movableItemAnchor;
    public TableView<OrderDetailTM> tblMovableItem;
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.QUERYBO);

    public void initialize(){
        tblMovableItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblMovableItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblMovableItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Description"));

        getAllMovableItem();
    }
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)movableItemAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Administrator.fxml"))));
        stage.centerOnScreen();
    }
    public void getAllMovableItem(){
        try {
            ArrayList<OrderDetailDTO>all = queryBO.getAllMovableItem();
            for (OrderDetailDTO dto : all){
                tblMovableItem.getItems().add(new OrderDetailTM(dto.getItemCode(),dto.getOrderQty(),dto.getDescription()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
