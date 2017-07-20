package test1.com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@SpringBootApplication
public class DemoApplication {

	static String INSTRUMENT_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data/0005";
	static String MARKET_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data";
	static String ORDER_URL = "https://cis2017-exchange.herokuapp.com/api/orders";
	static String TEAM_UID = "3dy93PQ9NP-TtABeab_C1w";
	static Boolean counter = false;
	double price = 0.0;

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

			InstrumentThread threadOne = new InstrumentThread("3988");
			threadOne.start();




//			while (true) {
//
//
//				getInstrumentService("0386");
//
//				try {
//					Thread.sleep(2000);
//				} catch (Exception ex) {
//					System.out.println(ex.getMessage());
//				}
//			}




		};
	}

	//get individual instrument
	public static void getInstrumentService(String instrumentID) {
		RestTemplate restTemplate = new RestTemplate();
		Instrument m1 = restTemplate.getForObject(MARKET_DATA_URL + "/" + instrumentID, Instrument.class);
		System.out.println(m1);
		Map<Double, Integer> buy = m1.getBuy();
		Map<Double, Integer> sell = m1.getSell();

		System.out.println("==================================");
		double buytotal = 0.0;
		double qty = 0;
		for(Double value: buy.keySet()){
			buytotal+=value;
		}

		for(Integer value: buy.values()){
			qty+=value;
		}

		System.out.println("buy average: "+buytotal/buy.size());
		System.out.println("buy quantity: "+qty);


		double selltotal = 0.0;
		double qty2 = 0;
		for(Double value: sell.keySet()){
			selltotal+=value;
		}

		for(Integer value: sell.values()){
			qty2+=value;
		}

		System.out.println("sell average: "+selltotal/sell.size());
		System.out.println("sell quantity: "+qty2);




	}

//	//make order
//	public static String orderService(String val) {
//		RestTemplate rt = new RestTemplate();
//		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		rt.getMessageConverters().add(new StringHttpMessageConverter());
//		Order order = new Order();
//		order.setTeam_uid(TEAM_UID);
//		order.setSymbol("3988");
//		order.setSide(val);
//		order.setPrice(1);
//		order.setOrder_type("market");
//		order.setQty(1000);
//		Order returnOrder = rt.postForObject(ORDER_URL, order, Order.class);
//		int filled_qty = returnOrder.getFilled_qty();
//		if (filled_qty==0){
//			return "not";
//		}
//
//		System.out.println("------------------" + returnOrder.toString());
//
//		return "yes";
//	}



}
