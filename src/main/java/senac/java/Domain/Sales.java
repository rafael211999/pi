package senac.java.Domain;


import org.json.JSONObject;

import java.util.List;

public class Sales {
    int Id;
    public  String user = "";
    public  String products = "";
    public  double valor = 0;
    public  boolean finishedSale;
    public double discount = 0;
    public String sale;


    //Construtor

    public Sales() {
    }

    public Sales(String user, String products, double valor, boolean finishedSale, double discount, String sale) {
        this.user = user;
        this.products = products;
        this.valor = valor;
        this.finishedSale = finishedSale;
        this.discount = discount;
        this.sale = sale;

    }

    //Getters and Setters

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean getFinishedSale() {
        return finishedSale;
    }

    public void setFinishedSale(boolean finishedSale) {
        this.finishedSale = finishedSale;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }


    public JSONObject toJson(){
        JSONObject json = new JSONObject();

        json.put("user", user);
        json.put("produto", products);
        json.put("valor", valor);
        json.put("venda_final", finishedSale);
        json.put("desconto", discount);
        json.put("venda", sale);

        return json;
    }


    public static List<Sales> getAllSales(List<Sales> salesList) {
        return salesList;
    }
}
