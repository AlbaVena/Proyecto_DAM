package com.alba.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.alba.proyecto.config.StageManager;
import com.alba.proyecto.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication 									//Anotacion propia de Spring
public class Tarea3Ad2024baseApplication extends Application {
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
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN);		//**Pantalla de inicio**
		//esto para cambiar de ESCENA,
	}	//de una pantalla a otra						//en este caso pantalla "LOGIN"

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}

					//***ESTA CLASE NO SE DEBERIA TOCAR***