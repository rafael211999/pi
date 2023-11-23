package senac.java.DAL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalesDal {
    public Connection conectar() {
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";
            String usuario = "user";
            String senha = "123456";

            conexao = DriverManager.getConnection(url, usuario, senha);

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
//                if (conexao != null && !conexao.isClosed()) {
//
//                    conexao.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("O erro no finally foi: " + e);
//            }
//
//        }
        return conexao;
    }

    public int inserirSales(String usuario, String products,float valor, boolean finishedSale, float discount, String sale) throws SQLException {
//        Aqui eu estou criando a minha query para inserir os valores no banco
        String sql = "INSERT INTO Sales(usuario, products, valor, finishedSale, discount, sale) VALUES (?, ?, ?, ?, ?)";

        int linhasAfetadas = 0;
        Connection conexao = conectar();

//      O PreparedStatement faz a troca dos meus numeros pela informações
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, usuario);
            statement.setString(2, products);
            statement.setFloat(3, valor);
            statement.setBoolean(4, finishedSale);
            statement.setFloat(5, discount);
            statement.setString(6, sale);

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

    public ResultSet listarSales() throws SQLException {
        String sql = "SELECT * FROM Sales";
        ResultSet result = null;
        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();

            System.out.println("Listagem de vendas: ");

            while (result.next()) {
                int id = result.getInt("id");
                String usuario = result.getString("usuario");
                String products = result.getString("products");
                String valor = result.getString("valor");
                String finishedSale = result.getString("finishedSale");
                String discount = result.getString("discount");
                String sale = result.getString("sale");

                System.out.println("id: " + id);
                System.out.println("usuario: " + usuario);
                System.out.println("products: " + products);
                System.out.println("valor: " + valor);
                System.out.println("finishedSale: " + finishedSale);
                System.out.println("discount: " + discount);
                System.out.println("sale: " + sale);
                System.out.println(" ");

            }

            return result;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return result;
    }

    public int atualizarSales(int id, String usuario, String products, float valor, boolean finishedSale,  float discount, String sale ) throws SQLException {
        String sql = "UPDATE Sales SET usuario = ?, products =  ?, valor = ?, finishedSale = ?, discount = ?, sale = ?   WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
//            statement.setString(1, usuario);
//            statement.setString(2, products);
//            statement.setString(3, valor);
//            statement.setString(4, finishedSale);
//            statement.setString(5, discount);
//            statement.setString(6, sale);
//            statement.setInt(7, id);

            linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram afetadas " + linhasAfetadas + " no banco de dados");
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return linhasAfetadas;
    }

    public int excluirSales(int id) throws SQLException {
        String sql = "DELETE FROM Sales WHERE id = ?";
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
