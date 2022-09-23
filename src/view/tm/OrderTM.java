package view.tm;

import java.time.LocalDate;

public class OrderTM {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;

    public OrderTM() {
    }

    public OrderTM(String orderId, LocalDate orderDate, String custId) {
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

    @Override
    public String toString() {
        return "OrderTM{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderDate=" + OrderDate +
                ", CustId='" + CustId + '\'' +
                '}';
    }
}
