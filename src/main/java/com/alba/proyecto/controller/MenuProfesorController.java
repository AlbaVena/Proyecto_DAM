package com.alba.proyecto.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.Periodo;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.modelo.Profesor;
import com.alba.proyecto.modelo.TutorEmpresa;
import com.alba.proyecto.repositorios.CursoRepository;
import com.alba.proyecto.repositorios.EstudianteRepository;
import com.alba.proyecto.repositorios.FCTRepository;
import com.alba.proyecto.repositorios.TutorEmpresaRepository;
import com.alba.proyecto.services.EmpresaService;
import com.alba.proyecto.services.InformesService;
import com.alba.proyecto.services.Sesion;
import com.alba.proyecto.services.UsuarioService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Validador;

/**
 * Clase MenuProfesorController.
 * 
 * Controlador del menú del Profesor. Tiene las mismas funcionalidades
 * que el Administrador pero restringidas a los perfiles Estudiante
 * y Tutor de Empresa.
 * 
 * Las estadísticas del panel principal muestran únicamente los datos
 * del curso asignado al profesor.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Controller
public class MenuProfesorController {

	@FXML
	private BorderPane PanelProfesor;

	// Paneles
	@FXML
	private VBox panelPrincipal;
	@FXML
	private VBox panelCrearEmpresa;
	@FXML
	private VBox panelTablaEmpresas;
	@FXML
	private VBox panelFormModificar;
	@FXML
	private VBox panelCrearUsuario;
	@FXML
	private VBox panelModificarUsuario;
	@FXML
	private VBox panelNuevaFE;
	@FXML
	private VBox panelTablaFEs;
	@FXML
	private VBox panelModFE;
	@FXML
	private VBox panelListadoFEs;

	// Barra superior
	@FXML
	private Label lblRol;
	@FXML
	private Label lblNombreUsuario;
	@FXML
	private Button btnLogOut;
	@FXML
	private Button btnAyuda;

	// Panel bienvenida
	@FXML
	private Label lblBienvenidaProfesor;
	@FXML
	private Label lblCursoProfesor;
	@FXML
	private Label lblNumEstudiantesProfesor;
	@FXML
	private Label lblNumFCTsProfesor;

	// Panel crear empresa
	@FXML
	private TextField tfNombreEmpresa;
	@FXML
	private TextField tfDireccionEmpresa;
	@FXML
	private TextField tfTelefonoEmpresa;
	@FXML
	private Button btnGuardarEmpresa;
	@FXML
	private TextField tfNombreTutor;
	@FXML
	private TextField tfApellidosTutor;
	@FXML
	private TextField tfEmailTutor;
	@FXML
	private TextField tfTelefonoTutor;
	@FXML
	private TextField tfUsuarioTutor;
	@FXML
	private TextField tfPasswordTutor;

	// Panel tabla empresas
	@FXML
	private Label lblTituloTablaEmpresas;
	@FXML
	private TextField tfBuscarEmpresa;
	@FXML
	private TableView<Empresa> tablaEmpresas;
	@FXML
	private TableColumn<Empresa, String> colNombreEmpresa;
	@FXML
	private TableColumn<Empresa, String> colDireccionEmpresa;
	@FXML
	private TableColumn<Empresa, String> colTelefonoEmpresa;
	@FXML
	private TableColumn<Empresa, String> colInfoEmpresa;

	// Panel form modificar empresa
	@FXML
	private Label lblTituloEmpresa;
	@FXML
	private TextField tfFormNombreEmpresa;
	@FXML
	private TextField tfFormDireccionEmpresa;
	@FXML
	private TextField tfFormTelefonoEmpresa;
	@FXML
	private Button btnEditarNombreEmpresa;
	@FXML
	private Button btnEditarDireccionEmpresa;
	@FXML
	private Button btnEditarTelefonoEmpresa;
	@FXML
	private Button btnGuardarFormEmpresa;
	@FXML
	private Button btnCancelarFormEmpresa;
	@FXML
	private Button btnVolverTablaEmpresa;

	// Panel crear usuario
	@FXML
	private ComboBox<String> cbRol;
	@FXML
	private ComboBox<Empresa> cbEmpresaUsuario;
	@FXML
	private ComboBox<Curso> cbCurso;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfApellidosUsuario;
	@FXML
	private TextField tfEmailUsuario;
	@FXML
	private TextField tfTelefonoUsuario;
	@FXML
	private TextField tfNombreUsuario;
	@FXML
	private TextField tfPass;
	@FXML
	private TextField tfNss;
	@FXML
	private Button btnGuardarCrearUsuario;
	@FXML
	private Button btnLimpiarCrearUsuario;

	// Panel modificar usuario
	@FXML
	private TextField tfBuscarUsuario;
	@FXML
	private ComboBox<String> cbFiltroRol;
	@FXML
	private TableView<Persona> tablaUsuarios;
	@FXML
	private TableColumn<Persona, String> colNombreUsuario;
	@FXML
	private TableColumn<Persona, String> colRolUsuario;
	@FXML
	private TableColumn<Persona, String> colFEUsuario;
	@FXML
	private TextField tfNombreMod;
	@FXML
	private TextField tfApellidosMod;
	@FXML
	private TextField tfEmailMod;
	@FXML
	private TextField tfTelefonoMod;
	@FXML
	private TextField tfUsuarioMod;
	@FXML
	private ComboBox<String> cbRolMod;
	@FXML
	private TextField tfPasswordMod;
	@FXML
	private Button btnEditarNombreMod;
	@FXML
	private Button btnEditarApellidosMod;
	@FXML
	private Button btnEditarEmailMod;
	@FXML
	private Button btnEditarTelefonoMod;
	@FXML
	private Button btnEditarUsuarioMod;
	@FXML
	private Button btnEditarRolMod;
	@FXML
	private Button btnEditarPasswordMod;
	@FXML
	private Button btnCancelarModUsuario;
	@FXML
	private Button btnGuardarModUsuario;
	@FXML
	private Label lblEmpresaMod;
	@FXML
	private ComboBox<Empresa> cbEmpresaMod;
	@FXML
	private Button btnEditarEmpresaMod;

	// Panel nueva FE
	@FXML
	private ComboBox<Estudiante> cbEstudianteFE;
	@FXML
	private ComboBox<TutorEmpresa> cbTutorFE;
	@FXML
	private ComboBox<Periodo> cbPeriodoFE;
	@FXML
	private DatePicker dpFechaInicioFE;
	@FXML
	private DatePicker dpFechaFinFE;
	@FXML
	private Button btnGuardarNuevaFE;

	// Panel tabla FEs
	@FXML
	private Label lblTituloTablaFEs;
	@FXML
	private TextField tfBuscarFE;
	@FXML
	private ComboBox<String> cbFiltroPeriodoFE;
	@FXML
	private TableView<FCT> tablaFEs;
	@FXML
	private TableColumn<FCT, String> colEstudianteFE;
	@FXML
	private TableColumn<FCT, String> colTutorFE;
	@FXML
	private TableColumn<FCT, String> colPeriodoFE;
	@FXML
	private TableColumn<FCT, String> colFechaInicioFE;
	@FXML
	private TableColumn<FCT, String> colFechaFinFE;

	// Panel modificar FE
	@FXML
	private TextField tfEstudianteModFE;
	@FXML
	private TextField tfPeriodoModFE;
	@FXML
	private ComboBox<TutorEmpresa> cbTutorModFE;
	@FXML
	private DatePicker dpFechaInicioModFE;
	@FXML
	private DatePicker dpFechaFinModFE;
	@FXML
	private Button btnEditarTutorModFE;
	@FXML
	private Button btnEditarFechaInicioModFE;
	@FXML
	private Button btnEditarFechaFinModFE;
	@FXML
	private Button btnVolverTablaFEs;
	@FXML
	private Button btnCancelarModFE;
	@FXML
	private Button btnGuardarModFE;
	@FXML
	private javafx.scene.control.Separator separadorConsultaFE;
	@FXML
	private Label lblTituloInfoFE;
	@FXML
	private javafx.scene.layout.GridPane gridInfoFE;
	@FXML
	private Label lblAlumnoFEConsulta;
	@FXML
	private Label lblCursoFEConsulta;
	@FXML
	private Label lblEmailAlumnoFEConsulta;
	@FXML
	private Label lblPeriodoFEConsulta;
	@FXML
	private Label lblFechaInicioFEConsulta;
	@FXML
	private Label lblFechaFinFEConsulta;
	@FXML
	private Label lblTutorFEConsulta;
	@FXML
	private Label lblEmpresaFEConsulta;

	private FCT feSeleccionada;
	private String modoTablaFEs = "";
	private Empresa empresaSeleccionada;
	private String modoTabla = "";
	private Persona usuarioSeleccionado;

	private ObservableList<Empresa> listaEmpresas = FXCollections.observableArrayList();

	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private Sesion sesion;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private FCTRepository fctRepository;
	@Autowired
	private TutorEmpresaRepository tutorEmpresaRepository;
	@Autowired
	private InformesService servicioInformes;

	@FXML
	public void initialize() {
		cargarSesion();
		cerrarSesion();
		configurarColumnas();
		configurarBuscador();
		configurarDobleClick();
		configurarPanelesGestionFE();
		configurarBotonesEditar();
		configurarPanelCrearUsuario();
		abrirAtajoAyuda();
		cargarEstadisticas();
		ocultarTodo();
		mostrarPanel(panelPrincipal);
	}

	/**
	 * Carga el mensaje de bienvenida y los contadores del panel principal
	 * filtrados por el curso asignado al profesor de la sesión activa.
	 */
	private void cargarEstadisticas() {
		Persona p = sesion.getUsuarioActual();
		if (p != null) {
			lblBienvenidaProfesor.setText("Bienvenido/a, " + p.getNombre());
			Profesor profesor = (Profesor) p;
			Curso curso = profesor.getCurso();
			if (curso != null) {
				lblCursoProfesor.setText("Curso: " + curso.toString());
				lblNumEstudiantesProfesor.setText(String.valueOf(estudianteRepository.countByCurso(curso)));
				lblNumFCTsProfesor.setText(String.valueOf(fctRepository.count()));
			}
		}
	}

	private void ocultarTodo() {
		panelPrincipal.setVisible(false);
		panelPrincipal.setManaged(false);
		panelCrearEmpresa.setVisible(false);
		panelCrearEmpresa.setManaged(false);
		panelTablaEmpresas.setVisible(false);
		panelTablaEmpresas.setManaged(false);
		panelFormModificar.setVisible(false);
		panelFormModificar.setManaged(false);
		panelCrearUsuario.setVisible(false);
		panelCrearUsuario.setManaged(false);
		panelModificarUsuario.setVisible(false);
		panelModificarUsuario.setManaged(false);
		panelNuevaFE.setVisible(false);
		panelNuevaFE.setManaged(false);
		panelTablaFEs.setVisible(false);
		panelTablaFEs.setManaged(false);
		panelModFE.setVisible(false);
		panelModFE.setManaged(false);
		panelListadoFEs.setVisible(false);
		panelListadoFEs.setManaged(false);
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

	// Botones menú lateral
	@FXML
	private void abrirCrearUsuario() {
		limpiarFormCrearUsuario();
		mostrarPanel(panelCrearUsuario);
	}

	@FXML
	private void abrirModificarUsuario() {
		cargarTablaUsuarios();
		limpiarFormModUsuario();
		mostrarPanel(panelModificarUsuario);
	}

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
	private void abrirNuevaFE() {
		limpiarNuevaFE();
		cbEstudianteFE.setItems(FXCollections.observableArrayList(estudianteRepository.findAll()));
		cbTutorFE.setItems(FXCollections.observableArrayList(tutorEmpresaRepository.findAll()));
		mostrarPanel(panelNuevaFE);
	}

	@FXML
	private void abrirModificarFE() {
		modoTablaFEs = "modificar";
		lblTituloTablaFEs.setText("Modificar FE");
		cargarTablaFEs();
		mostrarPanel(panelTablaFEs);
	}

	@FXML
	private void abrirConsultarFE() {
		modoTablaFEs = "consultar";
		lblTituloTablaFEs.setText("Consultar FE");
		separadorConsultaFE.setVisible(false);
		separadorConsultaFE.setManaged(false);
		lblTituloInfoFE.setVisible(false);
		lblTituloInfoFE.setManaged(false);
		gridInfoFE.setVisible(false);
		gridInfoFE.setManaged(false);
		cargarTablaFEs();
		mostrarPanel(panelTablaFEs);
	}

	@FXML
	private void abrirListadoFEs() {
		mostrarPanel(panelListadoFEs);
	}

	// Empresas — igual que Admin
	private void configurarColumnas() {
		colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	}

	private void cargarTabla() {
		List<Empresa> empresas = empresaService.findAll();
		listaEmpresas.clear();
		for (Empresa e : empresas) {
			listaEmpresas.add(e);
		}
		tablaEmpresas.setItems(listaEmpresas);
	}

	private void configurarBuscador() {
		tfBuscarEmpresa.setOnKeyReleased(event -> {
			String filtro = tfBuscarEmpresa.getText().toLowerCase();
			FilteredList<Empresa> listaFiltrada = new FilteredList<>(listaEmpresas);
			listaFiltrada.setPredicate(empresa -> {
				if (filtro.isEmpty())
					return true;
				if (empresa.getNombre() != null && empresa.getNombre().toLowerCase().contains(filtro))
					return true;
				if (empresa.getDireccion() != null && empresa.getDireccion().toLowerCase().contains(filtro))
					return true;
				return false;
			});
			tablaEmpresas.setItems(listaFiltrada);
		});
	}

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

	private void rellenarFormModificar() {
		tfFormNombreEmpresa.setText(empresaSeleccionada.getNombre());
		tfFormDireccionEmpresa.setText(empresaSeleccionada.getDireccion());
		tfFormTelefonoEmpresa.setText(empresaSeleccionada.getTelefono());
		tfFormNombreEmpresa.setEditable(false);
		tfFormDireccionEmpresa.setEditable(false);
		tfFormTelefonoEmpresa.setEditable(false);
	}

	private void configurarModoForm() {
		boolean esModificar = modoTabla.equals("modificar");
		btnEditarNombreEmpresa.setVisible(esModificar);
		btnEditarDireccionEmpresa.setVisible(esModificar);
		btnEditarTelefonoEmpresa.setVisible(esModificar);
		btnGuardarFormEmpresa.setVisible(esModificar);
		btnCancelarFormEmpresa.setVisible(esModificar);
	}

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
		btnEditarNombreMod.setOnAction(event -> {
			tfNombreMod.setEditable(true);
			tfNombreMod.requestFocus();
		});
		btnEditarApellidosMod.setOnAction(event -> {
			tfApellidosMod.setEditable(true);
			tfApellidosMod.requestFocus();
		});
		btnEditarEmailMod.setOnAction(event -> {
			tfEmailMod.setEditable(true);
			tfEmailMod.requestFocus();
		});
		btnEditarTelefonoMod.setOnAction(event -> {
			tfTelefonoMod.setEditable(true);
			tfTelefonoMod.requestFocus();
		});
		btnEditarUsuarioMod.setOnAction(event -> {
			tfUsuarioMod.setEditable(true);
			tfUsuarioMod.requestFocus();
		});
		btnEditarPasswordMod.setOnAction(event -> {
			tfPasswordMod.setEditable(true);
			tfPasswordMod.requestFocus();
		});
		btnEditarRolMod.setOnAction(event -> {
			cbRolMod.setDisable(false);
			cbRolMod.show();
		});
		btnEditarEmpresaMod.setOnAction(event -> {
			cbEmpresaMod.setDisable(false);
			cbEmpresaMod.show();
		});
		btnEditarTutorModFE.setOnAction(event -> {
			cbTutorModFE.setDisable(false);
			cbTutorModFE.show();
		});
		btnEditarFechaInicioModFE.setOnAction(event -> dpFechaInicioModFE.setDisable(false));
		btnEditarFechaFinModFE.setOnAction(event -> dpFechaFinModFE.setDisable(false));
	}

	@FXML
	private void guardarEmpresa() {
		String nombre = tfNombreEmpresa.getText().trim();
		String direccion = tfDireccionEmpresa.getText().trim();
		String telefono = tfTelefonoEmpresa.getText().trim();
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
		String nombreTutor = tfNombreTutor.getText().trim();
		String usuarioTutor = tfUsuarioTutor.getText().trim();
		String passwordTutor = tfPasswordTutor.getText().trim();
		if (!nombreTutor.isEmpty() && !usuarioTutor.isEmpty() && !passwordTutor.isEmpty()) {
			if (usuarioTutor.matches(utils.Validador.usuarioPasswordRegex)
					&& passwordTutor.matches(utils.Validador.usuarioPasswordRegex)) {
				String hash = utils.Transformador.hashPassword(passwordTutor);
				usuarioService.crearTutorEmpresa(usuarioTutor, hash, nombreTutor, tfApellidosTutor.getText().trim(),
						tfEmailTutor.getText().trim(), tfTelefonoTutor.getText().trim(), Perfil.TUTOREMPRESA, empresa,
						null);
			}
		}
		limpiarFormCrear();
		modoTabla = "modificar";
		cargarTabla();
		mostrarPanel(panelTablaEmpresas);
	}

	@FXML
	private void guardarFormEmpresa() {
		empresaSeleccionada.setNombre(tfFormNombreEmpresa.getText());
		empresaSeleccionada.setDireccion(tfFormDireccionEmpresa.getText());
		empresaSeleccionada.setTelefono(tfFormTelefonoEmpresa.getText());
		empresaService.save(empresaSeleccionada);
		tfFormNombreEmpresa.setEditable(false);
		tfFormDireccionEmpresa.setEditable(false);
		tfFormTelefonoEmpresa.setEditable(false);
		cargarTabla();
		mostrarPanel(panelTablaEmpresas);
	}

	@FXML
	private void cancelarFormEmpresa() {
		tfFormNombreEmpresa.setEditable(false);
		tfFormDireccionEmpresa.setEditable(false);
		tfFormTelefonoEmpresa.setEditable(false);
		rellenarFormModificar();
	}

	@FXML
	private void volverTablaEmpresa() {
		mostrarPanel(panelTablaEmpresas);
	}

	private void limpiarFormCrear() {
		tfNombreEmpresa.clear();
		tfDireccionEmpresa.clear();
		tfTelefonoEmpresa.clear();
		tfNombreEmpresa.setStyle("");
		tfNombreTutor.clear();
		tfApellidosTutor.clear();
		tfEmailTutor.clear();
		tfTelefonoTutor.clear();
		tfUsuarioTutor.clear();
		tfPasswordTutor.clear();
		tfUsuarioTutor.setStyle("");
		tfPasswordTutor.setStyle("");
	}

	// Usuarios — igual que Admin pero solo ESTUDIANTE y TUTOREMPRESA
	private void configurarPanelCrearUsuario() {
		// solo roles permitidos para el profesor
		cbRol.setItems(FXCollections.observableArrayList("ESTUDIANTE", "TUTOREMPRESA"));
		List<Curso> cursos = cursoRepository.findAll();
		cbCurso.setItems(FXCollections.observableArrayList(cursos));
		cbCurso.setCellFactory(lv -> new javafx.scene.control.ListCell<Curso>() {
			@Override
			protected void updateItem(Curso c, boolean empty) {
				super.updateItem(c, empty);
				setText(empty || c == null ? null : c.toString());
			}
		});
		cbCurso.setButtonCell(new javafx.scene.control.ListCell<Curso>() {
			@Override
			protected void updateItem(Curso c, boolean empty) {
				super.updateItem(c, empty);
				setText(empty || c == null ? null : c.toString());
			}
		});
		cbEmpresaUsuario.setCellFactory(lv -> new javafx.scene.control.ListCell<Empresa>() {
			@Override
			protected void updateItem(Empresa e, boolean empty) {
				super.updateItem(e, empty);
				setText(empty || e == null ? null : e.getNombre());
			}
		});
		cbEmpresaUsuario.setButtonCell(new javafx.scene.control.ListCell<Empresa>() {
			@Override
			protected void updateItem(Empresa e, boolean empty) {
				super.updateItem(e, empty);
				setText(empty || e == null ? null : e.getNombre());
			}
		});
		tfNss.setEditable(false);
		tfNss.setDisable(true);
		cbEmpresaUsuario.setDisable(true);
		cbCurso.setDisable(true);
		cbRol.valueProperty().addListener((obs, anterior, nuevo) -> {
			tfNss.setEditable(false);
			tfNss.setDisable(true);
			tfNss.clear();
			cbEmpresaUsuario.setDisable(true);
			cbEmpresaUsuario.setValue(null);
			cbCurso.setDisable(false);
			if ("TUTOREMPRESA".equals(nuevo)) {
				List<Empresa> empresas = empresaService.findAll();
				cbEmpresaUsuario.setItems(FXCollections.observableArrayList(empresas));
				cbEmpresaUsuario.setDisable(false);
				cbCurso.setDisable(true);
				cbCurso.setValue(null);
			} else if ("ESTUDIANTE".equals(nuevo)) {
				tfNss.setDisable(false);
				tfNss.setEditable(true);
			}
		});
		btnLimpiarCrearUsuario.setOnAction(event -> limpiarFormCrearUsuario());
		btnGuardarCrearUsuario.setOnAction(event -> guardarNuevoUsuario());
	}

	private void limpiarFormCrearUsuario() {
		cbRol.setValue(null);
		tfNombre.clear();
		tfApellidosUsuario.clear();
		tfEmailUsuario.clear();
		tfTelefonoUsuario.clear();
		tfNombreUsuario.clear();
		tfPass.clear();
		tfNss.clear();
		tfNss.setEditable(false);
		cbCurso.setValue(null);
		cbCurso.setDisable(false);
		cbEmpresaUsuario.setValue(null);
		cbEmpresaUsuario.setDisable(true);
		tfNombre.setStyle("");
		tfApellidosUsuario.setStyle("");
		tfNombreUsuario.setStyle("");
		tfPass.setStyle("");
		cbRol.setStyle("");
		cbCurso.setStyle("");
	}

	private void guardarNuevoUsuario() {
		String rol = cbRol.getValue();
		String nombre = tfNombre.getText().trim();
		String apellidos = tfApellidosUsuario.getText().trim();
		String email = tfEmailUsuario.getText().trim();
		String telefono = tfTelefonoUsuario.getText().trim();
		String usuario = tfNombreUsuario.getText().trim();
		String pass = tfPass.getText().trim();
		String nss = tfNss.getText().trim();
		Curso curso = cbCurso.getValue();
		Empresa empresa = cbEmpresaUsuario.getValue();
		boolean valido = true;
		if (rol == null) {
			cbRol.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			cbRol.setStyle("");
		}
		if (!nombre.matches(utils.Validador.nombreRegex)) {
			tfNombre.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfNombre.setStyle("");
		}
		if (!apellidos.matches(utils.Validador.apellidosRegex)) {
			tfApellidosUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfApellidosUsuario.setStyle("");
		}
		if (email.isEmpty() || !email.matches(utils.Validador.emailRegex)) {
			tfEmailUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else if (usuarioService.existeEmail(email)) {
			tfEmailUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfEmailUsuario.setStyle("");
		}
		if (!telefono.isEmpty() && !telefono.matches(utils.Validador.telefonoRegex)) {
			tfTelefonoUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfTelefonoUsuario.setStyle("");
		}
		if (!usuario.matches(utils.Validador.usuarioPasswordRegex)) {
			tfNombreUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else if (usuarioService.existeUsuario(usuario)) {
			tfNombreUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfNombreUsuario.setStyle("");
		}
		if (!pass.matches(utils.Validador.usuarioPasswordRegex)) {
			tfPass.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfPass.setStyle("");
		}
		if ("ESTUDIANTE".equals(rol)) {
			if (!nss.matches(utils.Validador.nSSRegex)) {
				tfNss.setStyle("-fx-border-color: red;");
				valido = false;
			} else {
				tfNss.setStyle("");
			}
			if (curso == null) {
				cbCurso.setStyle("-fx-border-color: red;");
				valido = false;
			} else {
				cbCurso.setStyle("");
			}
		} else if ("TUTOREMPRESA".equals(rol)) {
			if (empresa == null) {
				cbEmpresaUsuario.setStyle("-fx-border-color: red;");
				valido = false;
			} else {
				cbEmpresaUsuario.setStyle("");
			}
		}
		if (!valido)
			return;
		String passHash = utils.Transformador.hashPassword(pass);
		if ("ESTUDIANTE".equals(rol)) {
			usuarioService.crearEstudiante(usuario, passHash, nombre, apellidos, email, telefono, Perfil.ESTUDIANTE,
					nss, curso);
		} else if ("TUTOREMPRESA".equals(rol)) {
			usuarioService.crearTutorEmpresa(usuario, passHash, nombre, apellidos, email, telefono, Perfil.TUTOREMPRESA,
					empresa, null);
		}
		limpiarFormCrearUsuario();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Usuario creado");
		alert.setHeaderText(null);
		alert.setContentText("El usuario " + usuario + " se ha creado correctamente.");
		alert.showAndWait();
	}

	private void cargarTablaUsuarios() {
		colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
		colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("perfilStr"));
		colFEUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
		List<Persona> todos = usuarioService.findAll();
		ObservableList<Persona> lista = FXCollections.observableArrayList();
		for (Persona p : todos) {
			if (p.getPerfil() == Perfil.ESTUDIANTE || p.getPerfil() == Perfil.TUTOREMPRESA) {
				lista.add(p);
			}
		}
		FilteredList<Persona> listaFiltrada = new FilteredList<>(lista, p -> true);
		Runnable aplicarFiltro = () -> {
			String textoBuscar = tfBuscarUsuario.getText();
			String rolSeleccionado = cbFiltroRol.getValue();
			listaFiltrada.setPredicate(p -> {
				boolean coincideNombre = textoBuscar == null || textoBuscar.isEmpty()
						|| p.getNombreCompleto().toLowerCase().contains(textoBuscar.toLowerCase());
				boolean coincideRol = rolSeleccionado == null || rolSeleccionado.equals("Todos")
						|| p.getPerfilStr().equals(rolSeleccionado);
				return coincideNombre && coincideRol;
			});
		};
		tfBuscarUsuario.textProperty().addListener((obs, anterior, nuevo) -> aplicarFiltro.run());
		cbFiltroRol.valueProperty().addListener((obs, anterior, nuevo) -> aplicarFiltro.run());
		cbFiltroRol.setItems(FXCollections.observableArrayList("Todos", "ESTUDIANTE", "TUTOREMPRESA"));
		cbFiltroRol.setValue("Todos");
		tablaUsuarios.setItems(listaFiltrada);
		tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
			if (nuevo != null) {
				usuarioSeleccionado = nuevo;
				rellenarFormModUsuario(nuevo);
			}
		});
	}

	private void rellenarFormModUsuario(Persona p) {
		tfNombreMod.setText(p.getNombre());
		tfApellidosMod.setText(p.getApellidos());
		tfEmailMod.setText(p.getEmail());
		tfTelefonoMod.setText(p.getTelefono());
		tfUsuarioMod.setText(p.getUsuario());
		tfPasswordMod.clear();
		cbRolMod.setValue(p.getPerfil().toString());
		tfNombreMod.setEditable(false);
		tfApellidosMod.setEditable(false);
		tfEmailMod.setEditable(false);
		tfTelefonoMod.setEditable(false);
		tfUsuarioMod.setEditable(false);
		tfPasswordMod.setEditable(false);
		cbRolMod.setDisable(true);
		if (p.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) p;
			List<Empresa> empresas = empresaService.findAll();
			cbEmpresaMod.setItems(FXCollections.observableArrayList(empresas));
			cbEmpresaMod.setValue(tutor.getEmpresa());
			cbEmpresaMod.setDisable(true);
			cbEmpresaMod.setCellFactory(lv -> new javafx.scene.control.ListCell<Empresa>() {
				@Override
				protected void updateItem(Empresa e, boolean empty) {
					super.updateItem(e, empty);
					setText(empty || e == null ? null : e.getNombre());
				}
			});
			cbEmpresaMod.setButtonCell(new javafx.scene.control.ListCell<Empresa>() {
				@Override
				protected void updateItem(Empresa e, boolean empty) {
					super.updateItem(e, empty);
					setText(empty || e == null ? null : e.getNombre());
				}
			});
			lblEmpresaMod.setVisible(true);
			lblEmpresaMod.setManaged(true);
			cbEmpresaMod.setVisible(true);
			cbEmpresaMod.setManaged(true);
			btnEditarEmpresaMod.setVisible(true);
			btnEditarEmpresaMod.setManaged(true);
		} else {
			lblEmpresaMod.setVisible(false);
			lblEmpresaMod.setManaged(false);
			cbEmpresaMod.setVisible(false);
			cbEmpresaMod.setManaged(false);
			btnEditarEmpresaMod.setVisible(false);
			btnEditarEmpresaMod.setManaged(false);
		}
	}

	private void limpiarFormModUsuario() {
		tfNombreMod.clear();
		tfApellidosMod.clear();
		tfEmailMod.clear();
		tfTelefonoMod.clear();
		tfUsuarioMod.clear();
		tfPasswordMod.clear();
		cbRolMod.setValue(null);
		usuarioSeleccionado = null;
	}

	@FXML
	private void cancelarModUsuario() {
		if (usuarioSeleccionado != null)
			rellenarFormModUsuario(usuarioSeleccionado);
		else
			limpiarFormModUsuario();
	}

	@FXML
	private void guardarModUsuario() {
		if (usuarioSeleccionado == null)
			return;
		usuarioSeleccionado.setNombre(tfNombreMod.getText().trim());
		usuarioSeleccionado.setApellidos(tfApellidosMod.getText().trim());
		usuarioSeleccionado.setEmail(tfEmailMod.getText().trim());
		usuarioSeleccionado.setTelefono(tfTelefonoMod.getText().trim());
		usuarioSeleccionado.setUsuario(tfUsuarioMod.getText().trim());
		String nuevaPass = tfPasswordMod.getText().trim();
		if (!nuevaPass.isEmpty())
			usuarioSeleccionado.setContraseña(utils.Transformador.hashPassword(nuevaPass));
		if (usuarioSeleccionado.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) usuarioSeleccionado;
			if (cbEmpresaMod.getValue() != null)
				tutor.setEmpresa(cbEmpresaMod.getValue());
		}
		usuarioService.modificarUsuario(usuarioSeleccionado);
		cargarTablaUsuarios();
		limpiarFormModUsuario();
	}

	// FEs
	private void configurarPanelesGestionFE() {
		cbEstudianteFE.setItems(FXCollections.observableArrayList(estudianteRepository.findAll()));
		cbEstudianteFE.setCellFactory(lv -> new javafx.scene.control.ListCell<Estudiante>() {
			@Override
			protected void updateItem(Estudiante e, boolean empty) {
				super.updateItem(e, empty);
				setText(empty || e == null ? null : e.getNombreCompleto());
			}
		});
		cbEstudianteFE.setButtonCell(new javafx.scene.control.ListCell<Estudiante>() {
			@Override
			protected void updateItem(Estudiante e, boolean empty) {
				super.updateItem(e, empty);
				setText(empty || e == null ? null : e.getNombreCompleto());
			}
		});
		cbTutorFE.setItems(FXCollections.observableArrayList(tutorEmpresaRepository.findAll()));
		cbTutorFE.setCellFactory(lv -> new javafx.scene.control.ListCell<TutorEmpresa>() {
			@Override
			protected void updateItem(TutorEmpresa t, boolean empty) {
				super.updateItem(t, empty);
				setText(empty || t == null ? null : t.getNombreCompleto());
			}
		});
		cbTutorFE.setButtonCell(new javafx.scene.control.ListCell<TutorEmpresa>() {
			@Override
			protected void updateItem(TutorEmpresa t, boolean empty) {
				super.updateItem(t, empty);
				setText(empty || t == null ? null : t.getNombreCompleto());
			}
		});
		cbPeriodoFE.setItems(FXCollections.observableArrayList(Periodo.values()));
		btnGuardarNuevaFE.setOnAction(event -> guardarNuevaFE());
	}

	private void cargarTablaFEs() {
		colEstudianteFE.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getEstudiante().getNombreCompleto()));
		colTutorFE.setCellValueFactory(
				data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTutor().getNombreCompleto()));
		colPeriodoFE.setCellValueFactory(
				data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPeriodo().toString()));
		colFechaInicioFE.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getFechaInicio() != null ? data.getValue().getFechaInicio().toString() : "—"));
		colFechaFinFE.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getFechaFin() != null ? data.getValue().getFechaFin().toString() : "—"));
		List<FCT> todasFEs = fctRepository.findAll();
		ObservableList<FCT> lista = FXCollections.observableArrayList(todasFEs);
		FilteredList<FCT> listaFiltrada = new FilteredList<>(lista, f -> true);
		cbFiltroPeriodoFE.setItems(FXCollections.observableArrayList("Todos", "ORDINARIO", "EXTRAORDINARIO"));
		cbFiltroPeriodoFE.setValue("Todos");
		Runnable aplicarFiltro = () -> {
			String textoBuscar = tfBuscarFE.getText();
			String periodoSel = cbFiltroPeriodoFE.getValue();
			listaFiltrada.setPredicate(fe -> {
				boolean coincideNombre = textoBuscar == null || textoBuscar.isEmpty()
						|| fe.getEstudiante().getNombreCompleto().toLowerCase().contains(textoBuscar.toLowerCase());
				boolean coincidePeriodo = periodoSel == null || periodoSel.equals("Todos")
						|| fe.getPeriodo().toString().equals(periodoSel);
				return coincideNombre && coincidePeriodo;
			});
		};
		tfBuscarFE.textProperty().addListener((obs, a, n) -> aplicarFiltro.run());
		cbFiltroPeriodoFE.valueProperty().addListener((obs, a, n) -> aplicarFiltro.run());
		tablaFEs.setItems(listaFiltrada);
		tablaFEs.setOnMouseClicked(event -> {
			feSeleccionada = tablaFEs.getSelectionModel().getSelectedItem();
			if (feSeleccionada == null)
				return;
			if (modoTablaFEs.equals("modificar") && event.getClickCount() == 2) {
				rellenarModFE();
				configurarModoFormFE();
				mostrarPanel(panelModFE);
			} else if (modoTablaFEs.equals("consultar") && event.getClickCount() == 1) {
				mostrarInfoConsultaFE(feSeleccionada);
			}
		});
	}

	private void mostrarInfoConsultaFE(FCT fe) {
		lblAlumnoFEConsulta.setText(fe.getEstudiante().getNombreCompleto());
		lblCursoFEConsulta
				.setText(fe.getEstudiante().getCurso() != null ? fe.getEstudiante().getCurso().toString() : "—");
		lblEmailAlumnoFEConsulta.setText(fe.getEstudiante().getEmail() != null ? fe.getEstudiante().getEmail() : "—");
		lblPeriodoFEConsulta.setText(fe.getPeriodo().toString());
		lblFechaInicioFEConsulta.setText(fe.getFechaInicio() != null ? fe.getFechaInicio().toString() : "—");
		lblFechaFinFEConsulta.setText(fe.getFechaFin() != null ? fe.getFechaFin().toString() : "—");
		lblTutorFEConsulta.setText(fe.getTutor().getNombreCompleto());
		lblEmpresaFEConsulta.setText(fe.getTutor().getEmpresa() != null ? fe.getTutor().getEmpresa().getNombre() : "—");
		separadorConsultaFE.setVisible(true);
		separadorConsultaFE.setManaged(true);
		lblTituloInfoFE.setVisible(true);
		lblTituloInfoFE.setManaged(true);
		gridInfoFE.setVisible(true);
		gridInfoFE.setManaged(true);
	}

	private void rellenarModFE() {
		tfEstudianteModFE.setText(feSeleccionada.getEstudiante().getNombreCompleto());
		tfPeriodoModFE.setText(feSeleccionada.getPeriodo().toString());
		List<TutorEmpresa> tutores = tutorEmpresaRepository.findAll();
		cbTutorModFE.setItems(FXCollections.observableArrayList(tutores));
		cbTutorModFE.setCellFactory(lv -> new javafx.scene.control.ListCell<TutorEmpresa>() {
			@Override
			protected void updateItem(TutorEmpresa t, boolean empty) {
				super.updateItem(t, empty);
				setText(empty || t == null ? null : t.getNombreCompleto());
			}
		});
		cbTutorModFE.setButtonCell(new javafx.scene.control.ListCell<TutorEmpresa>() {
			@Override
			protected void updateItem(TutorEmpresa t, boolean empty) {
				super.updateItem(t, empty);
				setText(empty || t == null ? null : t.getNombreCompleto());
			}
		});
		cbTutorModFE.setValue(feSeleccionada.getTutor());
		cbTutorModFE.setDisable(true);
		dpFechaInicioModFE.setValue(feSeleccionada.getFechaInicio());
		dpFechaInicioModFE.setDisable(true);
		dpFechaFinModFE.setValue(feSeleccionada.getFechaFin());
		dpFechaFinModFE.setDisable(true);
	}

	private void configurarModoFormFE() {
		boolean esModificar = modoTablaFEs.equals("modificar");
		btnEditarTutorModFE.setVisible(esModificar);
		btnEditarFechaInicioModFE.setVisible(esModificar);
		btnEditarFechaFinModFE.setVisible(esModificar);
		btnGuardarModFE.setVisible(esModificar);
		btnCancelarModFE.setVisible(esModificar);
	}

	@FXML
	private void volverTablaFEs() {
		mostrarPanel(panelTablaFEs);
	}

	@FXML
	private void cancelarModFE() {
		if (feSeleccionada != null)
			rellenarModFE();
	}

	@FXML
	private void guardarModFE() {
		if (feSeleccionada == null)
			return;
		feSeleccionada.setTutor(cbTutorModFE.getValue());
		feSeleccionada.setFechaInicio(dpFechaInicioModFE.getValue());
		feSeleccionada.setFechaFin(dpFechaFinModFE.getValue());
		fctRepository.save(feSeleccionada);
		volverTablaFEs();
		cargarTablaFEs();
	}

	/**
	 * Recoge los datos del formulario de nueva FE, valida las fechas
	 * y comprueba que el estudiante no tenga ya una FE en el mismo periodo
	 * antes de guardar.
	 */
	private void guardarNuevaFE() {
		Estudiante estudiante = cbEstudianteFE.getValue();
		TutorEmpresa tutor = cbTutorFE.getValue();
		Periodo periodo = cbPeriodoFE.getValue();
		LocalDate inicio = dpFechaInicioFE.getValue();
		LocalDate fin = dpFechaFinFE.getValue();
		boolean valido = true;
		if (estudiante == null) {
			cbEstudianteFE.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			cbEstudianteFE.setStyle("");
		}
		if (tutor == null) {
			cbTutorFE.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			cbTutorFE.setStyle("");
		}
		if (periodo == null) {
			cbPeriodoFE.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			cbPeriodoFE.setStyle("");
		}
		if (inicio == null) {
			dpFechaInicioFE.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			dpFechaInicioFE.setStyle("");
		}
		if (inicio == null || fin == null || !Validador.esFechaValida(
		        inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
		        fin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
		    dpFechaFinFE.setStyle("-fx-border-color: red;");
		    valido = false;
		} else {
		    dpFechaFinFE.setStyle("");
		}
		
		if (!valido)
			return;
		FCT fct = new FCT();
		fct.setFechaInicio(inicio);
		fct.setFechaFin(fin);
		fct.setEstudiante(estudiante);
		fct.setPeriodo(periodo);
		fct.setTutor(tutor);

		// comprobar si el estudiante ya tiene una FE en ese periodo
		List<FCT> fctExistentes = fctRepository.findAll();
		for (FCT fctExistente : fctExistentes) {
			if (fctExistente.getEstudiante().getId().equals(estudiante.getId())
					&& fctExistente.getPeriodo() == periodo) {
				Alert alerta = new Alert(Alert.AlertType.WARNING);
				alerta.setTitle("FE duplicada");
				alerta.setHeaderText(null);
				alerta.setContentText("Este estudiante ya tiene una FE asignada en el periodo " + periodo
						+ ". No se puede crear otra.");
				alerta.showAndWait();
				return;
			}
		}

		fctRepository.save(fct);

		limpiarNuevaFE();
		modoTablaFEs = "modificar";
		lblTituloTablaFEs.setText("Modificar FE");
		cargarTablaFEs();
		mostrarPanel(panelTablaFEs);
	}

	private void limpiarNuevaFE() {
		cbEstudianteFE.setValue(null);
		cbTutorFE.setValue(null);
		cbPeriodoFE.setValue(null);
		dpFechaInicioFE.setValue(null);
		dpFechaFinFE.setValue(null);
		cbEstudianteFE.setStyle("");
		cbTutorFE.setStyle("");
		cbPeriodoFE.setStyle("");
		dpFechaInicioFE.setStyle("");
		dpFechaFinFE.setStyle("");
	}

	/**
	 * Genera un listado en PDF con todas las FEs registradas
	 * usando JasperReports y lo guarda en reportes_generados/.
	 */
	@FXML
	private void generarListadoFEs() {
		List<FCT> fcts = fctRepository.findAll();
		String ruta = servicioInformes.generarListadoFEs(fcts);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Informe generado");
		alert.setHeaderText(null);
		if (ruta != null) {
			alert.setContentText("Listado de FEs generado correctamente en:\n" + ruta);
		} else {
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText("Error al generar el listado. Comprueba la consola.");
		}
		alert.showAndWait();
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
					if (evento.getCode() == KeyCode.F1)
						abrirAyuda();
				});
			}
		});
	}
}