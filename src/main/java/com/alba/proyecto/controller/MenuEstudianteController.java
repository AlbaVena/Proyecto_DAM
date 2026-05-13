package com.alba.proyecto.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.services.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MenuEstudianteController {
	
	@FXML private BorderPane panelEstudiante;

    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnLogOut;

    @FXML private VBox panelMisDatos;
    @FXML private VBox panelMisFCT;
    @FXML private VBox panelAsistencia;
    @FXML private VBox panelDocumentacion;
    
    @FXML private VBox contenedorCalendario;
    
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidos;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfNSS;
    @FXML private TextField tfCurso;
    @FXML private TextField tfUsuario;
    @FXML private TextField tfContrasena;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditarEmail;
    @FXML private Button btnEditarTelefono;
    
    @FXML private Button btnAyuda;
    
    @FXML private Label lblNombreEstudiante;
    @FXML private Label lblCursoEstudiante;
    @FXML private Label lblNombreEmpresa;
    @FXML private Label lblDireccionEmpresa;
    @FXML private Label lblNombreTutor;
    @FXML private Label lblEmailTutor;
    @FXML private Label lblFechaInicio;
    @FXML private Label lblFechaFin;
    
    @FXML private TableView<?> tablaDocumentos;
    @FXML private TableColumn<?, ?> colNombreDoc;
    @FXML private TableColumn<?, ?> colTipoDoc;
    @FXML private TableColumn<?, ?> colModificadoDoc;

    @Autowired
    private Sesion sesion;

    @Autowired
    private ConfigurableApplicationContext context;
    
    

    @FXML
    public void initialize() {
        cargarSesion();
        cargarDatosEstudiante();
        configurarLogout();
        ocultarTodo();
        // mostramos asistencia por defecto
        mostrarPanel(panelAsistencia);
        com.calendarfx.view.MonthView calendario = new com.calendarfx.view.MonthView();
        contenedorCalendario.getChildren().add(calendario);
        
        abrirAtajoAyuda();
    }

    private void cargarDatosEstudiante() {
    Persona p = sesion.getUsuarioActual();
    
    // comprobamos que es un estudiante antes de hacer cast
    if (p instanceof Estudiante) {
        Estudiante e = (Estudiante) p;
        
        // panel Mis Datos
        tfNombre.setText(e.getNombre());
        tfApellidos.setText(e.getApellidos());
        tfEmail.setText(e.getEmail());
        tfTelefono.setText(e.getTelefono());
        tfNSS.setText(e.getnSS());
        tfUsuario.setText(e.getUsuario());
        tfContrasena.setText("••••••••");
        
        // curso: comprobamos que no sea null antes de llamar a toString
        if (e.getCurso() != null) {
            tfCurso.setText(e.getCurso().toString());
        } else {
            tfCurso.setText("-");
        }
        
        // panel Mi FCT: datos del estudiante
        lblNombreEstudiante.setText(e.getNombre() + " " + e.getApellidos());
        lblCursoEstudiante.setText(e.getCurso() != null ? e.getCurso().toString() : "-");
        
     // FCT: cogemos la primera si existe
        FCT fctActual = null;
        if (e.getFcts() != null && !e.getFcts().isEmpty()) {
            fctActual = e.getFcts().iterator().next();
        }

        if (fctActual != null) {
            if (fctActual.getTutor() != null) {
                lblNombreTutor.setText(fctActual.getTutor().getNombre() + " " + fctActual.getTutor().getApellidos());
                lblEmailTutor.setText(fctActual.getTutor().getEmail());
            } else {
                lblNombreTutor.setText("-");
                lblEmailTutor.setText("-");
            }
            if (fctActual.getTutor() != null && fctActual.getTutor().getEmpresa() != null) {
                lblNombreEmpresa.setText(fctActual.getTutor().getEmpresa().getNombre());
                lblDireccionEmpresa.setText(fctActual.getTutor().getEmpresa().getDireccion());
            } else {
                lblNombreEmpresa.setText("-");
                lblDireccionEmpresa.setText("-");
            }
            lblFechaInicio.setText(fctActual.getFechaInicio() != null
                ? fctActual.getFechaInicio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "-");
            lblFechaFin.setText(fctActual.getFechaFin() != null
                ? fctActual.getFechaFin().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "-");
        } else {
            lblNombreEmpresa.setText("-");
            lblDireccionEmpresa.setText("-");
            lblNombreTutor.setText("-");
            lblEmailTutor.setText("-");
            lblFechaInicio.setText("-");
            lblFechaFin.setText("-");
        }
    }
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
    
    @FXML
    private void editarEmail() {
        tfEmail.setDisable(false);
        tfEmail.requestFocus();
        btnGuardar.setDisable(false);
    }

    @FXML
    private void editarTelefono() {
        tfTelefono.setDisable(false);
        tfTelefono.requestFocus();
        btnGuardar.setDisable(false);
    }
    
    @FXML
    private void abrirAyuda() {
        try {
            
            String url = getClass().getResource("/ayuda/help.html").toExternalForm();

            WebView webView = new WebView();
            webView.getEngine().load(url);

            Stage ventanaAyuda = new Stage();
            ventanaAyuda.setTitle("Ayuda - Gestiona");

            Scene escenaAyuda = new Scene(webView, 700, 500);
            ventanaAyuda.setScene(escenaAyuda);

            
            ventanaAyuda.initModality(Modality.APPLICATION_MODAL);
            ventanaAyuda.setResizable(true);
            ventanaAyuda.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Archivo de ayuda no encontrado");
            alert.setContentText("No se pudo cargar el archivo de ayuda.");
            alert.showAndWait();
        }
    }
    
    private void abrirAtajoAyuda() {
    	panelEstudiante.sceneProperty().addListener((obs, escenaAnterior, escenaNueva) -> {
    	    if (escenaNueva != null) {
    	        escenaNueva.setOnKeyPressed(evento -> {
    	            if (evento.getCode() == KeyCode.F1) {
    	                abrirAyuda();
    	            }
    	        });
    	    }
    	});
    }
}