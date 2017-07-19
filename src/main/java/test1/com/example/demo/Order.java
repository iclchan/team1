package test1.com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by T.DW on 18/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String id;
    private String team_uid;
    private String symbol;
    private String side;
    private int qty;
    private String order_type;
    private double price;
    private String status;
    private int filled_qty;
    private List<Fill> fills;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam_uid() {
        return team_uid;
    }

    public void setTeam_uid(String team_uid) {
        this.team_uid = team_uid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFilled_qty() {
        return filled_qty;
    }

    public void setFilled_qty(int filled_qty) {
        this.filled_qty = filled_qty;
    }

    public List<Fill> getFills() {
        return fills;
    }

    public void setFills(List<Fill> fills) {
        this.fills = fills;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", team_uid=" + team_uid + ", symbol=" + symbol + ", side=" + side + ", qty=" + qty + ", order_type=" + order_type + ", price=" + price + ", status=" + status + ", filled_qty=" + filled_qty + ", fills=" + fills + '}';
    }
}
