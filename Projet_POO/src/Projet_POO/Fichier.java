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
public class Fichier {
    private int ID_fichier;
    private String Auteur;
    private String Titre;
    private String Schema;
    private String resume;
    private String commentaire;
    private int ID_utilisateur;

    public Fichier( String Auteur, String Titre, String Schema, String resume, String commentaire, int ID_utilisateur) {
        this.ID_fichier = 0;
        this.Auteur = Auteur;
        this.Titre = Titre;
        this.Schema = Schema;
        this.resume = resume;
        this.commentaire = commentaire;
        this.ID_utilisateur = ID_utilisateur;
    }

    public int getID_fichier() {
        return ID_fichier;
    }

    public void setID_fichier(int ID_fichier) {
        this.ID_fichier = ID_fichier;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String Auteur) {
        this.Auteur = Auteur;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public String getSchema() {
        return Schema;
    }

    public void setSchema(String Schema) {
        this.Schema = Schema;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getID_utilisateur() {
        return ID_utilisateur;
    }

    public void setID_utilisateur(int ID_utilisateur) {
        this.ID_utilisateur = ID_utilisateur;
    }
    
    public boolean ajouter_fichier(){
        Boolean succ=false;
          String url = "jdbc:oracle:thin:@localhost:1521:XE";
          String user = "system";
          String password = "bg12345";
          
           try {
        
             java.sql.Connection cnn= DriverManager.getConnection(url, user, password);
             String req = "select * from fichier where schema_fichier= ?";
            PreparedStatement smt = cnn.prepareStatement(req);
            smt.setString(1, Schema);
            ResultSet rs = smt.executeQuery();
            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("redendance du fichier");
                alert.setContentText("le fichier déjà marquer comme favoris ");
                alert.showAndWait();
                 succ=false;
            }else{
             String req2="insert into fichier (auteur,titre,resume,commentaire,schema_fichier,id_user) "
                     + "values(?,?,?,?,?,?)";
             PreparedStatement smt2=cnn.prepareStatement(req2);
             smt2.setString(1, Auteur);
             smt2.setString(2, Titre);
             smt2.setString(3, resume);
             smt2.setString(4,commentaire);
             smt2.setString(5, Schema);
             smt2.setInt(6, ID_utilisateur);
             smt2.executeUpdate();
             
             // recuperer l' id  de fichier 
             String req3= "select id_fichier from fichier where  schema_fichier= ?";
             PreparedStatement smt3 = cnn.prepareStatement(req);
             smt3.setString(1, Schema);
             ResultSet rs3 = smt3.executeQuery();
             rs3.next();
             
             
            // charger l id de fichier 
             ID_fichier=rs3.getInt("id_fichier");
             succ=true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("succées ");
                alert.setHeaderText("Ajout du fichier");
                alert.setContentText("le fichier est ajouter avec succées ");
                alert.showAndWait();
            }
             cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        return succ;
    }

    @Override
    public String toString() {
        return   "ID_fichier : " + ID_fichier + "\n Auteur : " + Auteur + "\n Titre : " + Titre + "\n Schema : " + Schema + "\n resume : " + resume + "\n commentaire : " + commentaire ;
    }
    
   
    
    
}
