package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;
import dto.IncomeDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryBO extends SuperBO {
    CustomDTO searchOrderByOrderID(String id)throws SQLException,ClassNotFoundException;
    ArrayList<OrderDetailDTO> getAllMovableItem()throws SQLException,ClassNotFoundException;
    ArrayList<OrderDetailDTO> getAllLeastMovableItem()throws SQLException,ClassNotFoundException;
    IncomeDTO getDailyIncome(String date) throws SQLException,ClassNotFoundException;
    ArrayList<IncomeDTO> getMonthlyIncome() throws SQLException,ClassNotFoundException;
    ArrayList<IncomeDTO> getAnnualIncome() throws SQLException,ClassNotFoundException;
}
