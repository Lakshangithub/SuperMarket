package controller;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import bo.custom.QueryBO;
import dto.CustomDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CustomTM;

import java.io.IOException;
import java.sql.SQLException;

public class SearchOrderFormController {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.QUERYBO);
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PURCHASE_ORDER);
    public TableView<CustomTM> tblSearchOrder;
    public TextField txtOrderId;
    public AnchorPane searchOrderAnchor;

    public void initialize() {
        tblSearchOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        tblSearchOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        tblSearchOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CustId"));
        tblSearchOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblSearchOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblSearchOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblSearchOrder.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("total"));


    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) searchOrderAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Cashier.fxml"))));
        stage.centerOnScreen();
    }

    public void btnSearchOrder(ActionEvent actionEvent) {
        String id = txtOrderId.getText();
        try {
            boolean b = purchaseOrderBO.checkOrderId(id);
            if (b){
                CustomDTO customer = queryBO.searchOrderByOrderID(id);
                tblSearchOrder.getItems().add(new CustomTM(customer.getOrderId(), customer.getOrderDate(), customer.getCustId(), customer.getItemCode(), customer.getOrderQty(), customer.getUnitPrice(),customer.getTotal()));
                txtOrderId.clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "This orderId can not be found").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnRemove(ActionEvent actionEvent) {
        tblSearchOrder.getItems().remove(tblSearchOrder.getSelectionModel().getSelectedItem());
    }
}
