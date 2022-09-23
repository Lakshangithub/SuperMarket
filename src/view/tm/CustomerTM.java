package view.tm;

public class CustomerTM {
    private String CustId;
    private String CustName;
    private String CustAddress;
    private String City;
    private String province;
    private String PostalCode;

    public CustomerTM() {
    }

    public CustomerTM(String custId, String custName, String custAddress, String city, String province, String postalCode) {
        this.CustId = custId;
        this.CustName = custName;
        this.CustAddress = custAddress;
        this.City = city;
        this.province = province;
        this.PostalCode = postalCode;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        this.CustId = custId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        this.CustName = custName;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        this.CustAddress = custAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        this.PostalCode = postalCode;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "CustId='" + CustId + '\'' +
                ", CustName='" + CustName + '\'' +
                ", CustAddress='" + CustAddress + '\'' +
                ", City='" + City + '\'' +
                ", province='" + province + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                '}';
    }
}
