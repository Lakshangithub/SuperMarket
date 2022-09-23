package view.tm;

import java.math.BigDecimal;

public class OrderDetailTM {
    private String ItemCode;
    private String Description;
    private int OrderQty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public OrderDetailTM(String itemCode, String description, int orderQty, BigDecimal unitPrice, BigDecimal total) {
        this.ItemCode = itemCode;
        this.Description = description;
        this.OrderQty = orderQty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public OrderDetailTM(String ItemCode, int orderQty, String description) {
        this.ItemCode = ItemCode;
        this.OrderQty = orderQty;
        this.Description = description;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        this.ItemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
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
        return "OrderDetailTM{" +
                "ItemCode='" + ItemCode + '\'' +
                ", Description='" + Description + '\'' +
                ", OrderQty=" + OrderQty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
