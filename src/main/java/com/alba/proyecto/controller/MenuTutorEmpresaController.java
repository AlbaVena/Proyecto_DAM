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
public class MenuTutorEmpresaController {

    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Label lblBienvenida;
    @FXML private Button btnLogOut;

    @FXML private VBox panelBienvenida;
    @FXML private VBox panelTutorias;
    @FXML private VBox panelNuevaEvaluacion;
    @FXML private VBox panelConsultarEvaluaciones;
    @FXML private VBox panelRegistrarFalta;
    @FXML private VBox panelDocumentacion;

    @Autowired
    private Sesion sesion;

    @Autowired
    private ConfigurableApplicationContext context;

    @FXML
    public void initialize() {
        cargarSesion();
        configurarLogout();
        ocultarTodo();
        panelBienvenida.setVisible(true);
        panelBienvenida.setManaged(true);
    }

    private void ocultarTodo() {
        panelBienvenida.setVisible(false);
        panelBienvenida.setManaged(false);
        panelTutorias.setVisible(false);
        panelTutorias.setManaged(false);
        panelNuevaEvaluacion.setVisible(false);
        panelNuevaEvaluacion.setManaged(false);
        panelConsultarEvaluaciones.setVisible(false);
        panelConsultarEvaluaciones.setManaged(false);
        panelRegistrarFalta.setVisible(false);
        panelRegistrarFalta.setManaged(false);
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
            lblBienvenida.setText("Bienvenido/a, " + p.getNombre());
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

    @FXML private void abrirTutorias() { mostrarPanel(panelTutorias); }
    @FXML private void abrirNuevaEvaluacion() { mostrarPanel(panelNuevaEvaluacion); }
    @FXML private void abrirConsultarEvaluaciones() { mostrarPanel(panelConsultarEvaluaciones); }
    @FXML private void abrirRegistrarFalta() { mostrarPanel(panelRegistrarFalta); }
    @FXML private void abrirDocumentacion() { mostrarPanel(panelDocumentacion); }
}