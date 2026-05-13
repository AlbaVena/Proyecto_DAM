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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;


@Controller
public class MenuProfesorController {
	
	@Autowired
	private Sesion sesion;
	
	@Autowired
	private ConfigurableApplicationContext context;
	
	@FXML private BorderPane PanelProfesor;

    // --- Paneles del StackPane ---
    @FXML private VBox panelNuevaFCT;
    @FXML private VBox panelTablaFCT;
    @FXML private VBox panelEliminarFCT;
    @FXML private VBox panelNuevaEvaluacion;
    @FXML private VBox panelConsultaEvaluacion;
   
    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnLogOut;
    
    @FXML private Button btnAyuda;
    
    //para crear FCT
    @FXML private ComboBox<?> cbEstudiantes;
    @FXML private ComboBox<?> cbEmpresas;
    @FXML private ComboBox<?> cbTutores;
    @FXML private ComboBox<?> cbPeriodo;
    @FXML private ComboBox<?> cbTipoDocumento;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private TextArea taAnotaciones;
    @FXML private Button btnAdjuntarArchivos;
    @FXML private Button btnLimpiarFCT;
    @FXML private Button btnGuardarFCT;
    
    //para modificar FCT
    @FXML private TextField tfBuscarEstudiante;
    @FXML private TextField tfBuscarEmpresaFCT;
    @FXML private Button btnBuscarFCT;
    @FXML private TableView<?> tablaFCTs;
    @FXML private TableColumn<?, ?> colEstudianteFCT;
    @FXML private TableColumn<?, ?> colEmpresaFCT;
    @FXML private TableColumn<?, ?> colTutorFCT;
    @FXML private TableColumn<?, ?> colPeriodoFCT;
    @FXML private TableColumn<?, ?> colFechaInicioFCT;
    @FXML private TableColumn<?, ?> colFechaFinFCT;
    @FXML private Button btnModificarFCTSeleccionada;
    @FXML private VBox subpanelModificarFCT;
    @FXML private TextField tfEstudianteMod;
    @FXML private ComboBox<?> cbEmpresasMod;
    @FXML private ComboBox<?> cbPeriodoMod;
    @FXML private ComboBox<?> cbTutoresMod;
    @FXML private DatePicker dpFechaInicioMod;
    @FXML private DatePicker dpFechaFinMod;
    @FXML private Button btnCancelarModFCT;
    @FXML private Button btnGuardarModFCT;
    

    @FXML
    public void initialize() {
    	cargarSesion();
    	ocultarTodo();
        cerrarSesion();
        abrirAtajoAyuda();
        subpanelModificarFCT.setVisible(false);
        subpanelModificarFCT.setManaged(false);
        
        btnModificarFCTSeleccionada.disableProperty().bind(tablaFCTs.getSelectionModel().selectedItemProperty().isNull());

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
            lblRol.setText(p.getPerfil().toString());
            lblNombreUsuario.setText(p.getNombre() + " " + p.getApellidos());
        }
    }
    
    private void cerrarSesion() {
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
    	PanelProfesor.sceneProperty().addListener((obs, escenaAnterior, escenaNueva) -> {
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
    private void buscarFCT() {
    	//TODO
        // por ahora solo muestra el subpanel vacio
        // la logica de filtrado se implementara cuando haya datos
    }

    @FXML
    private void modificarFCTSeleccionada() {
        subpanelModificarFCT.setVisible(true);
        subpanelModificarFCT.setManaged(true);
        //TODO por ahora los campos quedan vacios hasta implementar la carga de datos
    }

    @FXML
    private void cancelarModFCT() {
        // ocultamos el subpanel y limpiar campos
        subpanelModificarFCT.setVisible(false);
        subpanelModificarFCT.setManaged(false);
        tfEstudianteMod.clear();
        cbEmpresasMod.getSelectionModel().clearSelection();
        cbPeriodoMod.getSelectionModel().clearSelection();
        cbTutoresMod.getSelectionModel().clearSelection();
        dpFechaInicioMod.setValue(null);
        dpFechaFinMod.setValue(null);
    }

    @FXML
    private void guardarModFCT() {
        // por ahora solo muestra el mensaje de exito
        // la logica de guardado se implementara despues
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operación exitosa");
        alert.setHeaderText(null);
        alert.setContentText("La FCT se ha guardado correctamente.");
        alert.showAndWait();
        cancelarModFCT();
    }
    
    
    
    
}
