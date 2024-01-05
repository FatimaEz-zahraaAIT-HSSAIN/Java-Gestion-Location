package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class HomeController implements Initializable {
	
	
	@FXML
    private AnchorPane root;
	
	private Parent fxml;
	
	@FXML
    void accueil(MouseEvent event) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Accueil.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void contrat(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Contrat.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void facture(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Facture.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void historiques(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Historique.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void locataire(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Locataire.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void location(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Location.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void logement(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Logement.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Accueil.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
