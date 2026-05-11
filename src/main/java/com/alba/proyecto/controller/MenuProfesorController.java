package com.alba.proyecto.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.services.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


@Controller
public class MenuProfesorController {
	
	@Autowired
	private Sesion sesion;
	
	@Autowired
	private ConfigurableApplicationContext context;

    // --- Paneles del StackPane ---
    @FXML private VBox panelNuevaFCT;
    @FXML private VBox panelTablaFCT;
    @FXML private VBox panelEliminarFCT;
    @FXML private VBox panelNuevaEvaluacion;
    @FXML private VBox panelConsultaEvaluacion;
    
    @FXML private BarraSuperiorController barraSuperior;
    
    

    @FXML
    public void initialize() {
    	cargarSesion();
    	ocultarTodo();
        cerrarSesion();

    }

    private void ocultarTodo() {
        panelNuevaFCT.setVisible(false);
        panelNuevaFCT.setManaged(false);
        panelTablaFCT.setVisible(false);
        panelTablaFCT.setManaged(false);
        panelEliminarFCT.setVisible(false);
        panelEliminarFCT.setManaged(false);
        panelNuevaEvaluacion.setVisible(false);
        panelNuevaEvaluacion.setManaged(false);
        panelConsultaEvaluacion.setVisible(false);
        panelConsultaEvaluacion.setManaged(false);
    }
    
    private void cargarSesion() {
    
    	Persona p = sesion.getUsuarioActual();
        if (p != null) {
            barraSuperior.setDatosUsuario(
                p.getPerfil().toString(),
                p.getNombre() + " " + p.getApellidos()
            );
        }
    }
    
    private void cerrarSesion() {
    	barraSuperior.getBtnLogOut().setOnAction(event -> {
    	    try {
    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    	        loader.setControllerFactory(context::getBean);
    	        Parent root = loader.load();
    	        Stage stage = (Stage) barraSuperior.getScene().getWindow();
    	        stage.setScene(new Scene(root));
    	        stage.show();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	});
    }

    private void mostrarPanel(VBox panel) {
        ocultarTodo();
        panel.setVisible(true);
        panel.setManaged(true);
    }

    @FXML
    private void abrirNuevaFCT() {
        mostrarPanel(panelNuevaFCT);
    }

    @FXML
    private void abrirModificarFCT() {
        mostrarPanel(panelTablaFCT);
    }

    @FXML
    private void abrirEliminarFCT() {
        mostrarPanel(panelEliminarFCT);
    }

    @FXML
    private void abrirNuevaEvaluacion() {
        mostrarPanel(panelNuevaEvaluacion);
    }

    @FXML
    private void abrirConsultaEvaluacion() {
        mostrarPanel(panelConsultaEvaluacion);
    }
}
