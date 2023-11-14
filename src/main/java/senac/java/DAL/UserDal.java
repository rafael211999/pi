package senac.java.DAL;

import senac.java.Services.ConexaoSQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDal {
    //    Inserir - CREATE
    public void inserirUsuario(String name, String lastName, String email, String cpf) throws SQLException {
//        Aqui eu estou criando a minha query para inserir os valores no banco
        String sql = "INSERT INTO Users(name, lastName, email, cpf) VALUES (?, ?, ?, ?)";

        ConexaoSQLServer conexao = new ConexaoSQLServer();


//      O PreparedStatement faz a troca dos meus numeros pela informações
        try (PreparedStatement statement = conexao.conectar().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, cpf);

            int linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");
        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi: " + e);
        }

    }

    public void listarUsuario(Connection conexao) throws SQLException {
        String sql = "SELECT * FROM Users";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

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

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
    }


    public void atualizarUsuario(Connection conexao, int id, String name, String lastName, String email, String cpf) throws SQLException {
        String sql = "UPDATE Users SET name = ?, lastName =  ?, email = ?, cpf = ? WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, cpf);
            statement.setInt(5, id);

            int linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
    }


    public void excluirUsuario(Connection conexao, int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);

            int linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");

        } catch (SQLException e) {
            System.out.println("O erro na exclusão de dados foi: " + e);
        }


    }
}
