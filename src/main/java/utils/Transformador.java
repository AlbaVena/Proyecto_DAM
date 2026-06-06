package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mindrot.jbcrypt.BCrypt;



/**
 * Clase Transformador. En esta clase se implementan los métodos que se usan para
 * 
 * transformar una contraseña de usuario para guardarla de forma segura en la base de datos, 
 * 
 * y para transformar el formato de dechas a uno más legible.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
public class Transformador {

	/*
	 * Cambia un formato de fecha a uno mas legible.
	 */
	public static LocalDate tranformarFecha(String fecha) {
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return LocalDate.parse(fecha, formateador);
	}
	
	/**
	 * pasa una fecha a String legible
	 * @param fecha
	 * @return
	 */
	public static String transformarFechaAString(LocalDate fecha) {
	    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    return fecha.format(formateador);
	}
	
	/**
	 * Hashea una contraseña para guardarla en la base de datos
	 * mediante {@link BCrypt}.
	 * 
	 * @param pass Contraseña recibida.
	 * @return Contraseña hasheada.
	 */
	public static String hashPassword(String pass) {
		
		return BCrypt.hashpw(pass, BCrypt.gensalt(12));
	}
	
	
	
}
