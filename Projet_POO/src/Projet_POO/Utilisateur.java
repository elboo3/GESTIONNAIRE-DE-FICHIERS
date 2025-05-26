/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Projet_POO;

import java.sql.*;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class Utilisateur {

    private int ID_user;
    private String login;
    private String pwrd;
    private String role;

    public Utilisateur() {
        ID_user = 0;
        login = "";
        pwrd = "";
        role = "";
    }

    public Utilisateur(int id, String login, String pwrd, String role) {
        ID_user = id;
        this.login = login;
        this.pwrd = pwrd;
        this.role = role;
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwrd() {
        return pwrd;
    }

    public void setPwrd(String pwrd) {
        this.pwrd = pwrd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int exist() {
       
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            java.sql.Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select  id_user,pwrd from utilisateur "
                    + "where  login = ? and role =?";
            PreparedStatement pm = cnn.prepareStatement(req);
            pm.setString(1, login);
            pm.setString(2, role);
            ResultSet rs = pm.executeQuery();
            if (rs.next()) {
                if (pwrd.equals(rs.getString("pwrd"))){
                    return rs.getInt("id_user");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("échec de connexion");
                    alert.setHeaderText("Mot de passe incorecte ");
                    alert.showAndWait();
                   
                }
            }else{
                return -1;
                  
            }

        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
   
    public void ajouter(){
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";
        try{
            if(exist()>0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("échec de lors de la création");
                    alert.setHeaderText("pseudo utiliser ");
                    alert.setContentText("ce pseudo est déjà utiliser");
                    alert.showAndWait();
            }else{
                java.sql.Connection cnn = DriverManager.getConnection(url, user, password);
                String req="insert into utilisateur (LOGIN,PWRD,ROLE)"
                        + "values (?,?,?)";
                PreparedStatement pmt=cnn.prepareStatement(req);
                pmt.setString(1, login);
                pmt.setString(2, pwrd);
                pmt.setString(3, role);
                pmt.executeUpdate();
                
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("succés");
                    alert.setContentText("Compte créer avec succés");
                    alert.showAndWait();
            }
        }catch(SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
