package com.alba.proyecto.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.services.UsuarioService;

import componentes.PasswordFieldValidado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordFieldValidado password;

    @FXML
    private Button btnLogin;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConfigurableApplicationContext context;

    @FXML
    public void initialize() {
        btnLogin.setOnAction(this::handleLogin);
    }

    private void handleLogin(ActionEvent event) {

        String usuario = username.getText();
        String contrasena = password.getText();

        Persona persona = usuarioService.login(usuario, contrasena);

        if (persona != null) {
            switch (persona.getPerfil()) {
                case ADMINISTRADOR:
                    cargarPantalla("/fxml/MenuAdmin2.fxml");
                    break;
                case PROFESOR:
                    cargarPantalla("/fxml/MenuProfesor.fxml");
                    break;
                case ESTUDIANTE:
                    cargarPantalla("/fxml/MenuEstudiante.fxml");
                    break;
                case TUTOREMPRESA:
                    cargarPantalla("/fxml/MenuTutorEmpresa.fxml");
                    break;
                default:
                    System.out.println("Perfil no reconocido.");
            }
        } else {
            username.setStyle("-fx-border-color: red;");
            password.setStyle("-fx-border-color: red;");
        }
    }

    private void cargarPantalla(String rutaFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + rutaFxml);
        }
    }
}