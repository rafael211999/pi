package senac.java.DAL;

import com.sun.net.httpserver.HttpExchange;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import senac.java.Domain.Users;
import senac.java.Services.ConexaoSQLServer;
import senac.java.Services.ResponseEndPoints;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class UserDal {

    public static List<Users> usersList = new ArrayList<>();
    static ResponseEndPoints res = new ResponseEndPoints();


    public Connection conectar() {
//        Aqui eu estou criando o meu espaço em memória para a minha conexao com o banco;
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Aqui eu estou criando a minha String de conexão com o meu banco de dados;

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";
            String usuario = "user";
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
//        finally {
//            try {
//                System.out.println("Entrei no try do finally");
//                if (conexao != null && !conexao.isClosed()) {
//                    conexao.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Entrei no catch do finally");
//                System.out.println("O erro no finally foi: " + e);
//            }
//
//        }
        return conexao;
    }


    //    Inserir - CREATE
    public int inserirUsuario(String name, String lastName, String email, String cpf) throws SQLException {
//        Aqui eu estou criando a minha query para inserir os valores no banco
        String sql = "INSERT INTO Users(name, lastName, email, cpf) VALUES (?, ?, ?, ?)";

        int linhasAfetadas = 0;
        Connection conexao = conectar();

//      O PreparedStatement faz a troca dos meus numeros pela informações
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, cpf);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");

            conexao.close();
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi: " + e);
            conexao.close();
        }

        conexao.close();
        return linhasAfetadas;
    }

    public List listarUsuario() throws SQLException, IOException {
        String sql = "SELECT * FROM Users";
        ResultSet result = null;

        List<Users> userArray = new ArrayList<>();

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
//            Está variavel está armazenando o que vem do meu banco de dados;
            result = statement.executeQuery();

            System.out.println("Listagem dos usuarios: ");

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                String email = result.getString("email");
                String cpf = result.getString("cpf");

//                Aqui eu estou adicionando os dados que estão no meu banco de dados na minha variavel currentUser e depois estou adicionando os meus dados no meu userArray;
                Users currentUser = new Users(id, name, lastName, email, cpf);
                userArray.add(currentUser);


                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("lastName: " + lastName);
                System.out.println("email: " + email);
                System.out.println("cpf: " + cpf);
                System.out.println(" ");

            }
//          Aqui eu estou encerrando a minha conexão para não ficar travado essa conexão;
            result.close();
            return userArray;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return userArray;
    }









    public int atualizarUsuario(int id, String name, String lastName, String email, String cpf) throws SQLException {
        String sql = "UPDATE Users SET name = ?, lastName =  ?, email = ?, cpf = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
//            statement.setString(1, name);
//            statement.setString(2, lastName);
//            statement.setString(3, email);
//            statement.setString(4, cpf);
//            statement.setInt(5, id);

            linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return linhasAfetadas;
    }


    public int excluirUsuario(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
//            statement.setInt(1, id);

            linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");

            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na exclusão de dados foi: " + e);
        }

        return linhasAfetadas;
    }
}
