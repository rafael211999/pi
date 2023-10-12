package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Products;
import senac.java.Domain.Sales;
import senac.java.Services.ResponseEndPoints;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    static ResponseEndPoints res = new ResponseEndPoints();
    static JSONObject responseJson = new JSONObject();


    private static List<Products> productsList = new ArrayList<>();


    public static class ProductsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {




            if ("GET".equals(exchange.getRequestMethod())) {
//
                res.enviarResponseJson(exchange, responseJson, 200);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {

                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                    Products products = new Products(
                            json.getString("name"),
                            json.getString("fabrica"),
                            json.getInt("quantidade")
                    );
                    productsList.add(products);

                    res.enviarResponseJson(exchange, products.toJson(), 201);
                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {
//
                res.enviarResponseJson(exchange, responseJson, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
//
                res.enviarResponseJson(exchange, responseJson, 200);
            } else {
//
                res.enviarResponseJson(exchange, responseJson, 401);
            }


        }

    }
}
