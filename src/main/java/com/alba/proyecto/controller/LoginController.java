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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

@Controller
public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordFieldValidado password;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Label lblError;
    
    @FXML 
    private Hyperlink lblOlvido;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConfigurableApplicationContext context;

    @FXML
    public void initialize() {
        btnLogin.setOnAction(this::handleLogin);
        
     // limpiar el error cuando el usuario empiece a escribir
        username.textProperty().addListener((obs, anterior, nuevo) -> ocultarError());
        password.textProperty().addListener((obs, anterior, nuevo) ->{
        	lblError.setVisible(false);
            lblError.setManaged(false);
            username.setStyle("");
        }
        );

        // lblOlvido — de momento solo un mensaje informativo
        lblOlvido.setOnAction(event -> {
            lblError.setText("Para recuperar tu contraseña, contacta con el admin.");
            lblError.setVisible(true);
            lblError.setManaged(true);
        });
        
     // permitir login pulsando Enter
        username.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                handleLogin(null);
            }
        });
        password.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                handleLogin(null);
            }
        });
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
        	mostrarError("Usuario o contraseña incorrectos");
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
    
    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        lblError.setManaged(true);
        username.setStyle("-fx-border-color: #c0392b;");
       
    }

    private void ocultarError() {
        lblError.setVisible(false);
        lblError.setManaged(false);
        username.setStyle("");
       
    }
}