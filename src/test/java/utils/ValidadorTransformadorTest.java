package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase de pruebas unitarias para Validador y Transformador.
 * 
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
public class ValidadorTransformadorTest {

	// casos TC-001 a TC-011 — usuarioPasswordRegex

	@Test
	@DisplayName("TC-001 - Usuario válido, longitud mínima (3 caracteres)")
	public void testUsuarioLongitudMinima() {
		boolean resultado = Validador.esCadenaValida("abc", Validador.usuarioPasswordRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-002 - Usuario válido, longitud máxima (12 caracteres)")
	public void testUsuarioLongitudMaxima() {
		boolean resultado = Validador.esCadenaValida("usuario_12AB", Validador.usuarioPasswordRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-003 - Usuario con ñ minúscula")
	public void testUsuarioConNyMinuscula() {
		boolean resultado = Validador.esCadenaValida("señor", Validador.usuarioPasswordRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-004 - Usuario con Ñ mayúscula")
	public void testUsuarioConNyMayuscula() {
		boolean resultado = Validador.esCadenaValida("SEÑOR_1", Validador.usuarioPasswordRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-005 - Usuario demasiado corto (2 caracteres)")
	public void testUsuarioDemisiadoCorto() {
		boolean resultado = Validador.esCadenaValida("ab", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-006 - Usuario demasiado largo (13 caracteres)")
	public void testUsuarioDemisiadoLargo() {
		boolean resultado = Validador.esCadenaValida("usuario_12ABC", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-007 - Usuario con espacio en medio")
	public void testUsuarioConEspacio() {
		boolean resultado = Validador.esCadenaValida("us er", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-008 - Usuario con carácter especial no permitido (@)")
	public void testUsuarioConArroba() {
		boolean resultado = Validador.esCadenaValida("user@1", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-009 - Usuario con tilde (no permitida)")
	public void testUsuarioConTilde() {
		boolean resultado = Validador.esCadenaValida("usuário", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-010 - Usuario null")
	public void testUsuarioNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-011 - Usuario cadena vacía")
	public void testUsuarioVacio() {
		boolean resultado = Validador.esCadenaValida("", Validador.usuarioPasswordRegex);
		assertFalse(resultado);
	}

	// casos TC-012 a TC-019 — nombreRegex

	@Test
	@DisplayName("TC-012 - Nombre válido simple")
	public void testNombreValido() {
		boolean resultado = Validador.esCadenaValida("Maria", Validador.nombreRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-013 - Nombre válido con tilde")
	public void testNombreConTilde() {
		boolean resultado = Validador.esCadenaValida("José", Validador.nombreRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-014 - Nombre válido con ñ")
	public void testNombreConNy() {
		boolean resultado = Validador.esCadenaValida("Niño", Validador.nombreRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-015 - Nombre válido con espacio (dos palabras)")
	public void testNombreConEspacio() {
		boolean resultado = Validador.esCadenaValida("Ana Maria", Validador.nombreRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-016 - Nombre con número (no permitido)")
	public void testNombreConNumero() {
		boolean resultado = Validador.esCadenaValida("Ana1", Validador.nombreRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-017 - Nombre vacío")
	public void testNombreVacio() {
		boolean resultado = Validador.esCadenaValida("", Validador.nombreRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-018 - Nombre de 26 caracteres (límite superado)")
	public void testNombreDemisiadoLargo() {
		boolean resultado = Validador.esCadenaValida("Abcdefghijklmnopqrstuvwxyz", Validador.nombreRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-019 - Nombre null")
	public void testNombreNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.nombreRegex);
		assertFalse(resultado);
	}

	// casos TC-020 a TC-026 — emailRegex

	@Test
	@DisplayName("TC-020 - Email válido formato estándar")
	public void testEmailValido() {
		boolean resultado = Validador.esCadenaValida("usuario@gmail.com", Validador.emailRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-021 - Email válido con subdominio")
	public void testEmailConSubdominio() {
		boolean resultado = Validador.esCadenaValida("alba@correo.edu.es", Validador.emailRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-022 - Email sin arroba")
	public void testEmailSinArroba() {
		boolean resultado = Validador.esCadenaValida("usuariogmail.com", Validador.emailRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-023 - Email sin dominio")
	public void testEmailSinDominio() {
		boolean resultado = Validador.esCadenaValida("usuario@", Validador.emailRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-024 - Email sin extensión")
	public void testEmailSinExtension() {
		boolean resultado = Validador.esCadenaValida("usuario@gmail", Validador.emailRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-025 - Email vacío")
	public void testEmailVacio() {
		boolean resultado = Validador.esCadenaValida("", Validador.emailRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-026 - Email null")
	public void testEmailNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.emailRegex);
		assertFalse(resultado);
	}

	// casos TC-027 a TC-034 — telefonoRegex

	@Test
	@DisplayName("TC-027 - Teléfono válido empezando por 6")
	public void testTelefonoPor6() {
		boolean resultado = Validador.esCadenaValida("612345678", Validador.telefonoRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-028 - Teléfono válido empezando por 7")
	public void testTelefonoPor7() {
		boolean resultado = Validador.esCadenaValida("712345678", Validador.telefonoRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-029 - Teléfono válido empezando por 9")
	public void testTelefonoPor9() {
		boolean resultado = Validador.esCadenaValida("912345678", Validador.telefonoRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-030 - Teléfono empezando por 8 (no permitido)")
	public void testTelefonoPor8() {
		boolean resultado = Validador.esCadenaValida("812345678", Validador.telefonoRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-031 - Teléfono de 8 dígitos (corto)")
	public void testTelefonoCorto() {
		boolean resultado = Validador.esCadenaValida("61234567", Validador.telefonoRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-032 - Teléfono de 10 dígitos (largo)")
	public void testTelefonoLargo() {
		boolean resultado = Validador.esCadenaValida("6123456789", Validador.telefonoRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-033 - Teléfono con letras")
	public void testTelefonoConLetras() {
		boolean resultado = Validador.esCadenaValida("6A2345678", Validador.telefonoRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-034 - Teléfono null")
	public void testTelefonoNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.telefonoRegex);
		assertFalse(resultado);
	}

	// casos TC-035 a TC-039 — nSSRegex

	@Test
	@DisplayName("TC-035 - NSS válido (12 dígitos)")
	public void testNSSValido() {
		boolean resultado = Validador.esCadenaValida("123456789012", Validador.nSSRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-036 - NSS con 11 dígitos")
	public void testNSSCorto() {
		boolean resultado = Validador.esCadenaValida("12345678901", Validador.nSSRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-037 - NSS con 13 dígitos")
	public void testNSSLargo() {
		boolean resultado = Validador.esCadenaValida("1234567890123", Validador.nSSRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-038 - NSS con letras")
	public void testNSSConLetras() {
		boolean resultado = Validador.esCadenaValida("12345678901A", Validador.nSSRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-039 - NSS null")
	public void testNSSNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.nSSRegex);
		assertFalse(resultado);
	}

	// casos TC-040 a TC-045 — apellidosRegex y direccionRegex

	@Test
	@DisplayName("TC-040 - Apellidos válidos con tilde")
	public void testApellidosValidos() {
		boolean resultado = Validador.esCadenaValida("García López", Validador.apellidosRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-041 - Apellidos con número (no permitido)")
	public void testApellidosConNumero() {
		boolean resultado = Validador.esCadenaValida("García2", Validador.apellidosRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-042 - Apellidos null")
	public void testApellidosNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.apellidosRegex);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-043 - Dirección válida con número y coma")
	public void testDireccionValida() {
		boolean resultado = Validador.esCadenaValida("Calle Mayor, 12", Validador.direccionRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-044 - Dirección válida con barra")
	public void testDireccionConBarra() {
		boolean resultado = Validador.esCadenaValida("Avda. España s/n", Validador.direccionRegex);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-045 - Dirección null")
	public void testDireccionNull() {
		boolean resultado = Validador.esCadenaValida(null, Validador.direccionRegex);
		assertFalse(resultado);
	}

	// casos TC-046 a TC-052 — esFechaValida()

	@Test
	@DisplayName("TC-046 - Fechas válidas (diferencia de 2 meses)")
	public void testFechaValida2Meses() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "01/03/2025");
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-047 - Fechas válidas (diferencia exacta de 1 mes)")
	public void testFechaValida1Mes() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "01/02/2025");
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-048 - Fechas válidas (diferencia exacta de 4 meses)")
	public void testFechaValida4Meses() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "01/05/2025");
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-049 - Fecha fin anterior a inicio")
	public void testFechaFinAnterior() {
		boolean resultado = Validador.esFechaValida("01/03/2025", "01/01/2025");
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-050 - Diferencia menor de 1 mes")
	public void testFechaMenosDe1Mes() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "15/01/2025");
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-051 - Diferencia mayor de 4 meses")
	public void testFechaMasDe4Meses() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "01/06/2025");
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-052 - Fechas iguales (diferencia 0)")
	public void testFechasIguales() {
		boolean resultado = Validador.esFechaValida("01/01/2025", "01/01/2025");
		assertFalse(resultado);
	}

	// casos TC-053 a TC-055 — verificarPassword()

	@Test
	@DisplayName("TC-053 - Contraseña correcta contra su hash BCrypt")
	public void testVerificarPasswordCorrecta() {
		String hash = BCrypt.hashpw("pass", BCrypt.gensalt());
		boolean resultado = Validador.verificarPassword("pass", hash);
		assertTrue(resultado);
	}

	@Test
	@DisplayName("TC-054 - Contraseña incorrecta contra hash ajeno")
	public void testVerificarPasswordIncorrecta() {
		String hash = BCrypt.hashpw("pass", BCrypt.gensalt());
		boolean resultado = Validador.verificarPassword("otra", hash);
		assertFalse(resultado);
	}

	@Test
	@DisplayName("TC-055 - Hash malformado (no lanza excepción, devuelve false)")
	public void testVerificarPasswordHashMalformado() {
		boolean resultado = Validador.verificarPassword("pass", "noesunahash");
		assertFalse(resultado);
	}

	// casos TC-056 a TC-060 — Transformador
	@Test
	@DisplayName("TC-056 - Convertir String a LocalDate formato dd/MM/yyyy")
	public void testTransformarFecha() {
		LocalDate resultado = Transformador.tranformarFecha("15/03/2025");
		assertEquals(LocalDate.of(2025, 3, 15), resultado);
	}

	@Test
	@DisplayName("TC-057 - Convertir LocalDate a String formato dd-MM-yyyy")
	public void testTransformarFechaAString() {
		String resultado = Transformador.transformarFechaAString(LocalDate.of(2025, 3, 15));
		assertEquals("15-03-2025", resultado);
	}

	@Test
	@DisplayName("TC-058 - hashPassword devuelve String no nulo y no vacío")
	public void testHashPasswordNoNulo() {
		String resultado = Transformador.hashPassword("pass");
		assertNotNull(resultado);
		assertFalse(resultado.isEmpty());
	}

	@Test
	@DisplayName("TC-059 - hashPassword produce hash verificable con BCrypt")
	public void testHashPasswordVerificable() {
		String hash = Transformador.hashPassword("pass");
		boolean verificado = BCrypt.checkpw("pass", hash);
		assertTrue(verificado);
	}

	@Test
	@DisplayName("TC-060 - Dos hashes del mismo password son distintos (salt distinto)")
	public void testHashPasswordDistintos() {
		String hash1 = Transformador.hashPassword("pass");
		String hash2 = Transformador.hashPassword("pass");
		assertNotEquals(hash1, hash2);
	}

}
