package test1.com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by T.DW on 18/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instrument {

    private String symbol;
    private Map<String, Integer> buy;
    private Map<String, Integer> sell;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Map<String, Integer> getBuy() {
        return buy;
    }

    public void setBuy(Map<String, Integer> buy) {
        this.buy = buy;
    }

    public Map<String, Integer> getSell() {
        return sell;
    }

    public void setSell(Map<String, Integer> sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Instrument{" + "symbol=" + symbol + ", buy=" + buy + ", sell=" + sell + '}';
    }

}
