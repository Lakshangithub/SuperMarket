package entity;

import java.math.BigDecimal;

public class Item {
    private String ItemCode;
    private String Description;
    private String PackSize;
    private BigDecimal UnitPrice;
    private int QtyOnHand;

    public Item(String itemCode, String description, String packSize, BigDecimal unitPrice, int qtyOnHand) {
        this.ItemCode = itemCode;
        this.Description = description;
        this.PackSize = packSize;
        this.UnitPrice = unitPrice;
        this.QtyOnHand = qtyOnHand;
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

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        this.PackSize = packSize;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.UnitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.QtyOnHand = qtyOnHand;
    }
}
