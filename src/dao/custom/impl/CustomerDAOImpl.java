package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
       return SQLUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?)",entity.getCustId(),entity.getCustName(),entity.getCustAddress(),entity.getCity(),entity.getProvince(),entity.getPostalCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Customer WHERE CustId = ?",id);
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Customer SET CustName = ?,CustAddress = ?,City = ?,Province = ?,PostalCode = ? WHERE CustId = ?",entity.getCustName(),entity.getCustAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode(),entity.getCustId());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst =  SQLUtil.executeQuery("SELECT * FROM Customer WHERE CustId = ?",id);
        while (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT CustId FROM Customer WHERE CustId = ?",id).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT CustId FROM Customer ORDER BY CustId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CustId");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> all = new ArrayList<>();
        while (rst.next()){
            all.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)));
        }
        return all;
    }
}
