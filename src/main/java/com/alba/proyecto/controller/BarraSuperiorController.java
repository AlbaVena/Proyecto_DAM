package com.alba.proyecto.controller;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BarraSuperiorController extends HBox {

    @FXML private Label lblRol;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnLogOut;

    public BarraSuperiorController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BarraSuperior.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setDatosUsuario(String rol, String nombre) {
        lblRol.setText(rol);
        lblNombreUsuario.setText(nombre);
    }

    public Button getBtnLogOut() {
        return btnLogOut;
    }
}
