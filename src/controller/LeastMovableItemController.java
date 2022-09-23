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

public class LeastMovableItemController {
    public AnchorPane leastMovableItemAnchor;
    public TableView<OrderDetailTM> tblILeastItem;
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.QUERYBO);

    public void initialize(){
        tblILeastItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblILeastItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblILeastItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));

        getAllLeastMovableItem();
    }
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)leastMovableItemAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Administrator.fxml"))));
        stage.centerOnScreen();
    }

    public void getAllLeastMovableItem(){
        try {
            ArrayList<OrderDetailDTO>all = queryBO.getAllLeastMovableItem();
            for (OrderDetailDTO dto : all){
                tblILeastItem.getItems().add(new OrderDetailTM(dto.getItemCode(),dto.getOrderQty(),dto.getDescription()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
