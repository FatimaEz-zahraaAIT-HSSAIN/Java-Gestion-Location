package controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController implements Initializable{
	

    @FXML
    private VBox VBox;
    
    private Parent fxml;

    @FXML
    private Button btn_seconnecter;

    @FXML
    private Button btn_sinscrire;

    @FXML
    void openSignIn() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
		t.setToX(VBox.getLayoutX() * (-18));
		t.play();
		t.setOnFinished(e ->{
			try {
				fxml= FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
    }

    @FXML
    void openSignUp() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
		t.setToX(0);
		t.play();
		t.setOnFinished(e ->{
			try {
				fxml= FXMLLoader.load(getClass().getResource("/interfaces/SignUp.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
		t.setToX(VBox.getLayoutX() * (-18));
		t.play();
		t.setOnFinished(e ->{
			try {
				fxml= FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
	}


}
