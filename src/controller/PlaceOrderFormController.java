package controller;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXComboBox;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceOrderFormController {
    public JFXComboBox<String> cmbCustomer;
    public TextField txtCustomerName;

    private final PurchaseOrderBO purchaseOrderBO  = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PURCHASE_ORDER);
    public JFXComboBox<String> cmbItem;
    public TextField txtQtyOnHand;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public Button btnAddToCart;
    public TableView<OrderDetailTM> tblOrderDetail;
    public Label lblOrderId;
    public Label lblDate;
    public String orderId;
    public AnchorPane purchaseOrderAnchor;
    public Label lblTotal;
    public Button btnPlaceOrder;

    public void initialize(){
        tblOrderDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblOrderDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblOrderDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderDetail.getColumns().get(5);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblOrderDetail.getItems().remove(param.getValue());
                tblOrderDetail.getSelectionModel().clearSelection();
                calculateTotal();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                try {
                    CustomerDTO search = purchaseOrderBO.searchCustomer(newValue);
                    txtCustomerName.setText(search.getCustName());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                txtCustomerName.clear();
            }
        });

        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    ItemDTO item = purchaseOrderBO.searchItem(newValue + "");
                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());
                    Optional<OrderDetailTM> optOrderDetail = tblOrderDetail.getItems().stream().filter(detail -> detail.getItemCode().equals(newValue)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });
        tblOrderDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItem.setDisable(true);
                cmbItem.setValue(selectedOrderDetail.getItemCode());
                btnAddToCart.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnAddToCart.setText("Add");
                cmbItem.setDisable(false);
                cmbItem.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });
        loadAllCustomerID();
        loadAllItemCode();

        orderId = generateNewOrderId();
        lblDate.setText(LocalDate.now().toString());
        lblOrderId.setText(orderId);
    }

    private void loadAllItemCode() {
        try {
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO item : all){
                cmbItem.getItems().add(item.getItemCode());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerID() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customer : all){
                cmbCustomer.getItems().add(customer.getCustId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)purchaseOrderAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Cashier.fxml"))));
        stage.centerOnScreen();
    }

    public void AddToCartOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItem.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderDetail.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetail.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();
            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setOrderQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderDetail.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setOrderQty(orderDetailTM.getOrderQty() + qty);
                total = new BigDecimal(orderDetailTM.getOrderQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderDetail.refresh();
        } else {
            tblOrderDetail.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItem.getSelectionModel().clearSelection();
        cmbItem.requestFocus();
        calculateTotal();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetailTM detail : tblOrderDetail.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText(String.valueOf(total));
    }
    public void PlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomer.getValue(),
                tblOrderDetail.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getItemCode(), tm.getOrderQty(), tm.getUnitPrice(),tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order not placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        cmbCustomer.getSelectionModel().clearSelection();
        cmbItem.getSelectionModel().clearSelection();
        tblOrderDetail.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    private boolean saveOrder(String orderId, LocalDate OrderDate, String CustId, List<OrderDetailDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId,OrderDate,CustId,orderDetails));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateNewOrderId(){
        try {
           return purchaseOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

}
