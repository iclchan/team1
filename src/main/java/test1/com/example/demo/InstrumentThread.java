package test1.com.example.demo;

import org.springframework.web.client.RestTemplate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by T.DW on 19/7/17.
 */
public class InstrumentThread extends Thread{

    private String symbol = "";
    private Integer highris;
    private Integer lowris;

    static String MARKET_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data";


    boolean counter = false;
    int price = 0;
    double first = 0.0;
    double current = 0.0;
    int window = 0;

    double totalprice=0.0;
    double limitmoney = 100000.0;

    //double totalmoney = 1000000.0;
    double currentmoney = 0.0;


    public InstrumentThread(String symbol, Integer highris, Integer lowris){
        this.symbol = symbol;
        this.highris = highris;
        this.lowris = lowris;
    }

    public void run(){

        Getdata datas = new Getdata();
        Queue<Double> q = new LinkedList<>();
        Queue<Double> q2 = new LinkedList<>();

        Queue<Double> sellq = new LinkedList<>();
        Queue<Double> sellq2 = new LinkedList<>();

        double plus = 0.0;
        double avgplus = 0.0;
        double minus = 0.0;
        double avgminus = 0.0;
        double firstRs = 0.0;
        double rsi = 0.0;

        double currentgain = 0.0;
        double currentloss = 0.0;

        double top = 0.0;
        double down = 0.0;

        double smoothRs = 0.0;
        double nextRsi = 0.0;

        RestTemplate restTemplate = new RestTemplate();



        while (true) {
            MarketData[]  m1 = restTemplate.getForObject(MARKET_DATA_URL, MarketData[].class);

            for (MarketData element: m1){

                if (element.getSymbol().equalsIgnoreCase(symbol)) {

                    System.out.println("===================================");
                    System.out.println(element.toString());


                    //buy
                    if(element.getAsk()!=0) {

                        //calculate RSI
                        if (window==0) {
                            first = element.getAsk();
                            window ++;
                        }
                        else {
                            if (window>14){
                                avgplus = plus/window;
                                avgminus = minus/window;
                            }

                            current = element.getAsk();
                            if(current>first){
                                plus = plus + (current-first);
                                currentgain = current-first;
                                currentloss = 0;
                            }
                            else{
                                minus = minus + (first-current);
                                currentgain = 0;
                                currentloss = first-current;
                            }

                            first = current;
                            window ++;

                            System.out.println("-----Window: "+ window);

                            if (window==14){
                                firstRs = plus/minus ;
                                rsi = 100- 100/(1+firstRs);
                                System.out.println("-----14th RSI: "+ rsi);
                            }
                            if (window>14){
                                top = avgplus*(window-1)+currentgain;
                                down = avgminus*(window-1)+currentloss;

                                smoothRs = top/down;
                                nextRsi = 100-(100/(1+smoothRs));

                                System.out.println("-----RSI: "+ nextRsi);
                            }
                        }

                        //moving average
                        if (q.size() == 7) {
                            q.remove();
                        }
                        q.add(element.getAsk());
                        System.out.println("Small Pool BUY Size: " + q.size());

                        if (q2.size() == 20) {
                            q2.remove();
                        }
                        q2.add(element.getAsk());
                        System.out.println("Large Pool BUY Size:" + q2.size());

                        if (q.size() == 7 && q2.size() == 20) {
                            Iterator<Double> iter = q.iterator();
                            Iterator<Double> iter2 = q2.iterator();
                            double totalq = 0;
                            double totalq2 = 0;
                            while (iter.hasNext()) {
                                totalq = totalq + iter.next();
                            }

                            while (iter2.hasNext()) {
                                totalq2 = totalq2 + iter2.next();
                            }

                            System.out.println("Small MA BUY: " + totalq / q.size());
                            System.out.println("Large MA BUY: " + totalq2 / q2.size());


                            if ((totalq / q.size()) > (totalq2 / q2.size())) {
                                if(nextRsi<lowris) {
                                    if(limitmoney>500) {
                                        //buy decision made
                                        Order result = datas.orderService("buy", symbol, "market", 500);
                                        if (result.getQty() != 0) {
                                            System.out.println("Bought Bought Bought Bought Bought");

                                            //calculate total price
                                            List<Fill> fills = result.getFills();

                                            for (Fill child : fills) {
                                                double thisfillprice;
                                                thisfillprice = child.getPrice() * child.getQty();
                                                totalprice += thisfillprice;
                                            }
                                            limitmoney -= totalprice;
                                            //totalmoney-= totalprice;

                                            System.out.println("You have bought $$$:" + totalprice);
                                            System.out.println("You left $$$:" + limitmoney);


                                            price = price + result.getQty();
                                            System.out.println("Successful---------");
                                            break;
                                        }
                                    }
                                    else{
                                        System.out.println("You have reached the limit.");
                                    }
                                }
                            }
                        }
                    }



                    //sell
                    if(element.getBid()!=0) {

                            //moving average
							if (sellq.size() == 7) {
								sellq.remove();
							}
							sellq.add(element.getBid());
							System.out.println("Small Pool SELL Size: " + sellq.size());

							if (sellq2.size() == 20) {
								sellq2.remove();
							}
							sellq2.add(element.getBid());
							System.out.println("Large Pool SELL Size: " + sellq2.size());

							if (sellq.size() == 7 && sellq2.size() == 20) {
								Iterator<Double> iter3 = sellq.iterator();
								Iterator<Double> iter4 = sellq2.iterator();
								double totalsellq = 0;
								double totalsellq2 = 0;
								while (iter3.hasNext()) {
									totalsellq = totalsellq + iter3.next();
								}

								while (iter4.hasNext()) {
									totalsellq2 = totalsellq2 + iter4.next();
								}

								System.out.println("Small MA SELL: " + totalsellq / sellq.size());
								System.out.println("Large MA SELL: " + totalsellq2 / sellq2.size());

								if ((totalsellq / sellq.size()) < (totalsellq2 / sellq2.size())) {
								    if(nextRsi>highris) {
                                        if(price!=0) {
                                            //sell decision made
                                            Order result = datas.orderService("buy", symbol, "market", 1000);
                                            if (result.getQty() != 0) {
                                                System.out.println("Sold Sold Sold Sold Sold");
                                                price = price-result.getQty();
                                                System.out.println("Successful---------");
                                            }
                                        }
                                    }
								}
							}
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }






}
