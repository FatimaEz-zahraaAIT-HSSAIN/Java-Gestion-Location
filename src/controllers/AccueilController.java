package controllers;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AccueilController implements Initializable {
	
	Connection cn;
	public PreparedStatement st;
	public ResultSet rs;
	
    @FXML
    private ImageView imageLog;

    @FXML
    private Label lab_nbr;

    @FXML
    private Button precedant;

    @FXML
    private Button suivant;

    @FXML
    private TextField txt_loyer;

    @FXML
    private TextField txt_region;

    @FXML
    private TextField txt_sup;
    
    @FXML
    private TextField txt_adr;

    @FXML
    void showPrecedant() {
    	String adr = txt_adr.getText();
    	String sql3 = "select idLogement from logement where adrL= ?";
    	int position=0;
    	try (
			PreparedStatement st = cn.prepareStatement(sql3)){
				st.setString(1, adr);
	            rs= st.executeQuery();
	            
	            
	            if(rs.next()) {
	            	position = rs.getInt("idLogement");
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	String sql4 = "select loyer, superficie, nomRegion, adrL, image from logement, region where region.idRegion=logement.region and idLogement not in(select logement from location) and idLogement < ?";
    	int loyer=0;
		int sup=0;
		byte byteImage[];
		Blob blob;
		
    	try (
    			PreparedStatement st = cn.prepareStatement(sql4)){
    				st.setInt(1, position);
    	            rs= st.executeQuery();
    	            
    	            if(rs.next()) {
    	            	loyer = rs.getInt("loyer");
    					txt_loyer.setText(Integer.toString(loyer));
    					sup = rs.getInt("superficie");
    					txt_sup.setText(Integer.toString(sup));
    					txt_region.setText(rs.getString("nomRegion"));
    					txt_adr.setText(rs.getString("adrL"));
    					blob = rs.getBlob("image");
    					byteImage= blob.getBytes(1, (int) blob.length());
    					Image img = new Image(new ByteArrayInputStream(byteImage),imageLog.getFitHeight(),imageLog.getFitWidth(),true,true);
    					imageLog.setImage(img);
    	            }
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }

    @FXML
    void showSuivant() {
    	String adr = txt_adr.getText();
    	String sql3 = "select idLogement from logement where adrL= ?";
    	int position=0;
    	try (
			PreparedStatement st = cn.prepareStatement(sql3)){
				st.setString(1, adr);
	            rs= st.executeQuery();
	            
	            
	            if(rs.next()) {
	            	position = rs.getInt("idLogement");
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	String sql4 = "select loyer, superficie, nomRegion, adrL, image from logement, region where region.idRegion=logement.region and idLogement not in(select logement from location) and idLogement > ?";
    	int loyer=0;
		int sup=0;
		byte byteImage[];
		Blob blob;
		
    	try (
    			PreparedStatement st = cn.prepareStatement(sql4)){
    				st.setInt(1, position);
    	            rs= st.executeQuery();
    	            
    	            if(rs.next()) {
    	            	loyer = rs.getInt("loyer");
    					txt_loyer.setText(Integer.toString(loyer));
    					sup = rs.getInt("superficie");
    					txt_sup.setText(Integer.toString(sup));
    					txt_region.setText(rs.getString("nomRegion"));
    					txt_adr.setText(rs.getString("adrL"));
    					blob = rs.getBlob("image");
    					byteImage= blob.getBytes(1, (int) blob.length());
    					Image img = new Image(new ByteArrayInputStream(byteImage),imageLog.getFitHeight(),imageLog.getFitWidth(),true,true);
    					imageLog.setImage(img);
    	            }
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    
    public void showLogement() {
    	String sql = "select count(*) from logement where idLogement not in(select logement from location)";
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			int i = 0;
			
			if(rs.next()) {
				i= rs.getInt(1);
			}
			
			lab_nbr.setText(Integer.toString(i));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql2 = "select loyer, superficie, nomRegion,adrL, image from logement, region where region.idRegion=logement.region and idLogement not in(select logement from location)";
    	try {
			st = cn.prepareStatement(sql2);
			rs = st.executeQuery();
			int loyer=0;
			int sup=0;
			byte byteImage[];
			Blob blob;
			
			if(rs.next()) {
				loyer = rs.getInt("loyer");
				txt_loyer.setText(Integer.toString(loyer));
				sup = rs.getInt("superficie");
				txt_sup.setText(Integer.toString(sup));
				txt_region.setText(rs.getString("nomRegion"));
				txt_adr.setText(rs.getString("adrL"));
				blob = rs.getBlob("image");
				byteImage= blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImage),imageLog.getFitHeight(),imageLog.getFitWidth(),true,true);
				imageLog.setImage(img);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cn = ConnexionMysql.connexionDB();
			showLogement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
