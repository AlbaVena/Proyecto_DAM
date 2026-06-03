package com.alba.proyecto.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.modelo.Profesor;
import com.alba.proyecto.modelo.TutorEmpresa;
import com.alba.proyecto.repositorios.CursoRepository;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MenuAdminController {

	@FXML
	private BorderPane PaneAdmin;

	// StackPane
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

	// Panel Crear Empresa
	@FXML
	private TextField tfNombreEmpresa;
	@FXML
	private TextField tfDireccionEmpresa;
	@FXML
	private TextField tfTelefonoEmpresa;
	@FXML
	private Button btnGuardarEmpresa;

	// Sección tutor opcional dentro del panel crear empresa
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

	// Panel Tabla
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

	// Panel Form Modificar
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

	// Botones menus laterales
	@FXML
	private Button btnCrearEmpresaLateral;
	@FXML
	private Button btnModificarEmpresaLateral;
	@FXML
	private Button btnConsultarEmpresaLateral;

	@FXML
	private Label lblRol;
	@FXML
	private Label lblNombreUsuario;
	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnAyuda;

	@FXML
	private Label lblBienvenidaAdmin;
	@FXML
	private Label lblNumEstudiantes;
	@FXML
	private Label lblNumEmpresas;
	@FXML
	private Label lblNumFCTs;

	// Panel modificar usuario
	@FXML
	private VBox panelModificarUsuario;
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

	// Panel consultar usuario
	@FXML
	private VBox panelConsultarUsuario;
	@FXML
	private TextField tfBuscarUsuarioConsulta;
	@FXML
	private ComboBox<String> cbFiltroRolConsulta;
	@FXML
	private TableView<Persona> tablaUsuariosConsulta;
	@FXML
	private TableColumn<Persona, String> colNombreConsulta;
	@FXML
	private TableColumn<Persona, String> colRolConsulta;
	@FXML
	private TableColumn<Persona, String> colFEConsulta;
	// sub-paneles info
	@FXML
	private GridPane infoProfesor;
	@FXML
	private GridPane infoEstudiante;
	@FXML
	private GridPane infoTutor;
	// labels profesor
	@FXML
	private Label lblNombreProf;
	@FXML
	private Label lblUsuarioProf;
	@FXML
	private Label lblCursoProf;
	@FXML
	private Label lblEmailProf;
	// labels estudiante
	@FXML
	private Label lblNombreEst;
	@FXML
	private Label lblUsuarioEst;
	@FXML
	private Label lblCursoEst;
	@FXML
	private Label lblEmailEst;
	@FXML
	private Label lblFEEst;
	@FXML
	private Label lblEmpresaEst;
	// labels tutor
	@FXML
	private Label lblNombreTutorC;
	@FXML
	private Label lblUsuarioTutorC;
	@FXML
	private Label lblEmpresaTutorC;
	@FXML
	private Label lblNumFEsTutorC;

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

	// usuario seleccionado en la tabla de modificar
	private Persona usuarioSeleccionado;

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
	private EmpresaRepository empresaRepository;

	@Autowired
	private FCTRepository fctRepository;

	// lista para la tabla de empresas
	private ObservableList<Empresa> listaEmpresas = FXCollections.observableArrayList();

	// para modificar
	private Empresa empresaSeleccionada;

	// "modificar" o "consultar"
	private String modoTabla = "";

	@FXML
	public void initialize() {
		cargarSesion();
		cerrarSesion();
		configurarColumnas();
		configurarBuscador();
		configurarDobleClick();
		configurarBotonesEditar();
		configurarPanelCrearUsuario();
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
		panelConsultarUsuario.setVisible(false);
		panelConsultarUsuario.setManaged(false);
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

	// Mostrar un panel
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

	private void configurarPanelCrearUsuario() {
		// cargar roles
		cbRol.setItems(FXCollections.observableArrayList("PROFESOR", "ESTUDIANTE", "TUTOREMPRESA"));

		// cargar cursos con toString()
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

		// cargar empresas para TutorEmpresa
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
		// estado inicial — todo deshabilitado hasta elegir rol
		tfNss.setEditable(false);
		tfNss.setDisable(true);   // visualmente gris, no clickable
		cbEmpresaUsuario.setDisable(true);
		cbCurso.setDisable(true);

		// al cambiar el rol, habilitar/deshabilitar campos según corresponda
		cbRol.valueProperty().addListener((obs, anterior, nuevo) -> {
			// resetear todos
			tfNss.setEditable(false);
		    tfNss.setDisable(true);
			tfNss.clear();
			cbEmpresaUsuario.setDisable(true);
			cbEmpresaUsuario.setValue(null);
			cbCurso.setDisable(false);

			if ("TUTOREMPRESA".equals(nuevo)) {
				// tutor: empresa obligatoria, sin curso, sin NSS
				List<Empresa> empresas = empresaService.findAll();
				cbEmpresaUsuario.setItems(FXCollections.observableArrayList(empresas));
				cbEmpresaUsuario.setDisable(false);
				cbCurso.setDisable(true);
				cbCurso.setValue(null);
			} else if ("ESTUDIANTE".equals(nuevo)) {
				// estudiante: NSS obligatorio, curso obligatorio, sin empresa
				tfNss.setDisable(false);
			}
			// PROFESOR: solo curso, sin NSS ni empresa
		});

		// botón limpiar
		btnLimpiarCrearUsuario.setOnAction(event -> limpiarFormCrearUsuario());

		// botón guardar
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
		// limpiar bordes rojos
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

		// rol
		if (rol == null) {
			cbRol.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			cbRol.setStyle("");
		}

		// nombre
		if (!nombre.matches(utils.Validador.nombreRegex)) {
			tfNombre.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfNombre.setStyle("");
		}

		// apellidos
		if (!apellidos.matches(utils.Validador.apellidosRegex)) {
			tfApellidosUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfApellidosUsuario.setStyle("");
		}

		// email — obligatorio, válido y único
		if (email.isEmpty() || !email.matches(utils.Validador.emailRegex)) {
		    tfEmailUsuario.setStyle("-fx-border-color: red;");
		    valido = false;
		} else if (usuarioService.existeEmail(email)) {
		    tfEmailUsuario.setStyle("-fx-border-color: red;");
		    tfEmailUsuario.setPromptText("Este email ya está registrado");
		    valido = false;
		} else {
		    tfEmailUsuario.setStyle("");
		    tfEmailUsuario.setPromptText("correo@email.com");
		}

		// teléfono — opcional pero si se rellena debe ser válido
		if (!telefono.isEmpty() && !telefono.matches(utils.Validador.telefonoRegex)) {
			tfTelefonoUsuario.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfTelefonoUsuario.setStyle("");
		}


		// usuario — obligatorio, formato válido y único
		if (!usuario.matches(utils.Validador.usuarioPasswordRegex)) {
		    tfNombreUsuario.setStyle("-fx-border-color: red;");
		    valido = false;
		} else if (usuarioService.existeUsuario(usuario)) {
		    tfNombreUsuario.setStyle("-fx-border-color: red;");
		    tfNombreUsuario.setPromptText("Este usuario ya existe");
		    valido = false;
		} else {
		    tfNombreUsuario.setStyle("");
		    tfNombreUsuario.setPromptText("");
		}

		// contraseña
		if (!pass.matches(utils.Validador.usuarioPasswordRegex)) {
			tfPass.setStyle("-fx-border-color: red;");
			valido = false;
		} else {
			tfPass.setStyle("");
		}

		// validaciones específicas por rol
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

		} else if ("PROFESOR".equals(rol)) {
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

		// si todo es válido, crear el usuario
		String passHash = utils.Transformador.hashPassword(pass);

		if ("PROFESOR".equals(rol)) {
			usuarioService.crearProfesor(usuario, passHash, nombre, apellidos, email, telefono, Perfil.PROFESOR, curso);

		} else if ("ESTUDIANTE".equals(rol)) {
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

	// Configurar columnas de la tabla de empresa
	private void configurarColumnas() {
		colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	}

	// Cargar datos en la tabla -- primero se limpia todo y luego se añade cadau na
	private void cargarTabla() {
		List<Empresa> empresas = empresaService.findAll();
		listaEmpresas.clear();
		for (Empresa e : empresas) {
			listaEmpresas.add(e);
		}
		tablaEmpresas.setItems(listaEmpresas);
	}

	// Buscador en tiempo real con el textfield
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

	// Doble click en tabla para cargar
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

	// Rellenar formulario con datos de la empresa seleccionada
	// Los campos siempre llegan bloqueados — el botón editar los desbloquea uno a
	// uno
	private void rellenarFormModificar() {
		tfFormNombreEmpresa.setText(empresaSeleccionada.getNombre());
		tfFormDireccionEmpresa.setText(empresaSeleccionada.getDireccion());
		tfFormTelefonoEmpresa.setText(empresaSeleccionada.getTelefono());
		tfFormNombreEmpresa.setEditable(false);
		tfFormDireccionEmpresa.setEditable(false);
		tfFormTelefonoEmpresa.setEditable(false);
	}

	// Botones laterales — Gestión de usuarios
	@FXML
	private void abrirCrearUsuario() {
		mostrarPanel(panelCrearUsuario);
	}

	@FXML
	private void abrirModificarUsuario() {
		cargarTablaUsuarios();
		limpiarFormModUsuario();
		mostrarPanel(panelModificarUsuario);
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

	private void cargarTablaUsuarios() {
		colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
		colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("perfilStr"));
		colFEUsuario.setCellValueFactory(new PropertyValueFactory<>("perfilStr")); // TODO: tieneFE

		// excluir ADMINISTRADOR
		List<Persona> todos = usuarioService.findAll();
		ObservableList<Persona> lista = FXCollections.observableArrayList();
		for (Persona p : todos) {
			if (p.getPerfil() != Perfil.ADMINISTRADOR) {
				lista.add(p);
			}
		}

		FilteredList<Persona> listaFiltrada = new FilteredList<>(lista, p -> true);

		// predicate centralizado — lo usan tanto el buscador como el combo
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

		cbFiltroRol.setItems(FXCollections.observableArrayList("Todos", "PROFESOR", "ESTUDIANTE", "TUTOREMPRESA"));
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
		// bloquear todos al cargar
		tfNombreMod.setEditable(false);
		tfApellidosMod.setEditable(false);
		tfEmailMod.setEditable(false);
		tfTelefonoMod.setEditable(false);
		tfUsuarioMod.setEditable(false);
		tfPasswordMod.setEditable(false);
		cbRolMod.setDisable(true);
		// mostrar empresa solo si es TutorEmpresa
		if (p.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) p;
			List<Empresa> empresas = empresaService.findAll();
			cbEmpresaMod.setItems(FXCollections.observableArrayList(empresas));
			cbEmpresaMod.setValue(tutor.getEmpresa());
			cbEmpresaMod.setDisable(true);
			// cellfactory para mostrar nombre
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

	// Configurar el formulario dependiendo de donde viene
	private void configurarModoForm() {
		// si viene de modificar, carga el form editable
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

	// botones para editar campo se ponen visibles en modo Editar nada mas

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

		// modificar usuario:
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

	}

	@FXML
	private void cancelarModUsuario() {
		if (usuarioSeleccionado != null) {
			rellenarFormModUsuario(usuarioSeleccionado);
		} else {
			limpiarFormModUsuario();
		}
	}

	@FXML
	private void guardarModUsuario() {
		if (usuarioSeleccionado == null) {
			return;
		}
		usuarioSeleccionado.setNombre(tfNombreMod.getText().trim());
		usuarioSeleccionado.setApellidos(tfApellidosMod.getText().trim());
		usuarioSeleccionado.setEmail(tfEmailMod.getText().trim());
		usuarioSeleccionado.setTelefono(tfTelefonoMod.getText().trim());
		usuarioSeleccionado.setUsuario(tfUsuarioMod.getText().trim());

		String nuevaPass = tfPasswordMod.getText().trim();
		if (!nuevaPass.isEmpty()) {
			usuarioSeleccionado.setContraseña(Transformador.hashPassword(nuevaPass));
		}
		if (usuarioSeleccionado.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) usuarioSeleccionado;
			if (cbEmpresaMod.getValue() != null) {
				tutor.setEmpresa(cbEmpresaMod.getValue());
			}
		}

		usuarioService.modificarUsuario(usuarioSeleccionado);
		cargarTablaUsuarios();
		limpiarFormModUsuario();
	}

	// Guardar empresa nueva (y tutor opcional si se han rellenado sus campos)
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
		String nombreTutor = tfNombreTutor.getText().trim();
		String apellidosTutor = tfApellidosTutor.getText().trim();
		String emailTutor = tfEmailTutor.getText().trim();
		String telefonoTutor = tfTelefonoTutor.getText().trim();
		String usuarioTutor = tfUsuarioTutor.getText().trim();
		String passwordTutor = tfPasswordTutor.getText().trim();

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
				usuarioService.crearTutorEmpresa(usuarioTutor, passwordHash, nombreTutor, apellidosTutor, emailTutor,
						telefonoTutor, Perfil.TUTOREMPRESA, empresa, null);
			}
		}

		limpiarFormCrear();
		mostrarPanel(panelTablaEmpresas);
		modoTabla = "modificar";
		cargarTabla();
	}

	// Guardar modificación
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

	// Cancelar modificación
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

	// Limpiar formulario crear (empresa y tutor)
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

	@FXML
	private void abrirConsultarUsuario() {
		cargarTablaUsuariosConsulta();
		ocultarInfoConsulta();
		mostrarPanel(panelConsultarUsuario);
	}

	private void cargarTablaUsuariosConsulta() {
		colNombreConsulta.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
		colRolConsulta.setCellValueFactory(new PropertyValueFactory<>("perfilStr"));
		colFEConsulta.setCellValueFactory(new PropertyValueFactory<>("perfilStr")); // TODO: tieneFE

		List<Persona> todos = usuarioService.findAll();
		ObservableList<Persona> lista = FXCollections.observableArrayList();
		for (Persona p : todos) {
			if (p.getPerfil() != Perfil.ADMINISTRADOR) {
				lista.add(p);
			}
		}

		FilteredList<Persona> listaFiltrada = new FilteredList<>(lista, p -> true);

		Runnable aplicarFiltro = () -> {
			String textoBuscar = tfBuscarUsuarioConsulta.getText();
			String rolSeleccionado = cbFiltroRolConsulta.getValue();
			listaFiltrada.setPredicate(p -> {
				boolean coincideNombre = textoBuscar == null || textoBuscar.isEmpty()
						|| p.getNombreCompleto().toLowerCase().contains(textoBuscar.toLowerCase());
				boolean coincideRol = rolSeleccionado == null || rolSeleccionado.equals("Todos")
						|| p.getPerfilStr().equals(rolSeleccionado);
				return coincideNombre && coincideRol;
			});
		};

		tfBuscarUsuarioConsulta.textProperty().addListener((obs, anterior, nuevo) -> aplicarFiltro.run());
		cbFiltroRolConsulta.valueProperty().addListener((obs, anterior, nuevo) -> aplicarFiltro.run());

		cbFiltroRolConsulta
				.setItems(FXCollections.observableArrayList("Todos", "PROFESOR", "ESTUDIANTE", "TUTOREMPRESA"));
		cbFiltroRolConsulta.setValue("Todos");
		tablaUsuariosConsulta.setItems(listaFiltrada);

		tablaUsuariosConsulta.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
			if (nuevo != null) {
				mostrarInfoConsulta(nuevo);
			}
		});
	}

	private void ocultarInfoConsulta() {
		infoProfesor.setVisible(false);
		infoProfesor.setManaged(false);
		infoEstudiante.setVisible(false);
		infoEstudiante.setManaged(false);
		infoTutor.setVisible(false);
		infoTutor.setManaged(false);
	}

	private void mostrarInfoConsulta(Persona p) {
		ocultarInfoConsulta();
		switch (p.getPerfil()) {
		case PROFESOR:
			Profesor prof = (Profesor) p;
			lblNombreProf.setText(prof.getNombreCompleto());
			lblUsuarioProf.setText(prof.getUsuario());
			lblEmailProf.setText(prof.getEmail() != null ? prof.getEmail() : "—");
			lblCursoProf.setText(prof.getCurso() != null ? prof.getCurso().toString() : "Sin curso asignado");
			infoProfesor.setVisible(true);
			infoProfesor.setManaged(true);
			break;
		case ESTUDIANTE:
			Estudiante est = (Estudiante) p;
			lblNombreEst.setText(est.getNombreCompleto());
			lblUsuarioEst.setText(est.getUsuario());
			lblEmailEst.setText(est.getEmail() != null ? est.getEmail() : "—");
			lblCursoEst.setText(est.getCurso() != null ? est.getCurso().toString() : "Sin curso");
			// FE: mirar si tiene alguna FCT asignada
			if (est.getFcts() != null && !est.getFcts().isEmpty()) {
				FCT fct = est.getFcts().iterator().next();
				lblFEEst.setText(fct.getFechaInicio() + " → " + fct.getFechaFin());
				lblEmpresaEst.setText(fct.getTutor() != null && fct.getTutor().getEmpresa() != null
						? fct.getTutor().getEmpresa().getNombre()
						: "—");
			} else {
				lblFEEst.setText("Sin FE asignada");
				lblEmpresaEst.setText("—");
			}
			infoEstudiante.setVisible(true);
			infoEstudiante.setManaged(true);
			break;
		case TUTOREMPRESA:
			TutorEmpresa tutor = (TutorEmpresa) p;
			lblNombreTutorC.setText(tutor.getNombreCompleto());
			lblUsuarioTutorC.setText(tutor.getUsuario());
			lblEmpresaTutorC.setText(tutor.getEmpresa() != null ? tutor.getEmpresa().getNombre() : "—");
			int numFEs = tutor.getFcts() != null ? tutor.getFcts().size() : 0;
			lblNumFEsTutorC.setText(String.valueOf(numFEs));
			infoTutor.setVisible(true);
			infoTutor.setManaged(true);
			break;
		default:
			break;
		}
	}

}