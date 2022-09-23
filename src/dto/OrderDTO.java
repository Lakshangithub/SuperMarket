package dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, LocalDate OrderDate, String CustId, List<OrderDetailDTO> orderDetails) {
        this.setOrderId(orderId);
        this.setOrderDate(OrderDate);
        this.setCustId(CustId);
        this.setOrderDetails(orderDetails);
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

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderDate=" + OrderDate +
                ", CustId='" + CustId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}

