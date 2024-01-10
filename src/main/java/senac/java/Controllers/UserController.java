package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.DAL.UserDal;
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

    static String response = "";


    public static class UserHandler implements HttpHandler {


        @Override
        public void handle(HttpExchange exchange) throws IOException {


            if ("GET".equals(exchange.getRequestMethod())) {
                doGet(exchange);

            } else if ("POST".equals(exchange.getRequestMethod())) {
                doPost(exchange);

            } else if ("PUT".equals(exchange.getRequestMethod())) {
                doPut(exchange);

            } else if ("DELETE".equals(exchange.getRequestMethod())) {

                doDelete(exchange);

            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                res.enviarResponseJson(exchange, responseJason, 405);
            }
        }
//                --------------------------------------------------------------------------------

        public void doGet(HttpExchange exchange) throws IOException {
//            Aqui eu estou criando a instacia das minhas classes para pode acessar meu metódos
            UserDal userDal = new UserDal();
            Users user = new Users();
            List<Users> userArray = new ArrayList<>();
            JSONObject json;


            try {
//                Aqui eu estou indo dentro da minha DAL para poder chamar o método que busca os dados no meu banco;
//                Depois que chega a minha resposta, estou colocando a minha resposta dentro da minha variavel userArray;
                userArray = userDal.listarUsuario();

//                Aqui eu estou pegando os dados que foram passados para a minha variavel e convertendo em um json para poder enviar para o front;
                json = user.arrayToJson(userArray);

//               Aqui eu estou enviando os meus dados para  o frot
                res.enviarResponseJson(exchange, json, 200);
            } catch (Exception e) {
                System.out.println("O erro foi: " + e);
                response = "Ocorreu um erro ao buscar os dados";
                res.enviarResponse(exchange, response, 500);
            }
        }

//                --------------------------------------------------------------------------------


        public void doPost(HttpExchange exchange) throws IOException {

            UserDal userDal = new UserDal();
            int resp = 0;

            try (InputStream requestBody = exchange.getRequestBody()) {

                JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                Users user = new Users(
                        json.getString("name"),
                        json.getString("last_name"),
                        json.getString("email"),
                        json.getString("cpf")
                );
                usersList.add(user);
                resp = userDal.inserirUsuario(user.name, user.lastName, user.email, user.cpf);

                if (resp == 0) {
                    response = "Houve um problema ao criar o usuario";
                    res.enviarResponse(exchange, response, 200);
                } else if (resp > 0) {
                    response = "Usuário criado com sucesso";
                    res.enviarResponse(exchange, response, 200);
                }

            } catch (Exception e) {
                String resposta = e.toString();
                System.out.println("O erro foi " + resposta);
                res.enviarResponse(exchange, resposta, 200);


            }

        }


//                --------------------------------------------------------------------------------


        public void doPut(HttpExchange exchange) throws IOException {
            UserDal userDal = new UserDal();
            int myId = 0;
            String myName = "";
            String mySobrenome = "";
            String myEmail = "";
            String myCpf = "";

            int resp = 0;

            try(InputStream requestBody = exchange.getRequestBody()) {
                JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                myId = Integer.parseInt(json.getString("id"));
                myName = json.getString("name");
                mySobrenome =  json.getString("last_name");
                myEmail = json.getString("email");
                myCpf = json.getString("cpf");

                userDal.atualizarUsuario(myId, myName, mySobrenome, myEmail, myCpf);

                if (resp == 0) {
                    response = "Houve um problema ao atualizar os dados do usuario";
                    res.enviarResponse(exchange, response, 200);
                } else if (resp > 0) {
                    response = "Dados atualizados com sucesso";
                    res.enviarResponse(exchange, response, 200);
                }

                res.enviarResponse(exchange, response, 200);
            } catch (Exception e) {
                System.out.println("O erro foi: " + e);
                res.enviarResponse(exchange, "Erro ao atualizar usuário", 200);
            }


        }


//                --------------------------------------------------------------------------------


        public void doDelete(HttpExchange exchange) throws IOException {
            UserDal userDal = new UserDal();
            int myId = 0;
            int resp = 0;


            try (InputStream requestBody = exchange.getRequestBody()){

                JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                myId = Integer.parseInt(json.getString("id"));
                userDal.excluirUsuario(myId);

                if (resp == 0) {
                    response = "Houve um problema ao deletar o usuario";
                    res.enviarResponse(exchange, response, 200);
                } else if (resp > 0) {
                    response = "Usuário deletado com sucesso";
                    res.enviarResponse(exchange, response, 200);
                }

            } catch (Exception e) {
                System.out.println("O erro foi: " + e);
                res.enviarResponse(exchange, "Erro ao deletar usuário", 200);
            }

        }



    }
}