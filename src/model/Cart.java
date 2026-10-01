package model;

public class Cart {

    private String CustomerName;
    private String FlavororCategory;
    private String Qty;
    private String Price;
    
    // Constructer
    public Cart(String CustomerName, String FlavororCategory, String Qty, String Price) {
        this.CustomerName = CustomerName;
        this.FlavororCategory = FlavororCategory;
        this.Qty = Qty;
        this.Price = Price;
    }
    
    // Getter % Setter
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getFlavororCategory() {
        return FlavororCategory;
    }

    public void setFlavororCategory(String FlavororCategory) {
        this.FlavororCategory = FlavororCategory;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    
}
