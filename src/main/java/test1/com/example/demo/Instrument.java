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
    private double buyPrice;
    private double sellPrice;
    private double demandVolume;
    private double supplyVolume;

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

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getDemandVolume() {
        return demandVolume;
    }

    public void setDemandVolume(double demandVolume) {
        this.demandVolume = demandVolume;
    }

    public double getSupplyVolume() {
        return supplyVolume;
    }

    public void setSupplyVolume(double supplyVolume) {
        this.supplyVolume = supplyVolume;
    }

    @Override
    public String toString() {
        return "Instrument{" + "symbol=" + symbol + ", buy=" + buy + ", sell=" + sell + '}';
    }

    public void calculateVolumes(){
        buyPrice = 0;
        sellPrice = 999999;
        for(Double key : buy.keySet()){
            try{
                if(key > buyPrice){
                    buyPrice = key;
                }
                demandVolume += buy.get(key);
            }catch(Exception ex){
                System.out.println("Error");
            }
        }
        for(Double key : sell.keySet()){
            try{
                if(key < sellPrice){
                    sellPrice = key;
                }
                supplyVolume += sell.get(key);
            }catch(Exception ex){
                System.out.println("Error");
            }
        }
        if(sell.keySet().isEmpty()){
            sellPrice = 0;
        }
    }

}
