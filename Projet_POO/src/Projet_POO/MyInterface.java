/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Projet_POO;

import java.awt.Insets;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 *
 * @author User
 */
public class MyInterface extends Application {

    private Stage primaryStage;
    private int id_user;
    private String role;

    @Override
    public void start(Stage Myform) throws Exception {
        this.primaryStage = Myform;
        primaryStage.setMaximized(true);
        bienvenu();
        primaryStage.show();
    }

    //page de login
    private void bienvenu() {
        Label lbl = new Label("bienvenu dans le sytème\n de gestion des fichiers");
        lbl.getStyleClass().add("title");
        HBox lab = new HBox(lbl);
        lab.setAlignment(Pos.CENTER);

        Button btn1 = new Button("Admin");
        Button btn2 = new Button("utilisateur");
        btn1.getStyleClass().add("role");
        btn2.getStyleClass().add("role");
        btn1.setOnAction(e -> {
            role = "admin";
            login();

        });
        btn2.setOnAction(e -> {
            role = "utilisateur";
            login();
        });
        VBox roles = new VBox(80, btn1, btn2);
        roles.setAlignment(Pos.CENTER);
        VBox container = new VBox(100, lab, roles);
        container.getStyleClass().add("container");
        BorderPane root = new BorderPane();

        root.setCenter(container);

        root.getStyleClass().add("root");
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void login() {

        //Titre
        Label titre = new Label("S'Authentifier");
        titre.getStyleClass().add("title");
        HBox Titre = new HBox(titre);
        Titre.setAlignment(Pos.CENTER);
        /////////////////////////////////////////

        // Login 
        Label lbl = new Label("Pseudo* :");
        TextField txtlogin = new TextField();
        txtlogin.setPromptText("Enter votre pseudo");
        HBox login = new HBox(10, lbl, txtlogin);
        login.getStyleClass().add("hbox");
        ////////////////////////////////////////////

        // Password 
        Label lbl2 = new Label("mot de passe* :");
        PasswordField txtpass = new PasswordField();
        txtpass.setPromptText("Enter votre mot de passe");
        HBox pwrd = new HBox(10, lbl2, txtpass);
        pwrd.getStyleClass().add("hbox");
        ////////////////////////////////////////////
        //button de login 
        Button btn = new Button("Login");
        btn.getStyleClass().add("button");
        VBox button;
        if (!role.equals("admin")) {
            Label lb = new Label("Créez un compte");
            lb.getStyleClass().add("signUp");
            lb.setOnMouseClicked(e ->sign_up());
            button = new VBox(5, btn, lb);
        } else {
            button = new VBox(5, btn);
        }

        button.setAlignment(Pos.CENTER);
        btn.setOnAction(e -> {
            if (txtlogin.getText().length() == 0 || txtpass.getText().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Des information obligatoires ne sont pas remplir");
                alert.setContentText("s' il vous plait de remplir  le champ obigatoire (avec *)");
                alert.showAndWait();
            } else {
                Utilisateur u = new Utilisateur();
                u.setLogin(txtlogin.getText());
                u.setPwrd(txtpass.getText());
                u.setRole(role);
                int id = u.exist();
                if (id > 0) {
                    if (role.equals("admin")) {

              
                        StatAdmin();
                    } else {

                        id_user = id;
                        Marquage_fichiers();
                    }
                }else{
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("échec de connexion");
                    alert.setHeaderText("Pas d'inscription ");
                    alert.setContentText("Pas d'inscription avec  '"+txtlogin.getText()+"'");
                    alert.showAndWait();
                            }
            }

        });
        /////////////////////////////////////////////////////////
        VBox container = new VBox(60, Titre, login, pwrd, button);
        container.getStyleClass().add("container");

        BorderPane root = new BorderPane();

        root.setCenter(container);

        root.getStyleClass().add("root");

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        primaryStage.setScene(scene);

    }

    private void sign_up() {
        //Titre
        Label titre = new Label("Créer un compte");
        titre.getStyleClass().add("title");
        HBox Titre = new HBox(titre);
        Titre.setAlignment(Pos.CENTER);
        /////////////////////////////////////////

        // Login 
        Label lbl = new Label("Pseudo* :");
        TextField txtlogin = new TextField();
        txtlogin.setPromptText("Enter votre pseudo");
        HBox login = new HBox(10, lbl, txtlogin);
        login.getStyleClass().add("hbox");
        ////////////////////////////////////////////

        // Password 
        Label lbl2 = new Label("mot de passe* :");
        PasswordField txtpass = new PasswordField();
        txtpass.setPromptText("Enter votre mot de passe");
        HBox pwrd = new HBox(10, lbl2, txtpass);
        pwrd.getStyleClass().add("hbox");
        ////////////////////////////////////////////
        //button de login 
        Button btn = new Button("Login");
        btn.getStyleClass().add("button");
        btn.setOnAction(eh -> {
            if(txtlogin.getText().isEmpty()||txtpass.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("échec");
                    alert.setHeaderText("champ vide ");
                    alert.setContentText("remplir tout les champ");
                    alert.showAndWait();
            }else{
            Utilisateur u = new Utilisateur();
            u.setLogin(txtlogin.getText());
            u.setPwrd(txtpass.getText());
            u.setRole(role);
            u.ajouter(); 
            }
            
        });
       VBox button = new VBox( btn);
        button.setAlignment(Pos.CENTER);
        VBox container = new VBox(60, Titre, login, pwrd, button);
        container.getStyleClass().add("container");

        BorderPane root = new BorderPane();

        root.setCenter(container);

        root.getStyleClass().add("root");

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        primaryStage.setScene(scene);

    }

    // la page de  ajout de fichier comme favoris
    private void Marquage_fichiers() {

        //Ajouer un menu 
        ToolBar toolBar = new ToolBar();
        Region spacer1 = new Region();
        spacer1.setPrefHeight(20);
        VBox menu = new VBox(toolBar, spacer1);
        toolBar.getStyleClass().add("toolbar");
        Button btnMarquage = new Button("Marquage des fichiers");
        btnMarquage.setOnAction(e -> Marquage_fichiers());

        Button btnModif = new Button("Modifier les fichiers");
        btnModif.setOnAction(e -> modifier());
        Button btnList = new Button("Lister les fichiers");
        btnList.setOnAction(e -> listage());

        Button btnRecherche = new Button("Chercher des fichiers");
        btnRecherche.setOnAction(e -> chercher());

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setOnAction(e -> {
            id_user = 0;
            bienvenu();
        });

// Ajouter tous les boutons à la ToolBar
        toolBar.getItems().addAll(
                btnMarquage,
                btnModif,
                btnList,
                btnRecherche,
                btnDeconnexion
        );

        //Ajouter le zone de recherche 
        Label lb = new Label("Ajouter un fichier");
        lb.getStyleClass().add("title");
        //Titre
        Label titre = new Label("Titre*");
        TextField txttitre = new TextField();
        HBox champtitre = new HBox(40, titre, txttitre);
        //Auteur
        Label auteur = new Label("Auteur");
        TextField txtauteur = new TextField();
        HBox champauteur = new HBox(40, auteur, txtauteur);
        //les tags 
        Label lbltag = new Label("Tags*");
        ListView<String> ListTags = new ListView<>();
        ListTags.setMinWidth(400);  // Largeur minimale
        ListTags.setMaxWidth(400);
        ListTags.setMinHeight(200);
        ListTags.setMaxHeight(200);
        ListTags.getItems().clear();
        VBox tagcontainer = new VBox(5, lbltag, ListTags);
        //Ajouter un tag 
        Label tag = new Label("Ajouter un tag");
        TextField txttag = new TextField();
        Button ajouter = new Button("Ajouter");
        ajouter.setOnAction(e -> ajouterTag(ListTags, txttag));
        HBox champtag = new HBox(40, tag, txttag, ajouter);
        //résume
        Label résume = new Label("Resumé");
        TextArea txtrésume = new TextArea();
        HBox champrésume = new HBox(40, résume, txtrésume);
        //commentaire
        Label commentaire = new Label("commentaire");
        TextArea txtcommentaire = new TextArea();
        HBox champcommentaire = new HBox(40, commentaire, txtcommentaire);

        //choix du fichier 
        Label choix = new Label("Choisir un fichier");
        Button btn = new Button("importer un fichier");

        //ajouter l'evenement de selection du  fichier
        TextField txtfile = new TextField();
        btn.setOnAction(e -> {
            FileChooser choser = new FileChooser();
            File file = choser.showOpenDialog(primaryStage);

            txtfile.setText(file.getAbsolutePath());

        });
        HBox champchoix = new HBox(40, choix, btn, txtfile);
        //Ajouter le button de validation
        Button btnvalider = new Button("Ajouter le fichier comme favoris");
        btnvalider.setOnAction(e -> ajouterFchier(ListTags, txttitre, txttag, txtauteur, txtrésume, txtcommentaire, txtfile));
        //vbox qui regroupe tout les champ de saisie 
        Region spac = new Region();
        spac.setPrefHeight(10);
        VBox cordonnee = new VBox(40, lb, spac, champauteur, champtitre, champtag, tagcontainer, champrésume, champcommentaire, champchoix, btnvalider);
        cordonnee.getStyleClass().add("cordonnee");
        BorderPane root = new BorderPane();
        root.setTop(menu);
        Region spacer3 = new Region();
        spacer3.setPrefHeight(50);
        root.setBottom(spacer3);

        root.setCenter(cordonnee);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        root.getStyleClass().add("root");
        Scene scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("marquage.css").toExternalForm());
        primaryStage.setScene(scene);

    }

    //page de listage des fichier marquer comme favoris
    private void listage() {
        //le menu 
        ToolBar toolBar = new ToolBar();

        Button btnMarquage = new Button("Marquage des fichiers");
        btnMarquage.setOnAction(e -> Marquage_fichiers());

        Button btnModif = new Button("Modifier les fichiers");
        btnModif.setOnAction(e -> modifier());
        Button btnList = new Button("Lister les fichiers");
        btnList.setOnAction(e -> listage());

        Button btnRecherche = new Button("Chercher des fichiers");
        btnRecherche.setOnAction(e -> chercher());

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setOnAction(e -> {
            id_user = 0;
            bienvenu();
        });

// Ajouter tous les boutons à la ToolBar
        toolBar.getItems().addAll(
                btnMarquage,
                btnModif,
                btnList,
                btnRecherche,
                btnDeconnexion
        );

        VBox Liste_Ficiher = new VBox(25);
        // ajouter les fichier
        // region permet d'ajouter comme un espace entre deux élements
        Region spacer1 = new Region();
        spacer1.setPrefHeight(20);
        Label lb = new Label("Liste des fichiers");
        Liste_Ficiher.getChildren().add(spacer1);
        Liste_Ficiher.getChildren().add(lb);
        lister_fichiers(Liste_Ficiher);

        Button fich = new Button("exporter vers un fichier");

        fich.setOnAction(e -> exporter_fichier());
        Liste_Ficiher.getChildren().add(fich);
        if (Liste_Ficiher.getChildren().size() == 3) {
            fich.setDisable(true);
        } else {
            fich.setDisable(false);
        }

        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        toolBar.getStyleClass().add("toolbar");
        Region spacer2 = new Region();
        spacer2.setPrefWidth(80);
        root.setLeft(spacer2);
        root.setCenter(Liste_Ficiher);
        Region spacer3 = new Region();
        spacer3.setPrefHeight(50);
        root.setBottom(spacer3);
        spacer3.getStyleClass().add("spac");
        root.setPrefHeight(primaryStage.getHeight());
        ScrollPane scrollPane = new ScrollPane(root);

        scrollPane.setFitToWidth(true);
        root.getStyleClass().add("root");

        Scene scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

    }

    //page de recherche des fichier marquer 
    private void chercher() {
        ToolBar toolBar = new ToolBar();

        toolBar.getStyleClass().add("toolbar");
        Button btnMarquage = new Button("Marquage des fichiers");
        btnMarquage.setOnAction(e -> Marquage_fichiers());

        Button btnModif = new Button("Modifier les fichiers");
        btnModif.setOnAction(e -> modifier());
        Button btnList = new Button("Lister les fichiers");
        btnList.setOnAction(e -> listage());

        Button btnRecherche = new Button("Chercher des fichiers");
        btnRecherche.setOnAction(e -> chercher());

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setOnAction(e -> {
            id_user = 0;
            bienvenu();
        });

// Ajouter tous les boutons à la ToolBar
        toolBar.getItems().addAll(
                btnMarquage,
                btnModif,
                btnList,
                btnRecherche,
                btnDeconnexion
        );
        Label lbl = new Label("Effectuer un recherche par : ");
        RadioButton rb1 = new RadioButton("auteur");
        RadioButton rb2 = new RadioButton("titre");
        RadioButton rb3 = new RadioButton("tag");
        // Groupe pour les rendre exclusifs (seul un peut être sélectionné)
        ToggleGroup group = new ToggleGroup();
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        TextField recherche = new TextField();
        recherche.setPrefWidth(200);
        Button btn = new Button("Chercher");
        Region spacer1 = new Region();
        spacer1.setPrefHeight(10);
        VBox choix = new VBox(10, lbl, rb1, rb2, rb3, recherche, btn);
        VBox result = new VBox(10);
        Region spacer3 = new Region();
        spacer3.setPrefHeight(50);
        VBox container = new VBox(40, spacer1, choix, result, spacer3);
        btn.setOnAction(e -> recherche_fichier(group, result, recherche));
        BorderPane root = new BorderPane();
        Region spacer2 = new Region();
        spacer2.setPrefWidth(20);
        Region spacer4 = new Region();
        spacer4.setPrefWidth(600);
        root.setTop(toolBar);
        root.setLeft(spacer2);
        root.setRight(spacer4);

        root.setCenter(container);
        root.setPrefHeight(primaryStage.getHeight());
        ScrollPane scrollPane = new ScrollPane(root);

        scrollPane.setFitToWidth(true);
        root.getStyleClass().add("root");

        Scene scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void modifier() {
        ToolBar toolBar = new ToolBar();
        Region spacer = new Region();
        spacer.setPrefHeight(20);
        VBox menu = new VBox(10, toolBar, spacer);
        toolBar.getStyleClass().add("toolbar");
        Button btnMarquage = new Button("Marquage des fichiers");
        btnMarquage.setOnAction(e -> Marquage_fichiers());

        Button btnModif = new Button("Modifier les fichiers");

        Button btnList = new Button("Lister les fichiers");
        btnList.setOnAction(e -> listage());

        Button btnRecherche = new Button("Chercher des fichiers");
        btnRecherche.setOnAction(e -> chercher());

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setOnAction(e -> {
            id_user = 0;
            bienvenu();
        });

// Ajouter tous les boutons à la ToolBar
        toolBar.getItems().addAll(
                btnModif,
                btnMarquage,
                btnList,
                btnRecherche,
                btnDeconnexion
        );

        ListView<String> listProject = new ListView<String>();
        recuperer_id(listProject);

        // les champ pour modifier le fichier 
        Label lb = new Label("Id_ficiher");
        TextField txt = new TextField();
        HBox v = new HBox(5, lb, txt);
        Label lb2 = new Label("Auteur");
        TextField txt2 = new TextField();
        HBox v2 = new HBox(5, lb2, txt2);
        Label lb3 = new Label("Titre");
        TextField txt3 = new TextField();
        HBox v3 = new HBox(5, lb3, txt3);

        VBox coor = new VBox(40, v, v2, v3);
        Button modifier = new Button("Modifier");
        Button Supprimer = new Button("Supprimer");

        VBox btncont = new VBox(40, modifier, Supprimer);
        HBox container1 = new HBox(200, listProject, coor, btncont);

        Label lb4 = new Label("Tag");
        TextField txt4 = new TextField();
        ListView<String> ListTags = new ListView<>();
        Button btn = new Button("Ajouter");
        btn.setOnAction(e -> ajouterTag(ListTags, txt4));
        VBox vv = new VBox(5, txt4, btn);

        HBox h = new HBox(lb4, vv);
        HBox conttag = new HBox(30, h, ListTags);
        Label lb5 = new Label("Résume");
        TextArea a = new TextArea();
        a.setWrapText(true);
        Label lb6 = new Label("commantaire");
        TextArea a2 = new TextArea();
        a2.setWrapText(true);
        HBox h2 = new HBox(10, lb5, a);
        HBox h3 = new HBox(10, lb6, a2);
        HBox container2 = new HBox(40, conttag, h2, h3);
        VBox container = new VBox(60, container1, container2);
        Region spac = new Region();
        spac.setPrefWidth(50);
        //modification
        modifier.setOnAction(e
                -> appliquer_modif(txt.getText(), txt2, txt3, ListTags, a, a2));
        Supprimer.setOnAction(e -> {
            supprimer_projet(txt.getText());
            recuperer_id(listProject);
            txt.clear();
            txt2.clear();
            txt3.clear();
            txt4.clear();
            a.clear();
            a2.clear();
            ListTags.getItems().clear();
        });
        //recuperation des corr de fichier selectionner 
        listProject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ListTags.getItems().clear();
                txt.setText(newValue);
                txt.setEditable(false);
                remplirchamp(newValue, txt2, txt3, ListTags, a, a2);
            }
        });

        //suppression d' un tag direct sur liste
        ListTags.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("DELETE")) {
                int selectedIndex = ListTags.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    String tag = ListTags.getItems().get(selectedIndex);
                    ListTags.getItems().remove(selectedIndex);
                    Tags_choisie t = new Tags_choisie(Integer.parseInt(txt.getText()), tag);
                    t.supprimer();
                }
            }

        });

        BorderPane root = new BorderPane();
        root.setTop(menu);
        root.setLeft(spac);
        root.setCenter(container);
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add(getClass().getResource("modif.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void StatAdmin() {
        ToolBar toolBar = new ToolBar();
        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setOnAction(e -> {
            id_user = 0;
            bienvenu();
        });
        toolBar.getItems().add(btnDeconnexion);
        toolBar.getStyleClass().add("toolbar");
        // block des statistique
        //nombre  total des fichier marquer comme favoris 
        Label nbtotal = new Label("nombre total marqués comme favoris :  ");
        Label valeur = new Label();
        nbrtotalfichier(valeur);
        HBox containernbtotal = new HBox(10, nbtotal, valeur);

        //liste des auteur 
        ListView<String> listAuteur = new ListView<>();
        Label auteur = new Label("Liste des auteur");
        listAuteur(listAuteur);
        VBox containerAuteur = new VBox(10, auteur, listAuteur);

        //liste des Tags utiliser
        ListView<String> ListTags = new ListView<>();
        listTags(ListTags);
        Label Tags = new Label("Liste des Tags utiliser ");
        VBox containerTags = new VBox(10, Tags, ListTags);
        // liste des Tags avec le nombre de fichier associer
        ListView<String> TagsNB = new ListView<>();
        nbTags(TagsNB);
        Label nbtags = new Label("Nombre de fichiers associés à chaque tag");
        VBox nbcontainer = new VBox(10, nbtags, TagsNB);
        BorderPane root = new BorderPane();
        // button d'exportation vers fichier.txt
        Button exporter = new Button("exporter vers un fichier.txt");
        exporter.setOnAction(e -> exporter_stat(valeur, listAuteur, ListTags, TagsNB));
        // ajouter l 'evenment d'exporation
        Region spaRegion = new Region();
        spaRegion.setPrefHeight(3);
        HBox h = new HBox(exporter);
        HBox h2 = new HBox(50, containernbtotal, containerAuteur, containerTags, nbcontainer);
        VBox container = new VBox(30, spaRegion, h2, h);
        root.setTop(toolBar);
        root.setLeft(container);
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add(getClass().getResource("stat.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void ajouterTag(ListView<String> tags, TextField txt) {
        if (tags.getItems().contains(txt.getText().toLowerCase()) || txt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("redendance du Tag");
            alert.setContentText("le Tag qui en traint du tapez est déjà existe");
            alert.showAndWait();
        } else {
            tags.getItems().add(txt.getText().toLowerCase());
        }
    }

    //fonction qui verifier la validité des champ et ajouter  le fichier comme favoris 
    private void ajouterFchier(ListView<String> listtags, TextField txttitre, TextField txttag, TextField txtauteur, TextArea txtrésume, TextArea txtcommentaire, TextField txtfile) {

        if (txttitre.getText().length() == 0 || (listtags.getItems().isEmpty()) || txtfile.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Des information obligatoires ne sont pas remplir");
            alert.setContentText("s' il vous plait de remplir le champ obigatoire (avec *) ,");
            alert.showAndWait();
        } else {

            // ajouter fichier comme favoris 
            Fichier fich = new Fichier(txtauteur.getText(), txttitre.getText(), txtfile.getText(), txtrésume.getText(), txtcommentaire.getText(), id_user);
            boolean ajout = fich.ajouter_fichier();

            if (ajout) {
                // ajouter les nouveaux Tags au table Tags dans la base de données 
                Tags t;
                for (int i = 0; i < listtags.getItems().size(); i++) {
                    t = new Tags(listtags.getItems().get(i));
                    t.ajouter_Tag();
                }
                //ajouter tout les tags selectionner dans la table tag_choisie par utilisateur 
                Tags_choisie T;
                for (int j = 0; j < listtags.getItems().size(); j++) {
                    T = new Tags_choisie(fich.getID_fichier(), listtags.getItems().get(j));
                    T.ajouteur();

                }
                txttitre.clear();
                txtauteur.clear();
                txtfile.clear();
                txtrésume.clear();
                txtcommentaire.clear();
            }

        }
    }

    //fonction qu permet de lister tout les fichier 
    private void lister_fichiers(VBox v) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {

            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "SELECT *  FROM fichier WHERE ID_USER = ?";
            PreparedStatement smt = cnn.prepareStatement(req);
            smt.setInt(1, id_user);
            ResultSet rs = smt.executeQuery();
            TextArea txt;
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Numéro fichier :  ").append(rs.getInt("ID_FICHIER")).append("\n\n");
                sb.append("Titre :  ").append(rs.getString("TITRE")).append("\n\n");
                if (rs.getString("AUTEUR") == null) {
                    sb.append("Auteur :  ").append("\n\n");
                } else {
                    sb.append("Auteur :  ").append(rs.getString("AUTEUR")).append("\n\n");
                }

                sb.append("Nom physique :  ").append(rs.getString("SCHEMA_FICHIER")).append("\n\n");
                if (rs.getString("RESUME") == null) {
                    sb.append("Résume :  ").append("\n\n");
                } else {
                    sb.append("Résume :  ").append(rs.getString("RESUME")).append("\n\n");
                }

                if (rs.getString("COMMENTAIRE") == null) {
                    sb.append("commantaire :  ").append("\n\n");
                } else {
                    sb.append("commantaire :  ").append(rs.getString("COMMENTAIRE")).append("\n\n");
                }

                //recuperer les tag associer a ce fichier 
                String req2 = "select NOM_TAG from  tag_choisie where ID_FICHIER = ?";

                PreparedStatement pm = cnn.prepareStatement(req2);
                pm.setInt(1, rs.getInt("ID_FICHIER"));
                ResultSet rs2 = pm.executeQuery();
                sb.append("Tags :  ");
                while (rs2.next()) {
                    sb.append(rs2.getString("NOM_TAG") + " ; ");
                }

                txt = new TextArea(sb.toString());
                v.getChildren().add(txt);

                sb.setLength(0);
                txt.setWrapText(true);
                txt.setEditable(false);

            }
            cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //fonction qui exporter le fichier marquer comme favoris dans un fichier texte
    private void exporter_fichier() {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            PrintWriter Fsortie = new PrintWriter(new FileWriter("C:\\Users\\User\\Desktop\\Projet_POO2_AYED Youssef_BOUAZIZI_Rayen_2BIS1\\fichier_favoris.txt"));

            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "SELECT *  FROM fichier WHERE ID_USER = ?";
            PreparedStatement smt = cnn.prepareStatement(req);
            smt.setInt(1, id_user);
            ResultSet rs = smt.executeQuery();
            Fichier f;
            while (rs.next()) {
                f = new Fichier(rs.getString("AUTEUR"), rs.getString("TITRE"), rs.getString("SCHEMA_FICHIER"), rs.getString("RESUME"), rs.getString("COMMENTAIRE"), 1);
                f.setID_fichier(rs.getInt("ID_FICHIER"));
                //recuperer les tag associer a ce fichier 
                String req2 = "select NOM_TAG from  tag_choisie where ID_FICHIER = ?";

                PreparedStatement pm = cnn.prepareStatement(req2);
                pm.setInt(1, rs.getInt("ID_FICHIER"));
                ResultSet rs2 = pm.executeQuery();
                StringBuilder sb = new StringBuilder();
                sb.append("\nTags :  ");
                while (rs2.next()) {
                    sb.append(rs2.getString("NOM_TAG") + " ; ");
                }

                Fsortie.println(f + " " + sb + "\n");

                sb.setLength(0);

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succées ");
            alert.setHeaderText("Exportation");
            alert.setContentText("les données sont exportées  avec succées ");
            alert.showAndWait();
            Fsortie.close();
            cnn.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recherche_fichier(ToggleGroup group, VBox v, TextField txt) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            RadioButton selectedRadio = (RadioButton) group.getSelectedToggle();
            if (selectedRadio != null) {
                String req;
                PreparedStatement pm;
                ResultSet rs;

                if (selectedRadio.getText() == "auteur" || selectedRadio.getText() == "titre") {
                    v.getChildren().clear();
                    String colonne = selectedRadio.getText();
                    if (txt.getText().isEmpty()) {
                        req = "SELECT ID_FICHIER, TITRE, SCHEMA_FICHIER "
                                + "FROM fichier "
                                + "WHERE " + colonne + " is null ";
                        pm = cnn.prepareStatement(req);
                    } else {
                        req = "SELECT ID_FICHIER, TITRE, SCHEMA_FICHIER "
                                + "FROM fichier "
                                + "WHERE LOWER(" + colonne + ") = ? ";
                        pm = cnn.prepareStatement(req);
                        pm.setString(1, txt.getText().toLowerCase());
                    }

                    rs = pm.executeQuery();
                    if (!rs.next()) {
                        Label lbl = new Label("acune résultat trouver");
                        v.getChildren().add(lbl);
                    } else {

                        TextArea text;
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append("Numéro fichier :  ").append(rs.getInt("ID_FICHIER")).append("\n\n");
                            sb.append("Titre :  ").append(rs.getString("TITRE")).append("\n\n");
                            sb.append("Nom physique :  ").append(rs.getString("SCHEMA_FICHIER")).append("\n\n");
                            text = new TextArea(sb.toString());
                            text.setWrapText(true);
                            text.setEditable(false);
                            v.getChildren().add(text);
                            sb.setLength(0);
                        } while (rs.next());
                    }

                } else {
                    v.getChildren().clear();
                    req = "SELECT f.ID_FICHIER, f.TITRE, f.SCHEMA_FICHIER "
                            + "FROM tag_choisie t "
                            + "JOIN fichier f ON t.ID_FICHIER = f.ID_FICHIER "
                            + "WHERE LOWER(t.NOM_TAG) = ?";
                    pm = cnn.prepareStatement(req);
                    pm.setString(1, txt.getText().toLowerCase());
                    rs = pm.executeQuery();

                    if (!rs.next()) {
                        Label lbl = new Label("acune résultat trouver");
                        v.getChildren().add(lbl);
                    } else {
                        TextArea text;
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append("Numéro fichier :  ").append(rs.getInt("ID_FICHIER")).append("\n\n");
                            sb.append("Titre :  ").append(rs.getString("TITRE")).append("\n\n");
                            sb.append("Nom physique :  ").append(rs.getString("SCHEMA_FICHIER")).append("\n\n");
                            text = new TextArea(sb.toString());
                            v.getChildren().add(text);
                            sb.setLength(0);
                        } while (rs.next());
                    }

                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("critère du recherche null");
                alert.setContentText("s' il vous plait de choisir un critère pour effectuer votre recherche");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void nbrtotalfichier(Label l) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select count(*) nb  from fichier ";
            Statement smt = cnn.createStatement();
            ResultSet rs = smt.executeQuery(req);
            rs.next();
            l.setText(String.valueOf(rs.getInt("nb")));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void listAuteur(ListView<String> listAuteur) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select distinct(AUTEUR) nb  from fichier  where AUTEUR is not null ";
            Statement smt = cnn.createStatement();
            ResultSet rs = smt.executeQuery(req);
            while (rs.next()) {
                listAuteur.getItems().add(rs.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void listTags(ListView<String> listTags) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select distinct(NOM_TAG) nb  from tag_choisie ";
            Statement smt = cnn.createStatement();
            ResultSet rs = smt.executeQuery(req);
            while (rs.next()) {
                listTags.getItems().add(rs.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void nbTags(ListView<String> TagsNb) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select  NOM_TAG , count(*) nb  "
                    + "from tag_choisie  "
                    + "group by NOM_TAG  "
                    + "order by count(*) desc";
            Statement smt = cnn.createStatement();
            ResultSet rs = smt.executeQuery(req);
            while (rs.next()) {
                TagsNb.getItems().add(rs.getString("NOM_TAG") + " :  " + rs.getInt("nb") + " fois");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void exporter_stat(Label valeur, ListView<String> listAuteur, ListView<String> ListTags, ListView<String> TagsNB) {
        try {
            PrintWriter Fsortie = new PrintWriter(new FileWriter("C:\\Users\\User\\Desktop\\Projet_POO2_AYED Youssef_BOUAZIZI_Rayen_2BIS1\\stat_Admin.txt"));
            Fsortie.println("nombre total marqués comme favoris :  " + valeur.getText());

            Fsortie.print("liste des auteurs :  ");
            for (int i = 0; i < listAuteur.getItems().size(); i++) {
                Fsortie.print(listAuteur.getItems().get(i) + " ; ");
            }

            Fsortie.print("\n");
            Fsortie.print("liste des Tags utiliser :  ");
            for (int i = 0; i < ListTags.getItems().size(); i++) {
                Fsortie.print(ListTags.getItems().get(i) + " ; ");
            }
            Fsortie.print("\n");
            Fsortie.print("Nombre des fichies associés a chaque Tag :  ");
            for (int i = 0; i < TagsNB.getItems().size(); i++) {
                Fsortie.print(TagsNB.getItems().get(i) + " ; ");
            }
            Fsortie.print("\n");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succées ");
            alert.setHeaderText("Exportation");
            alert.setContentText("les données sont exportées  avec succées ");
            alert.showAndWait();
            Fsortie.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void recuperer_id(ListView<String> l) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";
        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select ID_FICHIER from fichier where ID_USER=?";
            PreparedStatement pmt = cnn.prepareStatement(req);
            pmt.setInt(1, id_user);
            ResultSet rs = pmt.executeQuery();
            l.getItems().clear();
            while (rs.next()) {
                l.getItems().add(String.valueOf(rs.getInt("ID_FICHIER")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void supprimer_projet(String id_projet) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";
        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "delete from  fichier where ID_FICHIER=?";
            PreparedStatement pmt = cnn.prepareStatement(req);
            pmt.setInt(1, Integer.parseInt(id_projet));
          pmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("Suppression avec succes ");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void remplirchamp(String id_projet, TextField txt2, TextField txt3, ListView<String> ListTags, TextArea a, TextArea a2) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            String req = "select * from fichier where ID_FICHIER=?";
            PreparedStatement pmt = cnn.prepareStatement(req);
            pmt.setInt(1, Integer.parseInt(id_projet));
            ResultSet rs = pmt.executeQuery();
            rs.next();
            txt2.setText(rs.getString("AUTEUR"));
            txt3.setText(rs.getString("TITRE"));
            a.setText(rs.getString("RESUME"));
            a2.setText(rs.getString("COMMENTAIRE"));

            String req2 = "select NOM_TAG from TAG_CHOISIE where ID_FICHIER=?";
            PreparedStatement pmt2 = cnn.prepareStatement(req2);
            pmt2.setInt(1, Integer.parseInt(id_projet));
            ResultSet r2 = pmt2.executeQuery();
            while (r2.next()) {
                ListTags.getItems().add(r2.getString("NOM_TAG"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void appliquer_modif(String id_projet, TextField txt2, TextField txt3, ListView<String> ListTags, TextArea a, TextArea a2) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "bg12345";

        try {
            if (txt3.getText().isEmpty() || ListTags.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Des information obligatoires ne sont pas remplir");
                alert.setContentText("s' il vous plait de remplir le champ obigatoire (avec *) ,");
                alert.showAndWait();
            }
            Connection cnn = DriverManager.getConnection(url, user, password);
            //appliquer les modif sur fichier
            String req = "UPDATE fichier "
                    + "SET AUTEUR = ?, TITRE = ?, COMMENTAIRE = ?, RESUME = ? "
                    + "WHERE ID_FICHIER = ?";
            PreparedStatement pmt = cnn.prepareStatement(req);
            pmt.setString(1, txt2.getText());
            pmt.setString(2, txt3.getText());
            pmt.setString(3, a2.getText());
            pmt.setString(4, a.getText());
            pmt.setInt(5, Integer.parseInt(id_projet));
            pmt.executeUpdate();

            //ajouter les nouveau tags 
            Tags t;
            for (int i = 0; i < ListTags.getItems().size(); i++) {
                t = new Tags(ListTags.getItems().get(i));
                t.ajouter_Tag();
            }
            //relation entre tag et fichier
            Tags_choisie tc;
            for (int i = 0; i < ListTags.getItems().size(); i++) {
                tc = new Tags_choisie(Integer.parseInt(id_projet), ListTags.getItems().get(i));
                tc.ajouteur();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("modification avec succes ");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
