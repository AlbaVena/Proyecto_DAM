package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mindrot.jbcrypt.BCrypt;

public class Transformador {

	public static LocalDate tranformarFecha(String fecha) {
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return LocalDate.parse(fecha, formateador);
	}
	
	public static String hashPassword(String pass) {
		
		return BCrypt.hashpw(pass, BCrypt.gensalt(12));
	}
	
	
	
}
