package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.mindrot.jbcrypt.BCrypt;

public class Validador {
	
	public final static String usuarioPasswordRegex = "^[a-zA-Z0-9ñÑ_]{3,12}$";
	public final static String nombreRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{1,25}$";	
	public final static String apellidosRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{1,50}$";	
	public final static String direccionRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ 0-9,.ºª/-]{1,50}$";	
	public final static String telefonoRegex = "^[679][0-9]{8}$";	
	public final static String nSSRegex = "^[0-9]{12}$";	
	public final static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	
	private static final DateTimeFormatter formFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static Boolean esCadenaValida(String texto, String regex) {
		return texto != null && texto.matches(regex);
	}
	
	public static boolean esFechaValida(String fechaIni, String fechaFin) {
		LocalDate inicio = LocalDate.parse(fechaIni, formFecha);
		LocalDate fin = LocalDate.parse(fechaFin, formFecha);
		
		if (fin.isBefore(inicio)) {
			return false; //si inicio es antes que fin
		}
		
		long meses = ChronoUnit.MONTHS.between(inicio, fin);
		return meses >=1 && meses <=4 ; //validar entre 1 y 4 meses		
	}
	
	public static boolean verificarPassword(String pass, String passwordHasheada) {
        try {
            return BCrypt.checkpw(pass, passwordHasheada);
        } catch (Exception e) {
            return false;
        }
    }
	
}
