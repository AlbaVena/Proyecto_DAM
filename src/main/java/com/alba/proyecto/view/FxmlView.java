package com.alba.proyecto.view;

import java.util.ResourceBundle;

/**
 * Enumerado FxmlView.
 * 
 * Define las vistas FXML disponibles en la aplicación con su título
 * y ruta de archivo. Se usa junto a {@link StageManager} para
 * navegar entre pantallas.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
public enum FxmlView {
	//Pantalla USER
	USER {							
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	LOGIN {							//Pantalla LOGIN
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
