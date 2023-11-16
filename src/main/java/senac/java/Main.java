package senac.java;
import senac.java.DAL.UserDal;
import senac.java.Services.ConexaoSQLServer;
import senac.java.Services.Servidor;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

//        UserDal conexao = new UserDal();
//        conexao.conectar();

        ConexaoSQLServer conexao = new ConexaoSQLServer();
        conexao.conectar();


//        Servidor api = new Servidor();
//        api.apiServer();
    }
}