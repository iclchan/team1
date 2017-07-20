package test1.com.example.demo;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by T.DW on 20/7/17.
 */
public class Getdata {

    static String ORDER_URL = "https://cis2017-exchange.herokuapp.com/api/orders";
    static String TEAM_UID = "3dy93PQ9NP-TtABeab_C1w";
    static String MARKET_DATA_URL = "https://cis2017-exchange.herokuapp.com/api/market_data";


    //make order
    public static Integer orderService(String val) {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        Order order = new Order();
        order.setTeam_uid(TEAM_UID);
        order.setSymbol("3988ls");
        order.setSide(val);
        order.setPrice(1);
        order.setOrder_type("market");
        order.setQty(1000);
        Order returnOrder = rt.postForObject(ORDER_URL, order, Order.class);
        int filled_qty = returnOrder.getFilled_qty();


        List<Fill> fills = returnOrder.getFills();
        double avgFillprice = 0.0;
        int totalamount = 0;
        double totalprice = 0.0;

        for(Fill element: fills){
            totalprice=element.getPrice()*element.getQty();
            totalprice+=totalprice;

            avgFillprice+=element.getPrice();
            totalamount+=element.getQty();
        }
        avgFillprice = avgFillprice/totalamount;
        System.out.println("--Avg Price----" + avgFillprice);

        if (filled_qty==0){
            return 0;
        }

        System.out.println("------------------" + returnOrder.toString());

        return filled_qty;
    }


    //get individual instrument
    public static void getInstrumentService(String instrumentID) {
        RestTemplate restTemplate = new RestTemplate();
        Instrument m1 = restTemplate.getForObject(MARKET_DATA_URL + "/" + instrumentID, Instrument.class);

        System.out.println(m1);
    }

}
