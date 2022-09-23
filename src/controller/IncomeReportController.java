package controller;

import bo.BOFactory;
import bo.custom.QueryBO;
import com.jfoenix.controls.JFXTextField;
import dto.IncomeDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.IncomeTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeReportController {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.QUERYBO);
    public JFXTextField txtDailyIncome;
    public AnchorPane incomeAnchor;
    public DatePicker cmbDate;
    public TableView<IncomeTM> tblMonthlyIncome;
    public TableView<IncomeTM> tblAnnualIncome;

    public void initialize(){
        tblMonthlyIncome.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblMonthlyIncome.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("month"));
        tblMonthlyIncome.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblAnnualIncome.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblAnnualIncome.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("total"));

        monthlyIncomeCheck();
        annualIncomeCheck();
    }
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) incomeAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Main-Form-Administrator.fxml"))));
        stage.centerOnScreen();
    }

    public void checkOnAction(ActionEvent actionEvent) {
        try {
            String date = String.valueOf(cmbDate.getValue());
            IncomeDTO dto = queryBO.getDailyIncome(date);
            txtDailyIncome.setText(String.valueOf(dto.getTotal()));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void monthlyIncomeCheck() {
        try {
            ArrayList<IncomeDTO> dto = queryBO.getMonthlyIncome();
            for (IncomeDTO income : dto){
                tblMonthlyIncome.getItems().add(new IncomeTM(income.getYear(),income.getMonth(),income.getTotal()));
            }
            tblMonthlyIncome.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void annualIncomeCheck(){
        try {
            ArrayList<IncomeDTO> dto = queryBO.getAnnualIncome();
            for (IncomeDTO income : dto){
                tblAnnualIncome.getItems().add(new IncomeTM(income.getYear(),income.getTotal()));
            }
            tblAnnualIncome.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
