package senac.java.Domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Users {

    int Id;
    public String name = "";
    public String lastName = "";
    public String email = "";
    public String cpf = "";


    //Constructor

    public Users() {

    }


    public Users(String name, String lastName, String email, String cpf) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;

    }


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();


        json.put("name", name);
        json.put("last_name", lastName);
        json.put("email", email);
        json.put("cpf", cpf);


        return json;
    }

    public JSONObject arrayToJson(List<Users> usersList) {
        JSONObject json = new JSONObject();


        if (!usersList.isEmpty()) {
            int contJson = 0;
            for (Users user : usersList) {

                JSONObject userJson = new JSONObject();
                userJson.put("name", user.getName());
                userJson.put("last_name", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("cpf", user.getCpf());

                json.put(String.valueOf(contJson) ,userJson);
                contJson++;
            }
            return json;
        } else {
            return null;
        }
    }

    public static List<Users> getAllUsers(List<Users> usersList) {
        return usersList;
    }


}
