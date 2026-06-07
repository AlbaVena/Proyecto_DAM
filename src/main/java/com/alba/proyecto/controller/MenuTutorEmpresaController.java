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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.TutorEmpresa;
import com.alba.proyecto.repositorios.FCTRepository;
import com.alba.proyecto.services.ServicioInformes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class MenuTutorEmpresaController {

	@FXML
	private BorderPane panelTutor;

	@FXML
	private Label lblRol;
	@FXML
	private Label lblNombreUsuario;
	@FXML
	private Label lblBienvenida;
	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnAyuda;

	@FXML
	private VBox panelBienvenida;
	@FXML
	private VBox panelTutorias;
	@FXML
	private VBox panelNuevaEvaluacion;
	@FXML
	private VBox panelConsultarEvaluaciones;
	@FXML
	private VBox panelRegistrarFalta;
	@FXML
	private VBox panelDocumentacion;

	@FXML
	private TableView<FCT> tablaTutorias;
	@FXML
	private TableColumn<FCT, String> colEstudianteTutoria;
	@FXML
	private TableColumn<FCT, String> colCursoTutoria;
	@FXML
	private TableColumn<FCT, String> colPeriodoTutoria;
	@FXML
	private TableColumn<FCT, String> colFechaInicioTutoria;
	@FXML
	private TableColumn<FCT, String> colFechaFinTutoria;

	@Autowired
	private FCTRepository fctRepository;

	@Autowired
	private ServicioInformes servicioInformes;

	@Autowired
	private Sesion sesion;

	@Autowired
	private ConfigurableApplicationContext context;

	@FXML
	public void initialize() {
		cargarSesion();
		configurarLogout();
		ocultarTodo();
		abrirAtajoAyuda();
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

	@FXML
	private void abrirTutorias() {
	    // configurar columnas
	    colEstudianteTutoria.setCellValueFactory(data ->
	        new javafx.beans.property.SimpleStringProperty(
	            data.getValue().getEstudiante().getNombreCompleto()));
	    colCursoTutoria.setCellValueFactory(data ->
	        new javafx.beans.property.SimpleStringProperty(
	            data.getValue().getEstudiante().getCurso() != null
	                ? data.getValue().getEstudiante().getCurso().toString() : "—"));
	    colPeriodoTutoria.setCellValueFactory(data ->
	        new javafx.beans.property.SimpleStringProperty(
	            data.getValue().getPeriodo().toString()));
	    colFechaInicioTutoria.setCellValueFactory(data ->
	        new javafx.beans.property.SimpleStringProperty(
	            data.getValue().getFechaInicio() != null
	                ? data.getValue().getFechaInicio().toString() : "—"));
	    colFechaFinTutoria.setCellValueFactory(data ->
	        new javafx.beans.property.SimpleStringProperty(
	            data.getValue().getFechaFin() != null
	                ? data.getValue().getFechaFin().toString() : "—"));

	    // cargar solo las FEs del tutor logueado
	    TutorEmpresa tutorActual = (TutorEmpresa) sesion.getUsuarioActual();
	    List<FCT> todasFEs = fctRepository.findAll();
	    ObservableList<FCT> misFEs = FXCollections.observableArrayList();
	    for (FCT fct : todasFEs) {
	        if (fct.getTutor() != null && fct.getTutor().getId().equals(tutorActual.getId())) {
	            misFEs.add(fct);
	        }
	    }
	    tablaTutorias.setItems(misFEs);
	    mostrarPanel(panelTutorias);
	}

	@FXML
	private void abrirNuevaEvaluacion() {
		mostrarPanel(panelNuevaEvaluacion);
	}

	@FXML
	private void abrirConsultarEvaluaciones() {
		mostrarPanel(panelConsultarEvaluaciones);
	}

	@FXML
	private void abrirRegistrarFalta() {
		mostrarPanel(panelRegistrarFalta);
	}

	@FXML
	private void abrirDocumentacion() {
		mostrarPanel(panelDocumentacion);
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
		panelTutor.sceneProperty().addListener((obs, escenaAnterior, escenaNueva) -> {
			if (escenaNueva != null) {
				escenaNueva.setOnKeyPressed(evento -> {
					if (evento.getCode() == KeyCode.F1) {
						abrirAyuda();
					}
				});
			}
		});
	}
	
	@FXML
	private void generarListadoFEs() {
	    TutorEmpresa tutorActual = (TutorEmpresa) sesion.getUsuarioActual();
	    List<FCT> todasFEs = fctRepository.findAll();
	    List<FCT> misFEs = new ArrayList<FCT>();
	    for (FCT fct : todasFEs) {
	        if (fct.getTutor() != null && fct.getTutor().getId().equals(tutorActual.getId())) {
	            misFEs.add(fct);
	        }
	    }

	    if (misFEs.isEmpty()) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Sin datos");
	        alert.setHeaderText(null);
	        alert.setContentText("No tienes FEs asignadas. No hay nada que exportar.");
	        alert.showAndWait();
	        return;
	    }

	    String ruta = servicioInformes.generarListadoFEs(misFEs);
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Informe generado");
	    alert.setHeaderText(null);
	    if (ruta != null) {
	        alert.setContentText("Listado generado correctamente en:\n" + ruta);
	    } else {
	        alert.setAlertType(Alert.AlertType.ERROR);
	        alert.setContentText("Error al generar el listado. Comprueba la consola.");
	    }
	    alert.showAndWait();
	}

}