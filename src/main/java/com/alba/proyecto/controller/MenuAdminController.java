package com.alba.proyecto.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.services.EmpresaService;
import com.alba.proyecto.services.Sesion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller
public class MenuAdminController {

    //StackPane  
    @FXML private VBox panelCrearEmpresa;
    @FXML private VBox panelTablaEmpresas;
    @FXML private VBox panelFormModificar;

    //Panel Crear Empresa  
    @FXML private TextField tfNombreEmpresa;
    @FXML private TextField tfDireccionEmpresa;
    @FXML private TextField tfTelefonoEmpresa;
    @FXML private Button btnGuardarEmpresa;

    // Panel Tabla  
    @FXML private TextField tfBuscarEmpresa;
    @FXML private TableView<Empresa> tablaEmpresas;
    @FXML private TableColumn<Empresa, String> colNombreEmpresa;
    @FXML private TableColumn<Empresa, String> colDireccionEmpresa;
    @FXML private TableColumn<Empresa, String> colTelefonoEmpresa;

    // Panel Form Modificar  
    @FXML private Label lblTituloEmpresa;
    @FXML private TextField tfFormNombreEmpresa;
    @FXML private TextField tfFormDireccionEmpresa;
    @FXML private TextField tfFormTelefonoEmpresa;
    @FXML private Button btnEditarNombreEmpresa;
    @FXML private Button btnEditarDireccionEmpresa;
    @FXML private Button btnEditarTelefonoEmpresa;
    @FXML private Button btnGuardarFormEmpresa;
    @FXML private Button btnCancelarFormEmpresa;
    @FXML private Button btnVolverTablaEmpresa;

    //Botones menus laterales  
    @FXML private Button btnCrearEmpresaLateral;
    @FXML private Button btnModificarEmpresaLateral;
    @FXML private Button btnConsultarEmpresaLateral;
    
    @FXML private BarraSuperiorController barraSuperior;

    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
	private Sesion sesion;
    
    @Autowired
    private ConfigurableApplicationContext context;

    // lista para la tabla de empresas
    private ObservableList<Empresa> listaEmpresas = FXCollections.observableArrayList();

    //para modificar
    private Empresa empresaSeleccionada;

    //"modificar" o "consultar"
    private String modoTabla = "";

    @FXML
    public void initialize() {
    	cargarSesion();
    	cerrarSesion();
        configurarColumnas();
        configurarBuscador();
        configurarDobleClick();
        configurarBotonesEditar();
        ocultarTodo();
    }

    private void ocultarTodo() {
        panelCrearEmpresa.setVisible(false);
        panelCrearEmpresa.setManaged(false);
        panelTablaEmpresas.setVisible(false);
        panelTablaEmpresas.setManaged(false);
        panelFormModificar.setVisible(false);
        panelFormModificar.setManaged(false);
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

    //Mostrar un panel  
    private void mostrarPanel(VBox panel) {
        ocultarTodo();
        panel.setVisible(true);
        panel.setManaged(true);
    }

    // Botones laterales  
    @FXML
    private void abrirCrearEmpresa() {
        limpiarFormCrear();
        mostrarPanel(panelCrearEmpresa);
    }

    @FXML
    private void abrirModificarEmpresa() {
        modoTabla = "modificar";
        lblTituloEmpresa.setText("Modificar Empresa");
        cargarTabla();
        mostrarPanel(panelTablaEmpresas);
    }

    @FXML
    private void abrirConsultarEmpresa() {
        modoTabla = "consultar";
        lblTituloEmpresa.setText("Empresa");
        cargarTabla();
        mostrarPanel(panelTablaEmpresas);
    }

    //   Configurar columnas de la tabla  de empresa
    private void configurarColumnas() {
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    //   Cargar datos en la tabla -- primero se limpia todo y luego se añade cadau na
    private void cargarTabla() {
        List<Empresa> empresas = empresaService.findAll();
        listaEmpresas.clear();
        for (Empresa e : empresas) {
            listaEmpresas.add(e);
        }
        tablaEmpresas.setItems(listaEmpresas);
    }

    //   Buscador en tiempo real  con el textfield
    private void configurarBuscador() {
        tfBuscarEmpresa.setOnKeyReleased(event -> {
            String filtro = tfBuscarEmpresa.getText().toLowerCase();
            FilteredList<Empresa> listaFiltrada = new FilteredList<>(listaEmpresas);
            listaFiltrada.setPredicate(empresa -> {
                if (filtro.isEmpty()) {
                    return true;
                }
                if (empresa.getNombre() != null && empresa.getNombre().toLowerCase().contains(filtro)) {
                    return true;
                }
                if (empresa.getDireccion() != null && empresa.getDireccion().toLowerCase().contains(filtro)) {
                    return true;
                }
                return false;
            });
            tablaEmpresas.setItems(listaFiltrada);
        });
    }

    //   Doble click en tabla  para cargar
    private void configurarDobleClick() {
        tablaEmpresas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                empresaSeleccionada = tablaEmpresas.getSelectionModel().getSelectedItem();
                if (empresaSeleccionada != null) {
                    rellenarFormModificar();
                    configurarModoForm();
                    mostrarPanel(panelFormModificar);
                }
            }
        });
    }

    //   Rellenar formulario con datos de la empresa seleccionada  
    private void rellenarFormModificar() {
        tfFormNombreEmpresa.setText(empresaSeleccionada.getNombre());
        tfFormDireccionEmpresa.setText(empresaSeleccionada.getDireccion());
        tfFormTelefonoEmpresa.setText(empresaSeleccionada.getTelefono());
    }

    //Configurar el formulario dependiendo de donde viene  
    private void configurarModoForm() {
    	//si viene de modificar, carga el form editable
        if (modoTabla.equals("modificar")) {
            btnEditarNombreEmpresa.setVisible(true);
            btnEditarDireccionEmpresa.setVisible(true);
            btnEditarTelefonoEmpresa.setVisible(true);
            btnGuardarFormEmpresa.setVisible(true);
            btnCancelarFormEmpresa.setVisible(true);
        } else {
            // modo consultar — solo lectura
            btnEditarNombreEmpresa.setVisible(false);
            btnEditarDireccionEmpresa.setVisible(false);
            btnEditarTelefonoEmpresa.setVisible(false);
            btnGuardarFormEmpresa.setVisible(false);
            btnCancelarFormEmpresa.setVisible(false);
        }
    }

    //botones para editar campo  se ponen visibles en modo Editar nada mas
    
    private void configurarBotonesEditar() {
        btnEditarNombreEmpresa.setOnAction(event -> {
            tfFormNombreEmpresa.setEditable(true);
            tfFormNombreEmpresa.requestFocus();
        });
        btnEditarDireccionEmpresa.setOnAction(event -> {
            tfFormDireccionEmpresa.setEditable(true);
            tfFormDireccionEmpresa.requestFocus();
        });
        btnEditarTelefonoEmpresa.setOnAction(event -> {
            tfFormTelefonoEmpresa.setEditable(true);
            tfFormTelefonoEmpresa.requestFocus();
        });
    }

    //Guardar empresa nueva  
    @FXML
    private void guardarEmpresa() {
        String nombre = tfNombreEmpresa.getText();
        String direccion = tfDireccionEmpresa.getText();
        String telefono = tfTelefonoEmpresa.getText();

        if (nombre.isEmpty()) {
            tfNombreEmpresa.setStyle("-fx-border-color: red;");
            return;
        }

        tfNombreEmpresa.setStyle("");

        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);

        empresaService.save(empresa);
        limpiarFormCrear();
        mostrarPanel(panelTablaEmpresas);
        modoTabla = "modificar";
        cargarTabla();
    }

    //Guardar modificación  
    @FXML
    private void guardarFormEmpresa() {
        empresaSeleccionada.setNombre(tfFormNombreEmpresa.getText());
        empresaSeleccionada.setDireccion(tfFormDireccionEmpresa.getText());
        empresaSeleccionada.setTelefono(tfFormTelefonoEmpresa.getText());

        empresaService.save(empresaSeleccionada);

        // devolvemos los campos a no editables
        tfFormNombreEmpresa.setEditable(false);
        tfFormDireccionEmpresa.setEditable(false);
        tfFormTelefonoEmpresa.setEditable(false);

        cargarTabla();
        mostrarPanel(panelTablaEmpresas);
    }

    //Cancelar modificación  
    @FXML
    private void cancelarFormEmpresa() {
        tfFormNombreEmpresa.setEditable(false);
        tfFormDireccionEmpresa.setEditable(false);
        tfFormTelefonoEmpresa.setEditable(false);
        rellenarFormModificar();
    }

    // Volver a la tabla  
    @FXML
    private void volverTablaEmpresa() {
        mostrarPanel(panelTablaEmpresas);
    }

    //Limpiar formulario crear  
    private void limpiarFormCrear() {
        tfNombreEmpresa.clear();
        tfDireccionEmpresa.clear();
        tfTelefonoEmpresa.clear();
        tfNombreEmpresa.setStyle("");
    }
}