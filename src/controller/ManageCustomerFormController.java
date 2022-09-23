package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;
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
import view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerFormController {

    public AnchorPane customerAnchor;
    public TextField txtCustId;
    public TextField txtCustName;
    public TextField txtCustAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TableView<CustomerTM> tblCustomer;
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.CUSTOMER);
    public Button btnSave;
    public Button btnDelete;
    public Button btnAddNewCustomer;

    public void initialize(){
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CustId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CustAddress"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("City"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("PostalCode"));

        clearData();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue!=null ?"Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue!=null){
                txtCustId.setText(newValue.getCustId());
                txtCustName.setText(newValue.getCustName());
                txtCustAddress.setText(newValue.getCustAddress());
                txtCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode());

                txtCustId.setDisable(false);
                txtCustName.setDisable(false);
                txtCustAddress.setDisable(false);
                txtCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);
            }
        });

        loadAllCustomer();
    }
    public void navigateToHome(MouseEvent event) throws IOException {
        Stage stage = (Stage)customerAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Cashier.fxml"))));
        stage.centerOnScreen();
    }

    public void loadAllCustomer(){
        tblCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> all = customerBO.getAllCustomers();
            for (CustomerDTO customer : all){
                tblCustomer.getItems().add(new CustomerTM(customer.getCustId(),customer.getCustName(),customer.getCustAddress(),customer.getCity(),customer.getProvince(), customer.getPostalCode()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void customerSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtCustId.getText();
        String name = txtCustName.getText();
        String address = txtCustAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustName.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Save")){
            if (exitCustomer(id)){
                new Alert(Alert.AlertType.ERROR,id+"already exists").show();
            }

            boolean save = customerBO.saveCustomer(new CustomerDTO(id,name,address,city,province,postalCode));
            tblCustomer.getItems().add(new CustomerTM(id,name,address,city,province,postalCode));
            clearData();
            if (save){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Failed").show();
            }

        }else {
            if (!exitCustomer(id)){
                new Alert(Alert.AlertType.ERROR,"No customer can be found with this id "+id).show();
            }
            customerBO.updateCustomer(new CustomerDTO(id,name,address,city,province,postalCode));

            CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setCustId(id);
            selectedCustomer.setCustName(name);
            selectedCustomer.setCustAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);

            tblCustomer.refresh();
        }


    }

    boolean exitCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }
    public void customerDeleteOnAction(ActionEvent actionEvent) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustId();
        try {
            customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            clearData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String newID(){
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C00-001";
    }

    public void AddNewCustomerOnAction(ActionEvent actionEvent) {
        txtCustId.setDisable(false);
        txtCustName.setDisable(false);
        txtCustAddress.setDisable(false);
        txtCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);
        txtCustId.clear();
        txtCustId.setText(newID());
        txtCustName.clear();
        txtCustAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        btnSave.setDisable(false);
        txtCustName.requestFocus();
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();

    }

    private void clearData() {
        txtCustId.clear();
        txtCustName.clear();
        txtCustAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCustId.setEditable(false);
        txtCustName.setDisable(true);
        txtCustAddress.setDisable(true);
        txtCity.setDisable(true);
        txtProvince.setDisable(true);
        txtPostalCode.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

}
