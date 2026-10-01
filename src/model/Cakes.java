package model;

public class Cakes {

    private String flavor;
    private String category;
    private String size;
    private int qty;
    private double price;

    // Constructor
    public Cakes(String flavor, String category, String size, int qty, double price) {
        this.flavor = flavor;
        this.category = category;
        this.size = size;
        this.qty = qty;
        this.price = price;
    }

    // Getter and Setter
    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
 
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
