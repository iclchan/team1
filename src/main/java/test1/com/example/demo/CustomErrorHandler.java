package test1.com.example.demo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomErrorHandler implements ResponseErrorHandler{
    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {

        try {

        } catch (Exception ex) {
            Logger.getLogger(CustomErrorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
