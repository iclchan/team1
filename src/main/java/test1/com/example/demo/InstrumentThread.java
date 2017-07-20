package test1.com.example.demo;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by T.DW on 19/7/17.
 */
public class InstrumentThread extends Thread{

    String symbol = "";
    static String MARKET_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data";
    static Boolean counter = false;
    static double price = 0.0;


    static double first = 0.0;
    static double current = 0.0;
    static int window = 0;


    public InstrumentThread(String symbol){
        this.symbol = symbol;
    }

    public void run(){
//			orderService();
        //change the <,>
        Getdata datas = new Getdata();
        Queue<Double> q = new LinkedList<Double>();
        Queue<Double> q2 = new LinkedList<Double>();

        Queue<Double> sellq = new LinkedList<Double>();
        Queue<Double> sellq2 = new LinkedList<Double>();

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
                System.out.println(element.toString());
                Market market = new Market(element.getSymbol(),element.getTime(),element.getBid(),element.getAsk());
                //marketRepository.save(market);

                //System.out.println("1111111111111111 ");

                if (element.getSymbol().equalsIgnoreCase(symbol)) {


                    //System.out.println("22222222222222 ");



                    //buy
                    if(element.getAsk()!=0) {


//                        if (window==0) {
//                            first = element.getAsk();
//                            window ++;
//                        }
//                        else {
//
//                            if (window>14){
//                                avgplus = plus/window;
//                                avgminus = minus/window;
//                            }
//
//
//
//
//                            current = element.getAsk();
//                            if(current>first){
//                                plus = plus + (current-first);
//                                currentgain = current-first;
//                                currentloss = 0;
//                            }
//                            else{
//                                minus = minus + (first-current);
//                                currentgain = 0;
//                                currentloss = first-current;
//                            }
//
//                            first = current;
//                            window ++;
//
//                            System.out.println("-----Window: "+ window);
//
//                            if (window==14){
//                                firstRs = plus/minus ;
//                                rsi = 100- 100/(1+firstRs);
//
//                                System.out.println("-----RSI: "+ rsi);
//                                if(rsi < 20){
//                                    //buy
//                                }
//
//                            }
//                            if (window>14){
//
//
//                                top = avgplus*(window-1)+currentgain;
//                                down = avgminus*(window-1)+currentloss;
//
//                                smoothRs = top/down;
//                                nextRsi = 100-100/(1+smoothRs);
//
//                                System.out.println("-----RSI: "+ nextRsi);
//                                if(nextRsi<40){
//                                    //buy
//
//                                    if (counter == false) {
//                                        String result = orderService("buy");
//                                        if(result.equalsIgnoreCase("yes")){
//                                            System.out.println("Buyyyyyyyyy");
//                                            counter=true;
//                                            price = element.getAsk();
//                                            System.out.println("Successful---------");
//                                            break;
//                                        }
//                                    }
//
//                                }
//
//
//                            }
//                        }







                        if (q.size() == 7) {
                            q.remove();
                        }
                        q.add(element.getAsk());
                        System.out.println("Small Pool buy: " + q.size());

                        if (q2.size() == 20) {
                            q2.remove();
                        }
                        q2.add(element.getAsk());
                        System.out.println("Large Pool buy :" + q2.size());

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

                            System.out.println("Small average buy: " + totalq / q.size());
                            System.out.println("Large average buy: " + totalq2 / q2.size());
                            System.out.println(counter);

                            if ((totalq / q.size()) > (totalq2 / q2.size())) {
                                //buy
                                if (counter == false) {
                                    double result = datas.orderService("buy");
                                    if(result!=0.0){
                                        System.out.println("Buyyyyyyyyy");
                                        counter=true;
                                        //price = element.getAsk();
                                        price = result;
                                        System.out.println("Successful---------");
                                        break;
                                    }
                                }
                            }
                        }



                    }

//                    if (counter == true && element.getBid()>price) {
                    if (counter == true && element.getBid()>price) {
                        double result = datas.orderService("sell");
                        if(result!=0.0){
                            System.out.println("Sellllllll");
                            counter=false;
                            System.out.println("Successful---------");
                        }
                    }


                    //sell
                    if(element.getBid()!=0) {
//							if (sellq.size() == 7) {
//								sellq.remove();
//							}
//							sellq.add(element.getBid());
//							System.out.println("Small Pool ask: " + sellq.size());
//
//							if (sellq2.size() == 20) {
//								sellq2.remove();
//							}
//							sellq2.add(element.getBid());
//							System.out.println("Large Pool aks: " + sellq2.size());
//
//							if (sellq.size() == 7 && sellq2.size() == 20) {
//								Iterator<Double> iter3 = sellq.iterator();
//								Iterator<Double> iter4 = sellq2.iterator();
//								double totalsellq = 0;
//								double totalsellq2 = 0;
//								while (iter3.hasNext()) {
//									totalsellq = totalsellq + iter3.next();
//								}
//
//								while (iter4.hasNext()) {
//									totalsellq2 = totalsellq2 + iter4.next();
//								}
//
//								System.out.println("Small average ask: " + totalsellq / sellq.size());
//								System.out.println("Large average aks: " + totalsellq2 / sellq2.size());
//								System.out.println(counter);
//
//								if ((totalsellq / sellq.size()) < (totalsellq2 / sellq2.size())) {
//									//sell
//									if (counter == true && element.getBid()>price) {
//										String result = orderService("sell");
//										if(result.equalsIgnoreCase("yes")){
//											System.out.println("Sellllllll");
//											counter=false;
//											System.out.println("Successful---------");
//										}
//									}
//								}
//							}
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
