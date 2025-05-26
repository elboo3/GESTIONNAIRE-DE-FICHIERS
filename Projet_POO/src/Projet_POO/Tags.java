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
public class Tags {

    private String nom_Tag;

    public Tags(String nom_Tag) {
        this.nom_Tag = nom_Tag;
    }

    public String getNom_Tag() {
        return nom_Tag;
    }

    public void setNom_Tag(String nom_Tag) {
        this.nom_Tag = nom_Tag;
    }

    public boolean ajouter_Tag() {
        boolean valide=false;
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            //verifier d'abord que le tags n 'existe pas dans la base 
            String req = "select * from tags where nom_tag= ? ";
            PreparedStatement smt = cnn.prepareStatement(req);
            smt.setString(1, nom_Tag.toLowerCase());
            ResultSet rs = smt.executeQuery();
            // si non un message d'erreur sera afficher 
            if (!rs.next()) {
                String req2 = "insert into tags "
                    + "values(?)";
            PreparedStatement smt2 = cnn.prepareStatement(req2);
            smt2.setString(1, nom_Tag);
            smt2.executeUpdate();
            valide=true;
               
            //  Ajout du tag 
            }else
             valide=false;  
            
            cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        return valide;
    }

  

}
