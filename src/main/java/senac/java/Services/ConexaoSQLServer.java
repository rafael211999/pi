package senac.java.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoSQLServer {

    public Connection conectar() {
//        Aqui eu estou criando o meu espaço em memória para a minha conexao com o banco;
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Aqui eu estou criando a minha String de conexão com o meu banco de dados;
            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";
//            integratedSecurity=true

            String usuario = "user";
//            SENACRJEDU/116128412023.1
            String senha = "123456";

            //Aqui eu estou fazendo a minha conexao com o meu banco de dados
            conexao = DriverManager.getConnection(url, usuario, senha);

            //Aqui eu estou validando a minha conexao
            if (conexao != null) {
                System.out.println("Conexão com o banco feita com sucesso");
                return conexao;

            }

//            Aqui eu estou verificando se tem algum erro na minha classe ou se teve erro na minha conexao
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("O erro foi: " + e);
        }
//        Aqui eu estou verificando se teve algum erro de SQL e caso a conexao esteja aberta, ele vai fechar.
        finally {
            try {
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.out.println("O erro no finally foi: " + e);
            }

        }
        return conexao;
    }


}
