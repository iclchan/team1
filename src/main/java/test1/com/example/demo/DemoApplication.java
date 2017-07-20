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


			InstrumentThread threadOne3988 = new InstrumentThread("3988",55,50);
			threadOne3988.start();

//			InstrumentThread threadOne0005 = new InstrumentThread("0005",55,50);
//			threadOne0005.start();
//
//			InstrumentThread threadOne0386 = new InstrumentThread("0386",55,50);
//			threadOne0386.start();
//
//			InstrumentThread threadOne0388 = new InstrumentThread("0388",55,50);
//			threadOne0388.start();
//
//			InstrumentThread threadOne0001 = new InstrumentThread("0001",55,50);
//			threadOne0001.start();




		};
	}
}
