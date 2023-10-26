package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Users;
import senac.java.Services.ResponseEndPoints;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


public class UserController {

    static ResponseEndPoints res = new ResponseEndPoints();

    public static List<Users> usersList = new ArrayList<>();

    static JSONObject responseJason = new JSONObject();


    public static class UserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if ("GET".equals(exchange.getRequestMethod())) {
                List<Users> getAllFromArray = Users.getAllUsers(usersList);

                Users user = new Users();


//                if (!getAllFromArray.isEmpty()) {
//                    for (Users user : getAllFromArray) {
//                        System.out.println("Name: " + user.getName());
//                        System.out.println("Last Name: " + user.getLastName());
//                        System.out.println("Email: " + user.getEmail());
//                        System.out.println("Cpf: " + user.getCpf());
//                        System.out.println(" ");
//                        System.out.println("-------------------------------------------");
//                        System.out.println(" ");
//
//
//                    }
                res.enviarResponseJson(exchange, user.arrayToJson(getAllFromArray), 200);
//                } else {
//                    System.out.println("Valor não encontrado");
//                }

                res.enviarResponseJson(exchange, responseJason, 200);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {

                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                    Users user = new Users(
                            json.getString("name"),
                            json.getString("last_name"),
                            json.getString("email"),
                            json.getString("cpf")
                    );
                    usersList.add(user);

                    res.enviarResponseJson(exchange, user.toJson(), 201);

                } catch (Exception e) {
                    String resposta = e.toString();
                    System.out.println("O erro foi " + resposta);
                    res.enviarResponse(exchange, resposta, 200);

                    System.out.println("Cheguei até o catch do meu post no backend " );

                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                res.enviarResponseJson(exchange, responseJason, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                res.enviarResponseJson(exchange, responseJason, 200);
            }else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                res.enviarResponseJson(exchange, responseJason, 405);
            }
        }
    }
}