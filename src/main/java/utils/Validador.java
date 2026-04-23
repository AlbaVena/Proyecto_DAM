package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.mindrot.jbcrypt.BCrypt;



/**
 * Clase Validador.
 * 
 * Los métodos de esta clase devuelven un Boolean en función de si coinciden
 * 
 * con unos patrones estipulados. Se usan para validar los campos de las interfaces.

 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
public class Validador {
	
	/**
	 * De 3 a 12 caracteres. Solo letras mayúsculas y minúsculas (incluyendo 'ñ'), 
	 * números del 0 al 9, y '_'.
	 */
	public final static String usuarioPasswordRegex = "^[a-zA-Z0-9ñÑ_]{3,12}$";
	
	/**
	 * De 1 a 25 caracteres. Solo letras mayúsculas y minúsculas (incluyendo 'ñ'). Admite tildes.
	 */
	public final static String nombreRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{1,25}$";	
	
	/**
	 * De 1 a 50 caracteres. Solo letras mayúsculas y minúsculas (incluyendo 'ñ'). Admite tildes.
	 */
	public final static String apellidosRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{1,50}$";	
	
	/**
	 * De 1 a 50 caracteres. Admite letras mayúsculas y minúsculas (incluyendo 'ñ'), tildes, espacios, 
	 * números del 0 al 9, y ',.ºª/-'.
	 */
	public final static String direccionRegex = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ 0-9,.ºª/-]{1,50}$";	
	
	/**
	 * Admite una cadena de numeros, que empiecen por 6/7/9, de 9 dígitos.
	 */
	public final static String telefonoRegex = "^[679][0-9]{8}$";	
	
	/**
	 * Solo admite 12 números.
	 */
	public final static String nSSRegex = "^[0-9]{12}$";	
	
	/**
	 * Admite formatos de email.
	 */
	public final static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	
	
	private static final DateTimeFormatter formFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/**
	 * Comprueba si una cadena de texo coincide con un string.
	 * 
	 * @param texto Cadena que se va a comparar.
	 * @param regex Cadena contra la que se compara.
	 * @return True si las dos cadenas coinciden.
	 */
	public static Boolean esCadenaValida(String texto, String regex) {
		return texto != null && texto.matches(regex);
	}
	
	/**
	 * Comprueba si una fecha cumple con varias condiciones:
	 * 1. La fecha de fin es anterior a la fecha de inicio.
	 * 2. La diferencia entre las dos fechas es mayor de 1 mes, pero menor de 4.
	 * 
	 * @param fechaIni Fecha de inicio.
	 * @param fechaFin Fecha de fin.
	 * @return True si se cumplen las dos condiciones.
	 */
	public static boolean esFechaValida(String fechaIni, String fechaFin) {
		LocalDate inicio = LocalDate.parse(fechaIni, formFecha);
		LocalDate fin = LocalDate.parse(fechaFin, formFecha);
		
		if (fin.isBefore(inicio)) {
			return false; //si inicio es antes que fin
		}
		
		long meses = ChronoUnit.MONTHS.between(inicio, fin);
		return meses >=1 && meses <=4 ; //validar entre 1 y 4 meses		
	}
	
	/**
	 * Comprueba si la contraseña introducida cuadra con la contraseña hasheada.
	 * 
	 * @param pass Contraseña introducida.
	 * @param passwordHasheada Versión de la contraseña guardada en la base de datos.
	 * @return True si coinciden.
	 */
	public static boolean verificarPassword(String pass, String passwordHasheada) {
        try {
            return BCrypt.checkpw(pass, passwordHasheada);
        } catch (Exception e) {
            return false;
        }
    }
	
}
