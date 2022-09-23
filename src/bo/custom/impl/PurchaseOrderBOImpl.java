package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.SQLUtil;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DBConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetails;
import entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDERDETAILS);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        boolean save = orderDAO.save(new Orders(dto.getOrderId(),dto.getOrderDate(), dto.getCustId()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getOrderDetails()) {
            boolean save1 = orderDetailsDAO.save(new OrderDetails(detail.getOrderId(), detail.getItemCode(),detail.getOrderQty(), detail.getUnitPrice(),detail.getTotal()));
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            ItemDTO item = searchItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getOrderQty());

            boolean update = itemDAO.update(new Item(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
       Customer customer  = customerDAO.search(id);
       return new CustomerDTO(customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean checkOrderId(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.exist(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        for (Customer customer : all){
            allCustomer.add(new CustomerDTO(customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomer;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItem = new ArrayList<>();
        for (Item item : all){
            allItem.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
        }
        return allItem;
    }
}
