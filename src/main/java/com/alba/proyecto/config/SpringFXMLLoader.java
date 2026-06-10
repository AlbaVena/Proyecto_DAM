package com.alba.proyecto.config;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Clase SpringFXMLLoader.
 * 
 * Se encarga de cargar los archivos FXML usando Spring como
 * fábrica de controladores, lo que permite inyectar dependencias
 * en los controladores de JavaFX
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Component
public class SpringFXMLLoader {
    private final ResourceBundle resourceBundle;
    private final ApplicationContext context;

    @Autowired
    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.context = context;
    }

    /**
     * Carga un archivo FXML y devuelve su nodo raíz.
     * 
     * @param fxmlPath Ruta del archivo FXML a cargar.
     * @return Nodo raíz del archivo FXML.
     */
    public Parent load(String fxmlPath) throws IOException {      
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
