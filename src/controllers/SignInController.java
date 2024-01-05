package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.fxml.Initializable;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignInController implements Initializable{
	
	Connection cn;
	public PreparedStatement st;
	public ResultSet rs;
	
	@FXML
    private VBox VBox;
	private Parent fxml;
	
	@FXML
    private Button btn_passwordForgoten;

    @FXML
    private Button btn_seconnecter;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_userName;

    @FXML
    void openHome(ActionEvent event) {
    	String nom = txt_userName.getText();
    	String pass = txt_password.getText();
    	String sql = "select userName, password from admin";
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			if(rs.next()) {
				if (nom.equals(rs.getString("userName")) && pass.equals(rs.getString("password"))) {
		    		System.out.println("bien");
		    		VBox.getScene().getWindow().hide();
		    		Stage home = new Stage();
		    		try {
						fxml = FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
						Scene scene = new Scene(fxml);
						home.setScene(scene);
						home.show();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    	}else {
		    		Alert alert = new Alert(AlertType.ERROR, "Les informations sont incorrect.", ButtonType.OK);
		    		alert.showAndWait();
		    	}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void sendPassword(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cn = ConnexionMysql.connexionDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
