package test1.com.example.demo;

/**
 * Created by T.DW on 18/7/17.
 */
public class Fill {
    private double price;
    private int qty;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Fill{" + "price=" + price + ", qty=" + qty + '}';
    }
}
