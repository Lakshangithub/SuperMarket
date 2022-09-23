package entity;

import java.math.BigDecimal;

public class OrderDetails {
    private String OrderId;
    private String ItemCode;
    private String Description;
    private int OrderQty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public OrderDetails(String orderId, String itemCode, int orderQty, BigDecimal unitPrice, BigDecimal total) {
        this.OrderId = orderId;
        this.ItemCode = itemCode;
        this.OrderQty = orderQty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public OrderDetails(String ItemCode, int orderQty, String Description) {
        this.ItemCode = ItemCode;
        this.OrderQty = orderQty;
        this.Description = Description;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        this.ItemCode = itemCode;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        this.OrderQty = orderQty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
