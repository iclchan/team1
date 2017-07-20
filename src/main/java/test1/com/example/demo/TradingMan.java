package test1.com.example.demo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class TradingMan  extends Thread {

    static String TEAM_UID = "ArOCc3HrHUJuyZVUo1JDfw";
    static String ORDER_URL = "https://cis2017-exchange.herokuapp.com/api/orders";

    List<Instrument> instrumentInfo = new ArrayList<Instrument>();
    double supplyDemandBuyAverage = 0.0;
    int buyCount = 0;
    double supplyDemandSellAverage = 0.0;
    int sellCount = 0;
    DecimalFormat f = new DecimalFormat("##.00");
    double averageBuy = 0.0;
    double averageSell = 0.0;
    int stocksAtHand = 0;

    double boughtPrice = 0.0;
    int boughtCount = 0;
    double stocksWorth = 0;
    boolean boughtThisTick = false;

    double maxPrice = 0.0;
    double minPrice = 0.0;
    double thirtyFifthPercentile = 0.0;
    double profitPercent = 0.0;

    String symbol = "";

    List<Order> orderList = new ArrayList<Order>();

    public TradingMan(String symbol) {
        this.symbol = symbol;
    }

    public void run() {
        while (true) {
            try {
                boughtThisTick = false;
                Instrument instrument = getInstrumentService(symbol);
                instrument.calculateVolumes();
                calculateProfitPercent(instrument);
                instrumentInfo.add(instrument);
                calculateThirtyFifthPercentile(instrument);
                processBuy(instrument);
                processSell(instrument);
                if (buyCount > 30) {
                    if (!boughtThisTick) {
                        boolean didIBuy = decideBuy(instrument);
                        if (!didIBuy) {
                            decideSell(instrument);
                        } else {
                            boughtThisTick = true;
                        }
                    } else {
                        decideSell(instrument);
                    }
                }
                cutLossAndSell(instrument);
                Thread.sleep(3000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void calculateProfitPercent(Instrument instrument) {
        if (instrument.getSellPrice() < 30) {
            profitPercent = 1.04;
        } else if (instrument.getSellPrice() < 100) {
            profitPercent = 1.02;
        } else {
            profitPercent = 1.01;
        }
    }

    public void calculateRSI(Instrument instrument) {
        /*double currAsk = instrument.getSellPrice();
         double currentGain = 0.0;
         double currentLoss = 0.0;
         if(currAsk > first){

         currentGain = currAsk - first;
         currentLoss = 0;
         }else{
         currentLoss = currAsk - first;
         currentGain = 0.0;
         }

         first = currAsk;

         int top = averagePlus * (buyCount) + currentGain;
         int down = averageMinus * (buyCount) + currentLoss;

         int smoothRs = top/down;
         int nextRsi = 100-100/(1+smoothRs);*/
    }

    public void calculateThirtyFifthPercentile(Instrument instrument) {
        double currPrice = instrument.getSellPrice();
        if (currPrice > maxPrice) {
            maxPrice = currPrice;
        }
        if (currPrice != 0 && (currPrice < minPrice || minPrice == 0.0)) {
            minPrice = currPrice;
        }
        double diff = maxPrice - minPrice;
        thirtyFifthPercentile = minPrice + (diff / 100 * 30);
    }

    public boolean decideBuy(Instrument instrument) {
        if(instrument.getSellPrice() != 0) {
            if (checkSupplyDemandBuy(instrument) && stocksWorth < 200000) {
                int quantityStocks = 20000 / (int) instrument.getSellPrice();
                Order returnedOrder = orderService(instrument.getSymbol(), "buy", quantityStocks, "market", 1);
                if (returnedOrder.getId() != null) {
                    System.out.println(returnedOrder);
                }
                if (returnedOrder.getFilled_qty() != 0) {
                    stocksAtHand += returnedOrder.getFilled_qty();
                    boughtCount++;
                    double currPrice = returnedOrder.getFills().get(0).getPrice();
                    stocksWorth = stocksWorth + (returnedOrder.getFilled_qty() * currPrice);
                    if (currPrice > boughtPrice) {
                        boughtPrice = currPrice;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void processBuy(Instrument instrument) {
        double currAvg = 1.0 * instrument.getSupplyVolume() / instrument.getDemandVolume();
        buyCount++;
        supplyDemandBuyAverage = ((supplyDemandBuyAverage * (buyCount - 1)) + currAvg) / buyCount;
        //System.out.println("Instrument Buy Price : " + instrument.getBuyPrice());
        //System.out.println("Instrument Buy Count : " + buyCount);
        averageBuy = ((averageBuy * (buyCount - 1)) + instrument.getSellPrice()) / buyCount;
        //System.out.println("Instrument Average Price : " + averageBuy);
    }

    public boolean checkSupplyDemandBuy(Instrument instrument) {
        double currBuyAvg = 1.0 * instrument.getSupplyVolume() / instrument.getDemandVolume();
        //System.out.println("Buy Price : " + averageBuy + "," + (averageBuy * 0.95));
        if (instrument.getSellPrice() < thirtyFifthPercentile && instrument.getSellPrice() > (boughtPrice * 0.9)) {
            return true;
        }
        if (currBuyAvg > (supplyDemandBuyAverage * 1.5) || instrument.getSellPrice() < (averageBuy * 0.96) || checkUpwardTrend()) {
            return true;
        }
        return false;
    }

    public boolean checkUpwardTrend() {
        Instrument currInstru = instrumentInfo.get(instrumentInfo.size() - 1);
        if (instrumentInfo.size() >= 3) {
            Instrument previousInstru = instrumentInfo.get(instrumentInfo.size() - 1);
            Instrument thirdLastInstru = instrumentInfo.get(instrumentInfo.size() - 3);
            if (thirdLastInstru.getSellPrice() > previousInstru.getSellPrice()) {
                return false;
            }
        }
        return true;
    }

    public void processSell(Instrument instrument) {
        if (instrument.getSellPrice() < (averageSell * 1.4) || averageSell == 0) {
            double currAvg = 1.0 * instrument.getDemandVolume() / instrument.getSupplyVolume();
            sellCount++;
            supplyDemandSellAverage = ((supplyDemandSellAverage * (sellCount - 1)) + currAvg) / sellCount;
            averageSell = ((averageSell * (sellCount - 1)) + instrument.getBuyPrice()) / sellCount;
        }
    }

    public boolean checkSupplyDemandSell(Instrument instrument) {
        double currSellAvg = 1.0 * instrument.getDemandVolume() / instrument.getSupplyVolume();
        //System.out.println("Sell Price : " + averageSell + "," + (averageSell * 1.03));
        if (instrument.getBuyPrice() > (averageSell * profitPercent)) {
            if (instrument.getSellPrice() > (boughtPrice * 1.02)) {
                return true;
            }
        }
        return false;
    }

    public void cutLossAndSell(Instrument instrument) {
        if (instrument.getSellPrice() < (boughtPrice - (boughtPrice * profitPercent))) {
            Order returnOrder = orderService(instrument.getSymbol(), "sell", stocksAtHand, "market", instrument.getSellPrice());
            if(returnOrder.getId() != null){
                System.out.println(returnOrder);
            }
            stocksAtHand -= returnOrder.getFilled_qty();
            if (stocksAtHand == 0) {
                boughtPrice = 0;
                boughtCount = 0;
                maxPrice = 0.0;
            }

        }
    }

    public boolean isSellPriceTrendGood(Instrument instrument) {
        return false;
    }

    public boolean decideSell(Instrument instrument) {
        Iterator<Order> iterator = orderList.iterator();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomErrorHandler());
        while(iterator.hasNext()) {
            Order tempOrder = iterator.next();
            try {
                Order returnOrder = restTemplate.getForObject("https://cis2017-exchange.herokuapp.com/api/market_data/" + tempOrder.getId(), Order.class);
                if (returnOrder == null) {
                    iterator.remove();
                } else if (returnOrder.getStatus().equals("FILLED")) {
                    stocksWorth = stocksWorth * ((1.0 * stocksAtHand - returnOrder.getFilled_qty()) / stocksAtHand);
                    stocksAtHand -= returnOrder.getFilled_qty();
                    iterator.remove();
                }
            }catch(Exception ex){
                stocksWorth = stocksWorth * ((1.0 * stocksAtHand - tempOrder.getQty()) / stocksAtHand);
                stocksAtHand -= tempOrder.getQty();
                iterator.remove();
            }
        }
        System.out.println("Instrument : " + instrument.getSymbol() + "Quantity Left : " + stocksAtHand);
        if(stocksAtHand == 0){
            boughtPrice = 0;
            boughtCount = 0;
        }
        if (instrument.getBuyPrice() != 0 && checkSupplyDemandSell(instrument) && stocksAtHand > 0) {
            System.out.println("Selling");
            if (stocksAtHand > 100) {
                Order returnOrder = orderService(instrument.getSymbol(), "sell", stocksAtHand / 2, "limit", instrument.getSellPrice());
                if(returnOrder.getId() != null){
                    System.out.println(returnOrder);
                    if(!returnOrder.getStatus().equals("FILLED")) {
                        orderList.add(returnOrder);
                    }
                }
                //stocksAtHand = stocksAtHand / 2;
                //stocksWorth = stocksWorth / 2;
            } else {
                Order returnOrder = orderService(instrument.getSymbol(), "sell", stocksAtHand, "limit", instrument.getSellPrice());
                if(returnOrder.getId() != null){
                    System.out.println(returnOrder);
                    if(!returnOrder.getStatus().equals("FILLED")) {
                        orderList.add(returnOrder);
                    }
                }
                /*stocksAtHand = 0;
                stocksWorth = 0.0;
                if (stocksAtHand == 0) {
                    boughtPrice = 0;
                    boughtCount = 0;
                }*/
            }
            System.out.println("Quantity Left : " + stocksAtHand);
            return true;
        }
        return false;
    }

    public static Instrument getInstrumentService(String instrumentID) {
        RestTemplate restTemplate = new RestTemplate();
        Instrument m1 = restTemplate.getForObject("https://cis2017-exchange.herokuapp.com/api/market_data/" + instrumentID, Instrument.class);
        return m1;
    }

    public static Order orderService(String symbol, String buySell, int quantity, String marketLimit, double sellPrice) {
        RestTemplate rt = new RestTemplate();
        rt.setErrorHandler(new CustomErrorHandler());
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        Order order = new Order();
        order.setTeam_uid(TEAM_UID);
        order.setSymbol(symbol);
        order.setSide(buySell);
        order.setPrice(sellPrice);
        order.setOrder_type(marketLimit);
        order.setQty(quantity);
        Order returnOrder = rt.postForObject(ORDER_URL, order, Order.class);
        System.out.println(returnOrder.toString());
        return returnOrder;
    }
}
