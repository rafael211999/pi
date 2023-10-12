package senac.java.Services;

import com.sun.net.httpserver.HttpServer;
import senac.java.Controllers.ProductController;
import senac.java.Controllers.SalesController;
import senac.java.Controllers.UserController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Servidor {

    public  void apiServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/users", new UserController.UserHandler());
        server.createContext("/api/products", new ProductController.ProductsHandler());
        server.createContext("/api/sales", new SalesController.SalesPersonHandler());

        server.setExecutor(null);
        server.start();

    }

}
