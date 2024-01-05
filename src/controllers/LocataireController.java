package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Locataire;


public class LocataireController implements Initializable{
	
	Connection cn;
	public PreparedStatement st;
	public ResultSet rs;
	
	@FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_edit;

    @FXML
    private TableColumn<Locataire, String> cin_cin;

    @FXML
    private TableColumn<Locataire, Date> cin_date;

    @FXML
    private TableColumn<Locataire, Integer> cin_id;

    @FXML
    private TableColumn<Locataire, String> cin_nom;

    @FXML
    private TableColumn<Locataire, String> cin_tel;

    @FXML
    private DatePicker dateNai;

    @FXML
    private TableView<Locataire> table_locataire;

    @FXML
    private TextField txt_CIN;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_searchCIN;

    @FXML
    private TextField txt_tel;
    
	public ObservableList<Locataire> data = FXCollections.observableArrayList();

    @FXML
    void addLocataire() {
    	String nom = txt_nom.getText();
    	String tel = txt_tel.getText();
    	String cin = txt_CIN.getText();
    	
    	String sql = "insert into locataire(nomprenomL, datenaissL, teleL, CIN) values(?,?,?,?)";
    	
    	if(!nom.equals("") && !tel.equals("") && !cin.equals("") && !dateNai.getValue().equals(null)) {
    		try (
        			PreparedStatement st = cn.prepareStatement(sql)){
        				st.setString(1, nom);
        				java.util.Date d = java.util.Date.from(dateNai.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        				Date sqlDate = new Date(d.getTime());
        				st.setDate(2, sqlDate);
        				st.setString(3, tel);
        				st.setString(4, cin);
        	            st.execute();
        	            
        	            txt_CIN.setText("");
        	            txt_tel.setText("");
    	            	txt_nom.setText("");
    	            	txt_searchCIN.setText("");
    	            	dateNai.setValue(null);
        	            
    	            	Alert a = new Alert(AlertType.CONFIRMATION, "Locataire ajoutee avec succes!", ButtonType.OK);
    	            	a.showAndWait();
        	            
    	            	showLocataire();
        	            
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
    	}else {
    		Alert a = new Alert(AlertType.WARNING, "Veuillez remplir tous les champs.", ButtonType.OK);
    		a.showAndWait();
    	}
    	
    	

    }

    @FXML
    void deleteLocataire() {
    	
    	String sql = "delete from locataire where CIN = ?";
    	
    	if(!txt_searchCIN.getText().equals("") ) {
    		try (
        			PreparedStatement st = cn.prepareStatement(sql)){
        				st.setString(1, txt_searchCIN.getText());
        	            st.execute();
        	            
        	            txt_CIN.setText("");
        	            txt_tel.setText("");
    	            	txt_nom.setText("");
    	            	txt_searchCIN.setText("");
    	            	dateNai.setValue(null);
        	            
    	            	Alert a = new Alert(AlertType.CONFIRMATION, "Locataire supprime avec succes!", ButtonType.OK);
    	            	a.showAndWait();
        	            
    	            	showLocataire();
        	            
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
    	}else {
    		Alert a = new Alert(AlertType.WARNING, "Vous n'avez pas selectionne un locataire a supprimer.", ButtonType.OK);
    		a.showAndWait();
    	}

    }

    @FXML
    void editLocataire() {
    	String nom = txt_nom.getText();
    	String tel = txt_tel.getText();
    	String cin = txt_CIN.getText();
    	
    	String sql = "update locataire set nomprenomL = ?, datenaissL = ?, teleL = ?, CIN = ? where CIN = ?";
    	
    	if(!nom.equals("") && !tel.equals("") && !cin.equals("") && !dateNai.getValue().equals(null)) {
    		try (
        			PreparedStatement st = cn.prepareStatement(sql)){
        				st.setString(1, nom);
        				java.util.Date d = java.util.Date.from(dateNai.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        				Date sqlDate = new Date(d.getTime());
        				st.setDate(2, sqlDate);
        				st.setString(3, tel);
        				st.setString(4, cin);
        				st.setString(5, txt_searchCIN.getText());
        	            st.executeUpdate();
        	            
        	            txt_CIN.setText("");
        	            txt_tel.setText("");
    	            	txt_nom.setText("");
    	            	txt_searchCIN.setText("");
    	            	dateNai.setValue(null);
        	            
    	            	Alert a = new Alert(AlertType.CONFIRMATION, "Locataire modifie avec succes!", ButtonType.OK);
    	            	a.showAndWait();
        	            
    	            	showLocataire();
        	            
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
    	}else {
    		Alert a = new Alert(AlertType.WARNING, "Veuillez remplir tous les champs.", ButtonType.OK);
    		a.showAndWait();
    	}
    }

    @FXML
    void searchLocataire() {
    	String sql = "select * from locataire where CIN = ?";
    	int erreur = 1;
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, txt_searchCIN.getText());
    	            rs= st.executeQuery();
    	            
    	            
    	            if(rs.next()) {
    	            	txt_CIN.setText(rs.getString("CIN"));
    	            	txt_tel.setText(rs.getString("teleL"));
    	            	txt_nom.setText(rs.getString("nomprenomL"));
    	            	Date d = rs.getDate("datenaissL");
    	            	dateNai.setValue(d.toLocalDate());
    	            	erreur = 0;
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	if(erreur == 1) {
    		
    		Alert a = new Alert(AlertType.INFORMATION, "Aucun locataire trouve avec le code CIN: "+txt_searchCIN.getText(), ButtonType.CLOSE );
    		a.showAndWait();
    		
    	}
    }
    
    public void showLocataire() {
    	table_locataire.getItems().clear();
    	String sql = "select * from locataire";
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				data.add(new Locataire(rs.getInt("idL"), rs.getString("nomprenomL"),rs.getDate("datenaissL"), rs.getString("teleL"), rs.getString("CIN")));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cin_cin.setCellValueFactory(new PropertyValueFactory<Locataire,String>("cin"));
    	cin_date.setCellValueFactory(new PropertyValueFactory<Locataire,Date>("dateDeNaissance"));
    	cin_id.setCellValueFactory(new PropertyValueFactory<Locataire,Integer>("id"));
    	cin_nom.setCellValueFactory(new PropertyValueFactory<Locataire,String>("nomPrenom"));
    	cin_tel.setCellValueFactory(new PropertyValueFactory<Locataire,String>("tele"));
    	table_locataire.setItems(data);
    	
    }
    
    @FXML
    void tableLocataireEvent() {
    	Locataire l = table_locataire.getSelectionModel().getSelectedItem();
    	
    	String sql = "select * from locataire where idL = ?";
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setInt(1, l.getId());
    	            rs= st.executeQuery();
    	            
    	            
    	            if(rs.next()) {
    	            	txt_CIN.setText(rs.getString("CIN"));
    	            	txt_tel.setText(rs.getString("teleL"));
    	            	txt_nom.setText(rs.getString("nomprenomL"));
    	            	Date d = rs.getDate("datenaissL");
    	            	dateNai.setValue(d.toLocalDate());
    	            	txt_searchCIN.setText(rs.getString("CIN"));
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cn = ConnexionMysql.connexionDB();
			showLocataire();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
