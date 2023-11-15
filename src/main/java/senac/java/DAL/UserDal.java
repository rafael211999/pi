package senac.java.DAL;

import senac.java.Services.ConexaoSQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDal {


    public Connection conectar() {
//        Aqui eu estou criando o meu espaço em memória para a minha conexao com o banco;
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Aqui eu estou criando a minha String de conexão com o meu banco de dados;
            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi";

            String usuario = "SENACRJEDU/116128412023.1";
            String senha = "senac@12841";

            //Aqui eu estou fazendo a minha conexao com o meu banco de dados
            conexao = DriverManager.getConnection(url, usuario, senha);

            //Aqui eu estou validando a minha conexao
            if (conexao != null) {

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

    public ResultSet listarUsuario() throws SQLException {
        String sql = "SELECT * FROM Users";
        ResultSet result = null;
        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();

            System.out.println("Listagem dos usuarios: ");

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lasteName = result.getString("lastName");
                String email = result.getString("email");
                String cpf = result.getString("cpf");

                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("lastName: " + lasteName);
                System.out.println("email: " + email);
                System.out.println("cpf: " + cpf);
                System.out.println(" ");

            }

            return result;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return result;
    }


    public int atualizarUsuario() throws SQLException {
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


    public int excluirUsuario() throws SQLException {
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
