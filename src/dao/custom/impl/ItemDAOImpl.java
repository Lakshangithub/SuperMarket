package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)",entity.getItemCode(), entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Item WHERE ItemCode = ?",id);
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET Description =?,PackSize =?,UnitPrice =?,QtyOnHand =? WHERE ItemCode =?",entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getItemCode());
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item WHERE ItemCode =?",id);
        while (rst.next()){
            return new Item(rst.getString(1),rst.getString(2), rst.getString(3),rst.getBigDecimal(4),rst.getInt(5));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode =?",id).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newCustomerId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newCustomerId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> all = new ArrayList<>();
        while (rst.next()){
            all.add(new Item(rst.getString(1), rst.getString(2),rst.getString(3),rst.getBigDecimal(4),rst.getInt(5)));
        }
        return all;
    }
}
