package test1.com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


@SpringBootApplication
public class DemoApplication {

	static String INSTRUMENT_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data/0005";
	static String MARKET_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data";
	static String ORDER_URL = "https://cis2017-exchange.herokuapp.com/api/orders";
	static String TEAM_UID = "3dy93PQ9NP-TtABeab_C1w";
	static Boolean counter = false;

	public static void main(String args[]) {
		SpringApplication.run(DemoApplication.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, MarketRepository marketRepository) throws Exception {
		return args -> {

//			orderService();
			//change the <,>

			Queue<Double> q = new LinkedList<Double>();
			Queue<Double> q2 = new LinkedList<Double>();

			Queue<Double> sellq = new LinkedList<Double>();
			Queue<Double> sellq2 = new LinkedList<Double>();

			while (true) {
				MarketData[]  m1 = restTemplate.getForObject(MARKET_DATA_URL, MarketData[].class);

				for (MarketData element: m1){
					System.out.println(element.toString());
					Market market = new Market(element.getSymbol(),element.getTime(),element.getBid(),element.getAsk());
					//marketRepository.save(market);

					if (element.getSymbol().equalsIgnoreCase("3988")) {





						//buy
						if(element.getAsk()!=0) {
							if (q.size() == 10) {
								q.remove();
							}
							q.add(element.getAsk());
							System.out.println("Small Pool buy: " + q.size());

							if (q2.size() == 50) {
								q2.remove();
							}
							q2.add(element.getAsk());
							System.out.println("Large Pool buy :" + q2.size());

							if (q.size() == 10 && q2.size() == 50) {
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
										System.out.println("Buyyyyyyyyy");
										//orderService("buy");
										counter = true;
										break;
									}
								}
							}
						}


							//sell
						if(element.getBid()!=0) {
							if (sellq.size() == 10) {
								sellq.remove();
							}
							sellq.add(element.getBid());
							System.out.println("Small Pool ask: " + sellq.size());

							if (sellq2.size() == 50) {
								sellq2.remove();
							}
							sellq2.add(element.getBid());
							System.out.println("Large Pool aks: " + sellq2.size());

							if (sellq.size() == 10 && sellq2.size() == 50) {
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

								System.out.println("Small average ask: " + totalsellq / sellq.size());
								System.out.println("Large average aks: " + totalsellq2 / sellq2.size());
								System.out.println(counter);

								if ((totalsellq / sellq.size()) < (totalsellq2 / sellq2.size())) {
									//sell
									if (counter == true) {
										//orderService("sell");
										System.out.println("Sellllllll");
										counter = false;
									}
								}
							}
						}
					}
				}

				try {
					Thread.sleep(1000);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}



		};
	}

	//get individual instrument
	public static void getInstrumentService(String instrumentID) {
		RestTemplate restTemplate = new RestTemplate();
		Instrument m1 = restTemplate.getForObject(MARKET_DATA_URL + "/" + instrumentID, Instrument.class);
		System.out.println(m1);
	}

	//make order
	public static void orderService(String val) {
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		Order order = new Order();
		order.setTeam_uid(TEAM_UID);
		order.setSymbol("3988");
		order.setSide(val);
		order.setPrice(1);
		order.setOrder_type("market");
		order.setQty(1000);
		Order returnOrder = rt.postForObject(ORDER_URL, order, Order.class);
		System.out.println("------------------" + returnOrder.toString());
	}



}
