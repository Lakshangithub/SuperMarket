package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageItemFormController {
    public Button btnAddNewItem;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public AnchorPane itemAnchor;
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ITEM);
    public Button btnSave;
    public Button btnDelete;
    public TableView<ItemTM> tblItem;

    public void initialize(){
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        clearData();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue!=null){
                txtItemCode.setText(newValue.getItemCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand()+ "");

                txtItemCode.setDisable(false);
                txtDescription.setDisable(false);
                txtPackSize.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });

        loadAllItem();
    }
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)itemAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Administrator.fxml"))));
        stage.centerOnScreen();
    }

    public void loadAllItem(){
        tblItem.getItems().clear();
        try {
            ArrayList<ItemDTO> all = itemBO.getAllItems();
            for (ItemDTO item : all){
                tblItem.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void itemSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save")){
            if (exitItem(itemCode)){
                new Alert(Alert.AlertType.ERROR,itemCode+"already exists").show();
            }
            boolean save = itemBO.saveItem(new ItemDTO(itemCode,description,packSize,unitPrice,qtyOnHand));
            tblItem.getItems().add(new ItemTM(itemCode,description,packSize,unitPrice,qtyOnHand));
            clearData();
            if (save){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Failed").show();
            }
        }else {
            if (!exitItem(itemCode)){
                new Alert(Alert.AlertType.ERROR,"No item can be found with this id "+itemCode).show();
            }
            itemBO.updateItem(new ItemDTO(itemCode,description,packSize,unitPrice,qtyOnHand));

            ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
            selectedItem.setItemCode(itemCode);
            selectedItem.setDescription(description);
            selectedItem.setPackSize(packSize);
            selectedItem.setUnitPrice(unitPrice);
            selectedItem.setQtyOnHand(qtyOnHand);

            tblItem.refresh();
        }
    }

    boolean  exitItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(code);
    }
    public void itemDeleteOnAction(ActionEvent actionEvent) {
        String id = tblItem.getSelectionModel().getSelectedItem().getItemCode();
        try {
            itemBO.deleteItem(id);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            clearData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String newCode(){
        try {
            return itemBO.generateItemCode();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }

    public void AddNewItemOnAction(ActionEvent actionEvent) {
        txtItemCode.setDisable(false);
        txtDescription.setDisable(false);
        txtPackSize.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtItemCode.clear();
        txtItemCode.setText(newCode());
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        txtDescription.requestFocus();
        tblItem.getSelectionModel().clearSelection();
    }

    public void clearData(){
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtItemCode.setEditable(false);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

}
