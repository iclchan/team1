package test1.com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by T.DW on 18/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketData {

    private String symbol;
    private Long time;
    private Double bid;
    private Double ask;

    public MarketData(){}

    public MarketData(String symbol, Long time, Double bid, Double ask) {
        this.symbol = symbol;
        this.time = time;
        this.bid = bid;
        this.ask = ask;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "MarketData{" +
                "symbol='" + symbol + '\'' +
                ", time=" + time +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
