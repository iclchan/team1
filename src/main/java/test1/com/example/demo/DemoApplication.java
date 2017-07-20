package test1.com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class DemoApplication {


	public static void main(String args[]) {
		SpringApplication.run(DemoApplication.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {

			//Dewang's Threads
			/*InstrumentThread threadOne3988 = new InstrumentThread("3988",55,40);
			threadOne3988.start();

			InstrumentThread threadOne0005 = new InstrumentThread("0005",55,40);
			threadOne0005.start();

			InstrumentThread threadOne0386 = new InstrumentThread("0386",55,40);
			threadOne0386.start();

			InstrumentThread threadOne0388 = new InstrumentThread("0388",55,40);
			threadOne0388.start();

			InstrumentThread threadOne0001 = new InstrumentThread("0001",55,40);
			threadOne0001.start();*/

			//Ernest Threads
			TradingMan tradingManOne = new TradingMan("0001");
			tradingManOne.start();
			TradingMan tradingManTwo = new TradingMan("0005");
			tradingManTwo.start();
			TradingMan tradingManThree = new TradingMan("0386");
			tradingManThree.start();
			TradingMan tradingManFour = new TradingMan("0388");
			tradingManFour.start();
			TradingMan tradingManFive = new TradingMan("3988");
			tradingManFive.start();



		};
	}
}
