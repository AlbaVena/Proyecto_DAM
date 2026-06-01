package com.alba.proyecto.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.repositorios.EmpresaRepository;
import com.alba.proyecto.repositorios.EstudianteRepository;
import com.alba.proyecto.repositorios.FCTRepository;
import com.alba.proyecto.services.EmpresaService;
import com.alba.proyecto.services.Sesion;
import com.alba.proyecto.services.UsuarioService;
import utils.Transformador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MenuAdminController {
	
	@FXML private BorderPane PaneAdmin;

    //StackPane  
    @FXML private VBox panelCrearEmpresa;
    @FXML private VBox panelTablaEmpresas;
    @FXML private VBox panelFormModificar;

    //Panel Crear Empresa  
    @FXML private TextField tfNombreEmpresa;
    @FXML private TextField tfDireccionEmpresa;
    @FXML private TextField tfTelefonoEmpresa;
    @FXML private Button btnGuardarEmpresa;

    // Sección tutor opcional dentro del panel crear empresa
    @FXML private TextField tfNombreTutor;
    @FXML private TextField tfApellidosTutor;
    @FXML private TextField tfEmailTutor;
    @FXML private TextField tfTelefonoTutor;
    @FXML private TextField tfUsuarioTutor;
    @FXML private TextField tfPasswordTutor;

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
    
    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnLogOut;
    
    @FXML private Button btnAyuda;
    
    @FXML private VBox panelPrincipal;
    @FXML private Label lblBienvenidaAdmin;
    @FXML private Label lblNumEstudiantes;
    @FXML private Label lblNumEmpresas;
    @FXML private Label lblNumFCTs;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
	private Sesion sesion;
    
    @Autowired
    private ConfigurableApplicationContext context;
    
    @Autowired private EstudianteRepository estudianteRepository;
    
    @Autowired private EmpresaRepository empresaRepository;
    
    @Autowired private FCTRepository fctRepository;

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
        abrirAtajoAyuda();
        ocultarTodo();
        mostrarPanel(panelPrincipal);
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
    	  Persona p = sesion.getUsuarioActual();
    	    if (p != null) {
    	        lblBienvenidaAdmin.setText("Bienvenido/a, " + p.getNombre());
    	    }
    	    lblNumEstudiantes.setText(String.valueOf(estudianteRepository.count()));
    	    lblNumEmpresas.setText(String.valueOf(empresaRepository.count()));
    	    lblNumFCTs.setText(String.valueOf(fctRepository.count()));
		
	}

	private void ocultarTodo() {
        panelCrearEmpresa.setVisible(false);
        panelCrearEmpresa.setManaged(false);
        panelTablaEmpresas.setVisible(false);
        panelTablaEmpresas.setManaged(false);
        panelFormModificar.setVisible(false);
        panelFormModificar.setManaged(false);
        panelPrincipal.setVisible(false);
        panelPrincipal.setManaged(false);
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
                stage.centerOnScreen();
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

    //Guardar empresa nueva (y tutor opcional si se han rellenado sus campos)
    @FXML
    private void guardarEmpresa() {
        String nombre = tfNombreEmpresa.getText().trim();
        String direccion = tfDireccionEmpresa.getText().trim();
        String telefono = tfTelefonoEmpresa.getText().trim();

        // nombre de empresa obligatorio
        if (nombre.isEmpty()) {
            tfNombreEmpresa.setStyle("-fx-border-color: red;");
            return;
        }
        tfNombreEmpresa.setStyle("");

        // guardar empresa
        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresaService.save(empresa);

        // comprobar si se han rellenado datos del tutor
        String nombreTutor    = tfNombreTutor.getText().trim();
        String apellidosTutor = tfApellidosTutor.getText().trim();
        String emailTutor     = tfEmailTutor.getText().trim();
        String telefonoTutor  = tfTelefonoTutor.getText().trim();
        String usuarioTutor   = tfUsuarioTutor.getText().trim();
        String passwordTutor  = tfPasswordTutor.getText().trim();

        // si al menos nombre, usuario y contraseña tienen algo, creamos el tutor
        boolean crearTutor = !nombreTutor.isEmpty() && !usuarioTutor.isEmpty() && !passwordTutor.isEmpty();

        if (crearTutor) {
            // validación mínima de usuario y contraseña
            if (!usuarioTutor.matches(utils.Validador.usuarioPasswordRegex)) {
                tfUsuarioTutor.setStyle("-fx-border-color: red;");
                // la empresa ya se guardó, avisamos pero no bloqueamos
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Tutor no guardado");
                alerta.setHeaderText(null);
                alerta.setContentText("La empresa se ha guardado correctamente.\n"
                    + "El tutor no se ha creado porque el usuario no tiene el formato correcto "
                    + "(entre 3 y 12 caracteres, solo letras, números y _).");
                alerta.showAndWait();
            } else if (!passwordTutor.matches(utils.Validador.usuarioPasswordRegex)) {
                tfPasswordTutor.setStyle("-fx-border-color: red;");
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Tutor no guardado");
                alerta.setHeaderText(null);
                alerta.setContentText("La empresa se ha guardado correctamente.\n"
                    + "El tutor no se ha creado porque la contraseña no tiene el formato correcto.");
                alerta.showAndWait();
            } else {
                // hashear contraseña y crear tutor
                String passwordHash = Transformador.hashPassword(passwordTutor);
                usuarioService.crearTutorEmpresa(
                    usuarioTutor, passwordHash,
                    nombreTutor, apellidosTutor,
                    emailTutor, telefonoTutor,
                    Perfil.TUTOREMPRESA, empresa, null
                );
            }
        }

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

    //Limpiar formulario crear (empresa y tutor)
    private void limpiarFormCrear() {
        // empresa
        tfNombreEmpresa.clear();
        tfDireccionEmpresa.clear();
        tfTelefonoEmpresa.clear();
        tfNombreEmpresa.setStyle("");
        // tutor
        tfNombreTutor.clear();
        tfApellidosTutor.clear();
        tfEmailTutor.clear();
        tfTelefonoTutor.clear();
        tfUsuarioTutor.clear();
        tfPasswordTutor.clear();
        tfUsuarioTutor.setStyle("");
        tfPasswordTutor.setStyle("");
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
    	PaneAdmin.sceneProperty().addListener((obs, escenaAnterior, escenaNueva) -> {
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