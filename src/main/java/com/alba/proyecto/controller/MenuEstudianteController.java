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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller
public class MenuEstudianteController {

    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnLogOut;

    @FXML private VBox panelMisDatos;
    @FXML private VBox panelMisFCT;
    @FXML private VBox panelAsistencia;
    @FXML private VBox panelDocumentacion;
    
    @FXML private VBox contenedorCalendario;

    @Autowired
    private Sesion sesion;

    @Autowired
    private ConfigurableApplicationContext context;
    
    

    @FXML
    public void initialize() {
        cargarSesion();
        configurarLogout();
        ocultarTodo();
        // mostramos asistencia por defecto
        mostrarPanel(panelAsistencia);
        com.calendarfx.view.MonthView calendario = new com.calendarfx.view.MonthView();
        contenedorCalendario.getChildren().add(calendario);
    }

    private void ocultarTodo() {
        panelMisDatos.setVisible(false);
        panelMisDatos.setManaged(false);
        panelMisFCT.setVisible(false);
        panelMisFCT.setManaged(false);
        panelAsistencia.setVisible(false);
        panelAsistencia.setManaged(false);
        panelDocumentacion.setVisible(false);
        panelDocumentacion.setManaged(false);
    }

    private void mostrarPanel(VBox panel) {
        ocultarTodo();
        panel.setVisible(true);
        panel.setManaged(true);
       
    }

    private void cargarSesion() {
        Persona p = sesion.getUsuarioActual();
        if (p != null) {
            lblRol.setText(p.getPerfil().toString());
            lblNombreUsuario.setText(p.getNombre() + " " + p.getApellidos());
        }
    }

    private void configurarLogout() {
        btnLogOut.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();
                Stage stage = (Stage) btnLogOut.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void abrirMisDatos() {
        mostrarPanel(panelMisDatos);
    }

    @FXML
    private void abrirMiFCT() {
        mostrarPanel(panelMisFCT);
    }

    @FXML
    private void abrirAsistencia() {
        mostrarPanel(panelAsistencia);
    }

    @FXML
    private void abrirDocumentacion() {
        mostrarPanel(panelDocumentacion);
    }
}