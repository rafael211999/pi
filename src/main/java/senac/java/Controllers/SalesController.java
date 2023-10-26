package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Sales;

import senac.java.Domain.Users;
import senac.java.Services.ResponseEndPoints;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SalesController {
    static ResponseEndPoints res = new ResponseEndPoints();
    static JSONObject responseJason = new JSONObject();

    private static List<Sales> salesList = new ArrayList<>();


    public static class SalesPersonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {


            if ("GET".equals(exchange.getRequestMethod())) {

                List<Sales> getAllFromArray = Sales.getAllSales(salesList);


                if (!getAllFromArray.isEmpty()){
                    for (Sales sale : getAllFromArray){
                        System.out.println("Usuário: " + sale.getUser());
                        System.out.println("Produto: " + sale.getProducts());
                        System.out.println("Valor: " + sale.getValor());
                        System.out.println("Venda final: " + sale.getFinishedSale());
                        System.out.println("Desconto: " + sale.getDiscount());
                        System.out.println("Venda: " + sale.getSale());
                        System.out.println(" ");
                        System.out.println("-------------------------------------------" );
                        System.out.println(" ");
                    }
                    String response = "Dados encontrados com sucesso";
                    res.enviarResponse(exchange, response, 200);
                }else {
                    String response = "Dados não encontrados";
                    res.enviarResponse(exchange, response, 400);
                }

                res.enviarResponseJson(exchange, responseJason, 200);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {

                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                    Sales sale = new Sales(
                            json.getString("user"),
                            json.getString("produto"),
                            json.getDouble("valor"),
                            json.getBoolean("venda_final"),
                            json.getDouble("desconto"),
                            json.getString("venda")
                    );
                    salesList.add(sale);

                    res.enviarResponseJson(exchange, sale.toJson(), 201);

                } catch (Exception e) {
                    String resposta = e.toString();
                    System.out.println("O erro foi " + resposta);

                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {

                res.enviarResponseJson(exchange, responseJason, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {

                res.enviarResponseJson(exchange, responseJason, 200);
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                res.enviarResponseJson(exchange, responseJason, 401);
            }
        }
    }
}