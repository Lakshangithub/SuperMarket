package view.tm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomTM {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;
    private String ItemCode;
    private int OrderQty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public CustomTM() {
    }

    public CustomTM(String orderId, LocalDate orderDate, String custId, String itemCode, int orderQty, BigDecimal unitPrice, BigDecimal total) {
        this.OrderId = orderId;
        this.OrderDate = orderDate;
        this.CustId = custId;
        this.ItemCode = itemCode;
        this.OrderQty = orderQty;
        this.unitPrice = unitPrice;
        this.total = total;
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

    @Override
    public String toString() {
        return "CustomDTO{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderDate=" + OrderDate +
                ", CustId='" + CustId + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", OrderQty=" + OrderQty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
