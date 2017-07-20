package test1.com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by T.DW on 18/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instrument {

    private String symbol;
    private Map<Double, Integer> buy;
    private Map<Double, Integer> sell;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Map<Double, Integer> getBuy() {
        return buy;
    }

    public void setBuy(Map<Double, Integer> buy) {
        this.buy = buy;
    }

    public Map<Double, Integer> getSell() {
        return sell;
    }

    public void setSell(Map<Double, Integer> sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Instrument{" + "symbol=" + symbol + ", buy=" + buy + ", sell=" + sell + '}';
    }

}
