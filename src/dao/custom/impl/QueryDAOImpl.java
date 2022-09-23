package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.QueryDAO;
import dto.CustomDTO;
import dto.IncomeDTO;
import dto.OrderDetailDTO;
import entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class QueryDAOImpl implements QueryDAO {
    @Override
    public CustomDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("select o.orderId,o.orderDate,o.custId,OD.itemCode,OD.orderQty,OD.unitPrice,OD.total from orders o inner join orderDetails OD on o.orderId = OD.orderID where o.orderId =?",id);
        while (rst.next()){
            return new CustomDTO(rst.getString(1), LocalDate.parse(rst.getString(2)),rst.getString(3),rst.getString(4),rst.getInt(5),rst.getBigDecimal(6),rst.getBigDecimal(7));
        }
        return null;
    }

    @Override
    public ArrayList<OrderDetails> ShowMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT Item.ItemCode,SUM(OrderQty),Item.Description FROM OrderDetails inner join Item on Item.ItemCode = OrderDetails.ItemCode WHERE OrderQty BETWEEN 20 AND 100 GROUP BY ItemCode ORDER BY SUM(OrderQty) DESC");
        ArrayList<OrderDetails> all = new ArrayList<>();
        while (rst.next()) {
            all.add(new OrderDetails(rst.getString(1),rst.getInt(2),rst.getString(3)));
        }
        return  all;
    }

    @Override
    public ArrayList<OrderDetails> ShowLeastMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT Item.ItemCode,SUM(OrderQty),Item.Description FROM OrderDetails inner join Item on Item.ItemCode = OrderDetails.ItemCode WHERE OrderQty BETWEEN 1 AND 10 GROUP BY ItemCode ORDER BY SUM(OrderQty) ASC");
        ArrayList<OrderDetails> all = new ArrayList<>();
        while (rst.next()) {
            all.add(new OrderDetails(rst.getString(1),rst.getInt(2),rst.getString(3)));
        }
        return  all;
    }

    @Override
    public IncomeDTO dailyIncomeCheck(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT SUM(total) FROM `OrderDetails`INNER JOIN `Orders` on `Orders`.OrderId = `OrderDetails`.OrderId WHERE OrderDate = ?",date);
        while (resultSet.next()){
            return new IncomeDTO(resultSet.getBigDecimal(1));
        }
        return  null;
    }

    @Override
    public ArrayList<IncomeDTO> monthlyIncomeCheck() throws SQLException, ClassNotFoundException {
        ResultSet set = SQLUtil.executeQuery("SELECT YEAR(o.OrderDate) AS Year,MONTH(o.OrderDate) AS Month,SUM(Od.total) AS Total FROM Orders o inner join OrderDetails Od on o.OrderId = Od.OrderId GROUP BY YEAR(o.OrderDate), MONTH(o.OrderDate) ");
        ArrayList<IncomeDTO> all = new ArrayList<>();
        while (set.next()){
            all.add(new IncomeDTO(set.getString(1),set.getString(2),set.getBigDecimal(3)));
        }
        return all;
    }

    @Override
    public ArrayList<IncomeDTO> annualIncomeCheck() throws SQLException, ClassNotFoundException {
        ResultSet set = SQLUtil.executeQuery("SELECT YEAR(o.OrderDate)AS Year,SUM(Od.total)AS Total FROM Orders o inner join OrderDetails Od on o.OrderId = Od.OrderId GROUP BY YEAR(o.OrderDate)");
        ArrayList<IncomeDTO> all = new ArrayList<>();
        while (set.next()){
            all.add(new IncomeDTO(set.getString(1),set.getBigDecimal(2)));
        }
        return all;
    }


}
