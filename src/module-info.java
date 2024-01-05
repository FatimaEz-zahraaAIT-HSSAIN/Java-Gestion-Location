module Location {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	exports controllers;
	opens models;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers;
}
