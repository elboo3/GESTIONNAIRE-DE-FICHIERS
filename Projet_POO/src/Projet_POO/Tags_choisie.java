/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Projet_POO;
import java.sql.*;
/**
 *
 * @author User
 */
public class Tags_choisie {
    private int ID_fichier;
    private String nom_Tag;

    public Tags_choisie(int ID_fichier, String nom_Tag) {
        this.ID_fichier = ID_fichier;
        this.nom_Tag = nom_Tag;
    }

    public int getID_fichier() {
        return ID_fichier;
    }

    public void setID_fichier(int ID_fichier) {
        this.ID_fichier = ID_fichier;
    }

    public String getNom_Tag() {
        return nom_Tag;
    }

    public void setNom_Tag(String nom_Tag) {
        this.nom_Tag = nom_Tag;
    }
    
    public void ajouteur(){
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
          String user = "system";
          String password = "bg12345";
          
           try {
                 java.sql.Connection cnn= DriverManager.getConnection(url, user, password);
                  String req = "select * from TAG_CHOISIE where nom_tag= ? and ID_FICHIER=? ";
            PreparedStatement smt = cnn.prepareStatement(req);
            smt.setString(1, nom_Tag.toLowerCase());
            smt.setInt(2, ID_fichier);
            ResultSet rs = smt.executeQuery();
               if(!rs.next()){
                   
             String req2="insert into tag_choisie  "
                     + "values(?,?)";
             PreparedStatement smt2=cnn.prepareStatement(req2);
             smt2.setInt(1, ID_fichier);
             smt2.setString(2,nom_Tag);
             smt2.executeUpdate(); 
               }
             
             cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public  void supprimer(){
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
          String user = "system";
          String password = "bg12345";
          
           try {
             java.sql.Connection cnn= DriverManager.getConnection(url, user, password);
             String req="delete from tag_choisie  "
                     + " where ID_FICHIER=? and  NOM_TAG=? ";
             PreparedStatement smt=cnn.prepareStatement(req);
             smt.setInt(1, ID_fichier);
             smt.setString(2,nom_Tag);
             smt.executeUpdate();
             cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
