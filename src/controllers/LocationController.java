package controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LocationController implements Initializable {
	
	Connection cn;
	public PreparedStatement st;
	public ResultSet rs;
	
	@SuppressWarnings("exports")
	public Boolean isBetween(Date my_date, Date my_debut, Date my_fin) {
		return (my_date.equals(my_debut)) || my_date.after(my_debut) && my_date.equals(my_fin) || my_date.before(my_fin);
		
	}
	
	@SuppressWarnings("exports")
	public Boolean isOut(Date dateDebut,Date dateFin, Date my_debut, Date my_fin) {
		return (dateFin.after(my_fin) && dateDebut.before(my_debut));
		
	}
	
	@FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private ImageView img_logement;

    @FXML
    private TextField txt_adr;

    @FXML
    private TextField txt_cin;

    @FXML
    private TextField txt_loyer;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_period;

    @FXML
    private TextField txt_reg;

    @FXML
    private TextField txt_searchIdLocataire;

    @FXML
    private TextField txt_searchIdLogement;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_type;

    @FXML
    void addLocation() {
    	
    	String sql = "select idL from locataire where CIN = ?";
    	int loc=0;
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, txt_cin.getText());
    	            rs= st.executeQuery();
    	            
    	            
    	            if(rs.next()) {
    	            	loc = rs.getInt("idL");
    	            	
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	

    	String sql1 = "select idLogement from logement where adrL = ?";
    	int log=0;
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql1)){
    				st.setString(1, txt_adr.getText());
    	            rs= st.executeQuery();
    	            
    	            
    	            if(rs.next()) {
    	            	log = rs.getInt("idLogement");
    	            	
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	java.util.Date datedd = Date.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	Date dateDebut = new Date(datedd.getTime());
    	java.util.Date dateff = Date.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	Date dateFin = new Date(dateff.getTime());
    	
    	String sql2 ="select dateDebut, dateFin from location where logement = ?";
    	Boolean debut= false;
    	Boolean fin=false;
    	Date dated= null;
    	Date datef= null;
    	java.util.Date d=null;
    	java.util.Date f =null;
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql2)){
    				st.setInt(1, log);
    	            rs= st.executeQuery();
    	            
    	            while(rs.next()) {
    	            	dated=rs.getDate("dateDebut");
    	            	datef=rs.getDate("dateFin");
    	            	
    	            	if(isBetween(dateFin, dated, datef) == true) {
    	            		fin=true;
    	            	}
    	            	
    	            	if(isBetween(dateDebut, dated, datef) == true) {
    	            		debut=true;
    	            	}
    	            	
    	            	if(isOut(dateDebut,dateFin, dated, datef)==true) {
    	            		 fin=true;
    	            		 debut=true;
    	            	}
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	if(fin==true || debut==true) {
    		Alert a = new Alert(AlertType.WARNING, "Ce logement est occupe pendant la period specifie",ButtonType.OK);
    		a.showAndWait();
    	}else {
    		String sql0 = "insert into location(logement, locataire,dateDebut,dateFin) value(?,?,?,?)";
    		
    		try (
        			PreparedStatement st = cn.prepareStatement(sql0)){
        				st.setInt(1, log);
        				st.setInt(2, loc);
        				st.setDate(3, dateDebut);
        				st.setDate(4, dateFin);
        				st.executeUpdate();
        				
        	            txt_adr.setText("");
        	            txt_cin.setText("");
        	            txt_loyer.setText("");
        	            txt_nom.setText("");
        	            txt_reg.setText("");
        	            txt_tel.setText("");
        	            txt_type.setText("");
        	            this.dateDebut.setValue(null);
        	            this.dateFin.setValue(null);
        	            
        	            Alert a = new Alert(AlertType.CONFIRMATION, "Logement ajoutee",ButtonType.OK);
        	    		a.showAndWait();
        	            
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
    	}

    }

    @FXML
    void searchLocataire() {
    	String sql = "select nomprenomL, teleL, CIN from locataire where CIN = ?";
    	int nbr =0;
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, txt_searchIdLocataire.getText());
    	            rs= st.executeQuery();
    	            
    	            
    	            if(rs.next()) {
    	            	txt_cin.setText(rs.getString("CIN"));
    	            	txt_nom.setText(rs.getString("nomprenomL"));
    	            	txt_tel.setText(rs.getString("teleL"));
    	            	txt_searchIdLocataire.setText("");
    	            	nbr=1;
    	            	
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	if(nbr==0) {
    		Alert a = new Alert(AlertType.ERROR, "Aucun locataire trouve avec CIN: " + txt_searchIdLocataire.getText(),ButtonType.OK);
    		a.showAndWait();
    	}
    }

    @FXML
    void searchLogement() {
    	String sql = "select adrL, loyer, nomType, nomRegion, image from logement,type,region where logement.type=type.idType and logement.region=region.idRegion and logement.idLogement = ?";
    	int nb =0;
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, txt_searchIdLogement.getText());
    	            rs= st.executeQuery();
    	            byte ByteImg[];
    	            Blob blob;
    	            
    	            if(rs.next()) {
    	            	txt_adr.setText(rs.getString("adrL"));
    	            	int loyer = rs.getInt("loyer");
    	            	txt_loyer.setText(String.valueOf(loyer));
    	            	txt_type.setText(rs.getString("nomType"));
    	            	txt_reg.setText(rs.getString("nomRegion"));
    	            	blob = rs.getBlob("image");
    	            	ByteImg = blob.getBytes(1, (int) blob.length());
    	            	Image img = new Image(new ByteArrayInputStream(ByteImg),img_logement.getFitHeight(),img_logement.getFitWidth(),true,true);
    	            	img_logement.setImage(img);
    	            	txt_searchIdLogement.setText("");
    	            	nb=1;
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	if(nb==0) {
    		Alert a = new Alert(AlertType.ERROR, "Aucun logement trouve avec id: " + txt_searchIdLogement.getText(),ButtonType.OK);
    		a.showAndWait();
    	}
    }
    
    @FXML
    void period() {

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
