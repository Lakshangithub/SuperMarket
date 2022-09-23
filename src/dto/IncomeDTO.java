package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class IncomeDTO {
    private String OrderId;
    private LocalDate OrderDate;
    private BigDecimal total;

    private String year;
    private String month;


    public IncomeDTO() {
    }

    public IncomeDTO(BigDecimal total) {
        this.setTotal(total);
    }
    public IncomeDTO(String year, String month, BigDecimal total){
        this.year = year;
        this.month = month;
        this.total = total;
    }
    public IncomeDTO(String year, BigDecimal total){
        this.year = year;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "IncomeDTO{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderDate=" + OrderDate +
                ", total=" + total +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
