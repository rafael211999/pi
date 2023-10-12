package senac.java.Domain;

import org.json.JSONObject;

public class Products {

    int id;
    public static String name = "";
    public static String factory = "";
    public static int quantity = 0;


    //Constructor

    public  Products(){}


    public Products(String name, String factory, int quantity){
        this.name = name;
        this.factory = factory;
        this.quantity = quantity;

    }

    //Getters and Setters

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public static String getFactory() {
        return factory;
    }

    public void setFactory(String factory){
        this.factory = factory;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }



    public JSONObject toJson(){
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("fabrica", factory);
        json.put("quantidade", quantity);

        return json;
    }
}
