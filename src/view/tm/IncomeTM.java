package view.tm;

import java.math.BigDecimal;

public class IncomeTM {

    private String year;
    private String month;
    private BigDecimal total;

    public IncomeTM() {
    }

    public IncomeTM(String year, String month, BigDecimal total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }
    public IncomeTM(String year,BigDecimal total) {
        this.year = year;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "IncomeTM{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", total=" + total +
                '}';
    }
}
