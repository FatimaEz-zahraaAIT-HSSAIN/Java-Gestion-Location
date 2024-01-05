package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.Logement;



public class LogementController implements Initializable{
	
	Connection cn;
	public PreparedStatement st;
	public ResultSet rs;
	
	private FileInputStream fs;
    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_upload;

    @FXML
    private ComboBox<String> cb_com;

    @FXML
    private ComboBox<String> cb_pro;

    @FXML
    private ComboBox<String> cb_reg;

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private TableColumn<Logement, String> cln_adresse;

    @FXML
    private TableColumn<Logement, Integer> cln_id;

    @FXML
    private TableColumn<Logement, Integer> cln_loyer;

    @FXML
    private TableColumn<Logement, Integer> cln_sup;

    @FXML
    private TableColumn<Logement, String> cln_type;
    
    @FXML
    private TableColumn<Logement, String> cln_region;

    @FXML
    private ImageView img_logement;

    @FXML
    private Label lab_url;

    @FXML
    private TableView<Logement> table_logement;

    @FXML
    private TextField txt_adr;

    @FXML
    private TextField txt_loyer;

    @FXML
    private TextField txt_searchId;

    @FXML
    private TextField txt_sup;

    @FXML
    void addLogement() {
    	String adr = txt_adr.getText();
    	String sup = txt_sup.getText();
    	int supInt= Integer.parseInt(sup);
    	String loy = txt_loyer.getText();
    	int loyer = Integer.parseInt(loy);
    	
    	
    	String type = cb_type.getValue();
    	String sql1= "select idType from type where nomType = ?";
    	int typeInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql1)){
    				st.setString(1, type);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	typeInt = rs.getInt("idType");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String reg = cb_reg.getValue();
    	String sql2= "select idRegion from region where nomRegion = ?";
    	int regInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql2)){
    				st.setString(1, reg);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	regInt = rs.getInt("idRegion");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String pro = cb_pro.getValue();
    	String sql3= "select idProvince from province where nomProvince = ?";
    	int proInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql3)){
    				st.setString(1, pro);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	proInt = rs.getInt("idProvince");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String com = cb_com.getValue();
    	String sql4= "select idCommune from commune where nomCommune = ?";
    	int comInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql4)){
    				st.setString(1, com);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	comInt = rs.getInt("idCommune");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	File image = new File(lab_url.getText());
    	
    	String sql = "insert into logement(adrL, superficie, loyer, type, region, province, commune, image) value(?,?,?,?,?,?,?,?)";
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, adr);
    				st.setInt(2, supInt);
    				st.setInt(3, loyer);
    				st.setInt(4, typeInt);
    				st.setInt(5, regInt);
    				st.setInt(6, proInt);
    				st.setInt(7, comInt);
    				
    				fs = new FileInputStream(image);
    				st.setBinaryStream(8, fs, image.length());
    				st.executeUpdate();
    				showLogement();
    				lab_url.setText("aucune selectionee");
    				txt_adr.setText("");
    				txt_loyer.setText("");
    				txt_searchId.setText("");
    				txt_sup.setText("");
    				
    				cb_com.setValue("commune");
    				cb_pro.setValue("province");
    				cb_type.setValue("type");
    				cb_reg.setValue("region");
    				
    				img_logement.setImage(null);
    				Alert a = new Alert(AlertType.CONFIRMATION, "Logement ajoutee", ButtonType.OK);
    				a.showAndWait();
    				
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	
    }

    @FXML
    void deleteLogement() {
    	
    	String sql = "delete from logement where idLogement = ?";
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, txt_searchId.getText());
    	            st.execute();
    	            
    	            showLogement();
    				lab_url.setText("aucune selectionee");
    				txt_adr.setText("");
    				txt_loyer.setText("");
    				txt_searchId.setText("");
    				txt_sup.setText("");
    				
    				cb_com.setValue("commune");
    				cb_pro.setValue("province");
    				cb_type.setValue("type");
    				cb_reg.setValue("region");
    				
    				img_logement.setImage(null);
    				Alert a = new Alert(AlertType.CONFIRMATION, "Logement supprimer", ButtonType.OK);
    				a.showAndWait();
    	            
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

    }

    @FXML
    void editLogement() {
    	String adr = txt_adr.getText();
    	String sup = txt_sup.getText();
    	int supInt= Integer.parseInt(sup);
    	String loy = txt_loyer.getText();
    	int loyer = Integer.parseInt(loy);
    	
    	
    	String type = cb_type.getValue();
    	String sql1= "select idType from type where nomType = ?";
    	int typeInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql1)){
    				st.setString(1, type);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	typeInt = rs.getInt("idType");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String reg = cb_reg.getValue();
    	String sql2= "select idRegion from region where nomRegion = ?";
    	int regInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql2)){
    				st.setString(1, reg);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	regInt = rs.getInt("idRegion");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String pro = cb_pro.getValue();
    	String sql3= "select idProvince from province where nomProvince = ?";
    	int proInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql3)){
    				st.setString(1, pro);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	proInt = rs.getInt("idProvince");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	String com = cb_com.getValue();
    	String sql4= "select idCommune from commune where nomCommune = ?";
    	int comInt = 0;
    	

    	try (
    			PreparedStatement st = cn.prepareStatement(sql4)){
    				st.setString(1, com);
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	comInt = rs.getInt("idCommune");
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	File image = new File(lab_url.getText());
    	
    	String sql = "update logement set adrL= ?, superficie= ?, loyer = ?, type = ?, region = ?, province = ?, commune = ?, image = ? where idLogement = ?";
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, adr);
    				st.setInt(2, supInt);
    				st.setInt(3, loyer);
    				st.setInt(4, typeInt);
    				st.setInt(5, regInt);
    				st.setInt(6, proInt);
    				st.setInt(7, comInt);
    				
    				fs = new FileInputStream(image);
    				st.setBinaryStream(8, fs, image.length());
    				st.setString(9, txt_searchId.getText());
    				st.executeUpdate();
    				showLogement();
    				lab_url.setText("aucune selectionee");
    				txt_adr.setText("");
    				txt_loyer.setText("");
    				txt_searchId.setText("");
    				txt_sup.setText("");
    				
    				cb_com.setValue("commune");
    				cb_pro.setValue("province");
    				cb_type.setValue("type");
    				cb_reg.setValue("region");
    				
    				img_logement.setImage(null);
    				Alert a = new Alert(AlertType.CONFIRMATION, "Logement modifie", ButtonType.OK);
    				a.showAndWait();
    				
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	

    }

    @FXML
    void importerImage() {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("image Files", "*.png", "*.jpg", "*.jpeg"));
    	File f = fc.showOpenDialog(null);
    	if(f != null) {
    		lab_url.setText(f.getAbsolutePath());
    		Image img = new Image(f.toURI().toString(),img_logement.getFitHeight(),img_logement.getFitWidth(),true,true);
    		img_logement.setImage(img);
    	}

    }

    @FXML
    void remplirCommune() {
    	cb_com.getItems().clear();
    	String sql = "select nomCommune from commune where province = (select idProvince from province where nomProvince = ? )";
    	List<String> coms = new ArrayList<String>();
    	
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, cb_pro.getValue());
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	coms.add(rs.getString("nomCommune"));
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	cb_com.setItems(FXCollections.observableArrayList(coms));
    }

    @FXML
    void remplirProvince() {
    	cb_pro.getItems().clear();
    	String sql = "select nomProvince from province where region = (select idRegion from region where nomRegion = ? )";
    	List<String> provinces = new ArrayList<String>();
    	
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql)){
    				st.setString(1, cb_reg.getValue());
    	            rs= st.executeQuery();
    	            
    	            
    	            while(rs.next()) {
    	            	provinces.add(rs.getString("nomProvince"));
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	
    	cb_pro.setItems(FXCollections.observableArrayList(provinces));
    }

    @FXML
    void searchLogement() {
    	
    	//remp cb pro
    	String sql = "select nomProvince from province";
    	List<String> provinces = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				provinces.add(rs.getString("nomProvince"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_pro.setItems(FXCollections.observableArrayList(provinces));
    	
    	//remp cb com
    	String sql2 = "select nomCommune from commune";
    	List<String> coms = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql2);
			rs = st.executeQuery();
			
			while(rs.next()) {
				coms.add(rs.getString("nomCommune"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_com.setItems(FXCollections.observableArrayList(coms));
    	
    	
    	
    	String sql3 = "select idLogement, adrL, superficie, loyer, nomType, nomRegion, nomProvince, nomCommune, image from logement,type,region,province,commune where logement.type = type.idType and logement.region = region.idRegion and logement.commune = commune.idCommune and logement.province = province.idProvince and idLogement = ?";
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql3)){
    				st.setString(1, txt_searchId.getText());
    	            rs= st.executeQuery();
    	            
    	            byte byteImg[];
    	            Blob blob;
    	            while(rs.next()) {
    	            	int id = rs.getInt("idLogement");
    	            	txt_searchId.setText(String.valueOf(id));
    	            	txt_adr.setText(rs.getString("adrL"));
    	            	int sup= rs.getInt("superficie");
    	            	txt_sup.setText(String.valueOf(sup));
    	            	int l = rs.getInt("loyer");
    	            	txt_loyer.setText(String.valueOf(l));
    	            	cb_type.setValue(rs.getString("nomType"));
    	            	cb_reg.setValue(rs.getString("nomRegion"));
    	            	cb_pro.setValue(rs.getString("nomProvince"));
    	            	cb_com.setValue(rs.getString("nomCommune"));
    	            	blob = rs.getBlob("image");
    	            	byteImg =blob.getBytes(1, (int) blob.length());
    	            	Image img = new Image(new ByteArrayInputStream(byteImg),img_logement.getFitHeight(),img_logement.getFitWidth(),true,true);
    	            	img_logement.setImage(img);
    	            	
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

    }
    
    ObservableList<Logement> listLog = FXCollections.observableArrayList();
    public void showLogement() {
    	table_logement.getItems().clear();
    	
    	String sql = "select idLogement, adrL, superficie, loyer, nomType, nomRegion from logement,type,region where logement.region = region.idRegion and logement.type= type.idType";
    	try {
			st= cn.prepareStatement(sql);
			rs= st.executeQuery();
			
			while(rs.next()) {
				listLog.add(new Logement(rs.getInt("idLogement"), rs.getString("adrL"), rs.getInt("superficie"), rs.getInt("loyer"), rs.getString("nomType"), rs.getString("nomRegion")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	cln_id.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("id"));
    	cln_adresse.setCellValueFactory(new PropertyValueFactory<Logement,String>("adr"));
    	cln_loyer.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("loyer"));
    	cln_sup.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("superficie"));
    	cln_type.setCellValueFactory(new PropertyValueFactory<Logement,String>("type"));
    	cln_region.setCellValueFactory(new PropertyValueFactory<Logement,String>("region"));
    	
    	table_logement.setItems(listLog);
    
    }
    
    public void remplirType() {
    	String sql = "select nomType from type";
    	List<String> types = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				types.add(rs.getString("nomType"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_type.setItems(FXCollections.observableArrayList(types));
    }
    
    public void remplirRegion() {
    	String sql = "select nomRegion from region";
    	List<String> regions = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				regions.add(rs.getString("nomRegion"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_reg.setItems(FXCollections.observableArrayList(regions));
    }
    
    @FXML
    void tableLogEvent() {
    	
    	//remp cb pro
    	String sql = "select nomProvince from province";
    	List<String> provinces = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				provinces.add(rs.getString("nomProvince"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_pro.setItems(FXCollections.observableArrayList(provinces));
    	
    	//remp cb com
    	String sql2 = "select nomCommune from commune";
    	List<String> coms = new ArrayList<String>();
    	try {
			st = cn.prepareStatement(sql2);
			rs = st.executeQuery();
			
			while(rs.next()) {
				coms.add(rs.getString("nomCommune"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cb_com.setItems(FXCollections.observableArrayList(coms));
    	
    	Logement log = table_logement.getSelectionModel().getSelectedItem();
    	
    	String sql3 = "select idLogement, adrL, superficie, loyer, nomType, nomRegion, nomProvince, nomCommune, image from logement,type,region,province,commune where logement.type = type.idType and logement.region = region.idRegion and logement.commune = commune.idCommune and logement.province = province.idProvince and idLogement = ?";
    	
    	try (
    			PreparedStatement st = cn.prepareStatement(sql3)){
    				st.setInt(1, log.getId());
    	            rs= st.executeQuery();
    	            
    	            byte byteImg[];
    	            Blob blob;
    	            while(rs.next()) {
    	            	int id = rs.getInt("idLogement");
    	            	txt_searchId.setText(String.valueOf(id));
    	            	txt_adr.setText(rs.getString("adrL"));
    	            	int sup= rs.getInt("superficie");
    	            	txt_sup.setText(String.valueOf(sup));
    	            	int l = rs.getInt("loyer");
    	            	txt_loyer.setText(String.valueOf(l));
    	            	cb_type.setValue(rs.getString("nomType"));
    	            	cb_reg.setValue(rs.getString("nomRegion"));
    	            	cb_pro.setValue(rs.getString("nomProvince"));
    	            	cb_com.setValue(rs.getString("nomCommune"));
    	            	blob = rs.getBlob("image");
    	            	byteImg =blob.getBytes(1, (int) blob.length());
    	            	Image img = new Image(new ByteArrayInputStream(byteImg),img_logement.getFitHeight(),img_logement.getFitWidth(),true,true);
    	            	img_logement.setImage(img);
    	            	
    	            }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cn = ConnexionMysql.connexionDB();
			showLogement();
			remplirType();
			remplirRegion();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
