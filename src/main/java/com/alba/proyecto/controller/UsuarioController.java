package com.alba.proyecto.controller;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.TipoCurso;
import com.alba.proyecto.services.UsuarioService;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import utils.Transformador;
import utils.Validador;

public class UsuarioController {

	// TODO HAY QUE AÑADIR ANOTACIONES

	private TextField nombre;

	private TextField apellidos;

	private TextField nombreUsuario;

	private PasswordField password;

	private TextField email;

	private TextField telefono;

	private TextField nss;

	private RadioButton perfil; // se necesita para decidir qué metodo crear o modificar se usará

	private ComboBox<Curso> curso;
	
	private ComboBox<TipoCurso> modulo;

	private UsuarioService usuarioService;

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
