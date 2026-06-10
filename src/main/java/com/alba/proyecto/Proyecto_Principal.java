package com.alba.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.alba.proyecto.config.StageManager;
import com.alba.proyecto.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase Proyecto_Principal.
 * 
 * Punto de entrada de la aplicación. Combina Spring Boot con JavaFX,
 * arrancando el contexto de Spring en el método init() y lanzando
 * la interfaz gráfica en start(). La primera pantalla que se muestra
 * es el Login.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@SpringBootApplication 									//Anotacion propia de Spring
public class Proyecto_Principal extends Application {
												//Interfaz que lanza un aplicativo
	
	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		displayInitialScene();

	}

	/**
	 * Establece la pantalla inicial de la aplicación.
	 * Se muestra la pantalla de Login al arrancar.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN);		//**Pantalla de inicio**
		
		//esto para cambiar de ESCENA,
	}	//de una pantalla a otra						//en este caso pantalla "LOGIN"

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Proyecto_Principal.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}

					//***ESTA CLASE NO SE DEBERIA TOCAR***