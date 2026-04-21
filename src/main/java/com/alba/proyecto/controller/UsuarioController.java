package com.alba.proyecto.controller;

import org.springframework.stereotype.Controller;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.TipoCurso;
import com.alba.proyecto.services.UsuarioService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import utils.Transformador;
import utils.Validador;

@Controller
public class UsuarioController {

	// TODO HAY QUE AÑADIR ANOTACIONES

	@FXML
	private TextField nombre;

	@FXML private TextField apellidos;

	@FXML private TextField nombreUsuario;

	@FXML private PasswordField password;

	@FXML private TextField email;

	@FXML private TextField telefono;

	@FXML private TextField nss;

	@FXML private RadioButton perfil; // se necesita para decidir qué metodo crear o modificar se usará

	@FXML private ComboBox<Curso> curso;
	
	@FXML private ComboBox<TipoCurso> modulo;

	@FXML private UsuarioService usuarioService;
	

	@FXML 
	public void nuevoEstudiante(ActionEvent event) {

		Alert alert = new Alert(AlertType.ERROR);

		String usuarioStr = nombreUsuario.getText();
		String passStr = password.getText();

		if (!Validador.esCadenaValida(usuarioStr, Validador.usuarioPasswordRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Usuario no válido. El formato del usuario es incorrecto.");
			nombreUsuario.requestFocus();
			alert.showAndWait();
			return; // Bloqueamos el avance
		}
		if (!Validador.esCadenaValida(passStr, Validador.usuarioPasswordRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Contraseña no válida. El formato del contraseña es incorrecto.");
			password.requestFocus();
			alert.showAndWait();
			return;
		} else {
			passStr = Transformador.hashPassword(passStr);
		}

		String emailStr = email.getText();
		if (!Validador.esCadenaValida(emailStr, Validador.emailRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Email no válido. El formato del email es incorrecto.");
			email.requestFocus();
			alert.showAndWait();
			return;
		}

		String nombreStr = nombre.getText();
		if (!Validador.esCadenaValida(nombreStr, Validador.nombreRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Nombre no válido. El formato del nombre es incorrecto.");
			nombre.requestFocus();
			alert.showAndWait();
			return;
		}

		String apellidosStr = apellidos.getText();
		if (!Validador.esCadenaValida(apellidosStr, Validador.apellidosRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Apellidos no válidos. El formato de los apellidos es incorrecto.");
			apellidos.requestFocus();
			alert.showAndWait();
			return;
		}

		String telefonoStr = telefono.getText();
		if (!Validador.esCadenaValida(telefonoStr, Validador.telefonoRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Numero de telefono no válido. El formato del telefono es incorrecto.");
			telefono.requestFocus();
			alert.showAndWait();
			return;
		}

		String nssStr = nss.getText();
		if (!Validador.esCadenaValida(nssStr, Validador.nSSRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Numero de Seguridad Social no válido. El formato del numero es incorrecto.");
			nss.requestFocus();
			alert.showAndWait();
			return;
		}
		Curso cursoStr = curso.getValue();
		if (cursoStr == null) {
			alert.setTitle("Campo obligatorio");
			alert.setContentText("Debes seleccionar un curso de la lista.");
			curso.requestFocus();
			alert.showAndWait();
			return;
		}

		usuarioService.crearEstudiante(usuarioStr, passStr, nombreStr, apellidosStr, emailStr, telefonoStr,
				Perfil.ESTUDIANTE, nssStr, cursoStr);

	}

	@FXML 
	public void nuevoProfesor(ActionEvent event) {

		Alert alert = new Alert(AlertType.ERROR);

		String usuarioStr = nombreUsuario.getText();
		String passStr = password.getText();

		if (!Validador.esCadenaValida(usuarioStr, Validador.usuarioPasswordRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Usuario no válido. El formato del usuario es incorrecto.");
			nombreUsuario.requestFocus();
			alert.showAndWait();
			return; // Bloqueamos el avance
		}
		if (!Validador.esCadenaValida(passStr, Validador.usuarioPasswordRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Contraseña no válida. El formato del contraseña es incorrecto.");
			password.requestFocus();
			alert.showAndWait();
			return;
		} else {
			passStr = Transformador.hashPassword(passStr);
		}

		String emailStr = email.getText();
		if (!Validador.esCadenaValida(emailStr, Validador.emailRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Email no válido. El formato del email es incorrecto.");
			email.requestFocus();
			alert.showAndWait();
			return;
		}

		String nombreStr = nombre.getText();
		if (!Validador.esCadenaValida(nombreStr, Validador.nombreRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Nombre no válido. El formato del nombre es incorrecto.");
			nombre.requestFocus();
			alert.showAndWait();
			return;
		}

		String apellidosStr = apellidos.getText();
		if (!Validador.esCadenaValida(apellidosStr, Validador.apellidosRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Apellidos no válidos. El formato de los apellidos es incorrecto.");
			apellidos.requestFocus();
			alert.showAndWait();
			return;
		}

		String telefonoStr = telefono.getText();
		if (!Validador.esCadenaValida(telefonoStr, Validador.telefonoRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Numero de telefono no válido. El formato del telefono es incorrecto.");
			telefono.requestFocus();
			alert.showAndWait();
			return;
		}

		String nssStr = nss.getText();
		if (!Validador.esCadenaValida(nssStr, Validador.nSSRegex)) {
			alert.setTitle("Error de validacion");
			alert.setContentText("Numero de Seguridad Social no válido. El formato del numero es incorrecto.");
			nss.requestFocus();
			alert.showAndWait();
			return;
		}
		Curso cursoStr = curso.getValue();
		if (cursoStr == null) {
			alert.setTitle("Campo obligatorio");
			alert.setContentText("Debes seleccionar un curso de la lista.");
			curso.requestFocus();
			alert.showAndWait();
			return;
		}

		usuarioService.crearProfesor(usuarioStr, passStr, nombreStr, apellidosStr, emailStr, telefonoStr,
				Perfil.PROFESOR, cursoStr);

	}

}
