package bo.custom.impl;

import bo.custom.QueryBO;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import dto.CustomDTO;
import dto.CustomerDTO;
import dto.IncomeDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.QUERYDAO);

    @Override
    public CustomDTO searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        return queryDAO.searchOrder(id);
    }

    @Override
    public ArrayList<OrderDetailDTO> getAllMovableItem() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = queryDAO.ShowMovableItem();
        ArrayList<OrderDetailDTO> movableItem = new ArrayList<>();
        for (OrderDetails o : all){
            movableItem.add(new OrderDetailDTO(o.getItemCode(),o.getOrderQty(),o.getDescription()));
        }
        return movableItem;
    }

    @Override
    public ArrayList<OrderDetailDTO> getAllLeastMovableItem() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = queryDAO.ShowLeastMovableItem();
        ArrayList<OrderDetailDTO> movableItem = new ArrayList<>();
        for (OrderDetails o : all){
            movableItem.add(new OrderDetailDTO(o.getItemCode(),o.getOrderQty(),o.getDescription()));
        }
        return movableItem;
    }

    @Override
    public IncomeDTO getDailyIncome(String date) throws SQLException, ClassNotFoundException {
        return queryDAO.dailyIncomeCheck(date);
    }

    @Override
    public ArrayList<IncomeDTO> getMonthlyIncome() throws SQLException, ClassNotFoundException {
       return queryDAO.monthlyIncomeCheck();
    }

    @Override
    public ArrayList<IncomeDTO> getAnnualIncome() throws SQLException, ClassNotFoundException {
        return queryDAO.annualIncomeCheck();
    }

}
