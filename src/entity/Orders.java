package entity;

import java.time.LocalDate;

public class Orders {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;

    public Orders() {
    }

    public Orders(String orderId, LocalDate orderDate, String custId) {
        this.OrderId = orderId;
        this.OrderDate = orderDate;
        this.CustId = custId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.OrderDate = orderDate;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        this.CustId = custId;
    }
}
