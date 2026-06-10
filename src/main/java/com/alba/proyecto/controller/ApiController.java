package com.alba.proyecto.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.services.Sesion;
import com.alba.proyecto.services.UsuarioService;

import utils.Transformador;
import utils.Validador;

import java.util.HashMap;
import java.util.Map;

/**
  * Clase ApiController.
 * 
 * Controlador REST que expone los endpoints consumidos por la
 * aplicación móvil de Gestiona. Solo permite el acceso a estudiantes.
 * 
 * la app de escritorio actua como servidor, al que la app movil envia usuario y contraseña
 * éste recibe la peticion y si es correcta (estudiante) devuelve los datos.
 *
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
    private UsuarioService usuarioService;


	
    /**
     * Endpoint de login para la app móvil.
     * Recibe usuario y contraseña, devuelve los datos del estudiante si es correcto.
     * Representa una respuesta
     * 
     * @param credenciales @RequestBody lo que la app envia al "servidor"
     * @return ResponseEntity (representa una respuesta HTTP)
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciales) {

        String usuario = credenciales.get("usuario");
        String contrasena = credenciales.get("contrasena");

        Map<String, Object> respuesta = new HashMap<String, Object>();

        // validar que los campos no están vacíos
        if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
            respuesta.put("error", "Usuario y contraseña son obligatorios");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta); //400
        }

        // intentar login
        Persona persona = usuarioService.login(usuario, contrasena);

        if (persona == null) {
            respuesta.put("error", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta); //401
        }

        // solo permitir acceso a estudiantes
        if (persona.getPerfil() != Perfil.ESTUDIANTE) {
            respuesta.put("error", "Solo los estudiantes pueden acceder a la app móvil");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(respuesta); //403 prohibido
        }

        Estudiante estudiante = (Estudiante) persona;

        // construye respuesta con los datos del estudiante
        respuesta.put("id", estudiante.getId());
        respuesta.put("nombre", estudiante.getNombre());
        respuesta.put("apellidos", estudiante.getApellidos());
        respuesta.put("email", estudiante.getEmail() != null ? estudiante.getEmail() : "");
        respuesta.put("telefono", estudiante.getTelefono() != null ? estudiante.getTelefono() : "");
        respuesta.put("usuario", estudiante.getUsuario());
        respuesta.put("curso", estudiante.getCurso() != null ? estudiante.getCurso().toString() : "Sin curso");
        respuesta.put("nss", estudiante.getnSS());

        // datos de la FE si tiene una asignada
        if (estudiante.getFcts() != null && !estudiante.getFcts().isEmpty()) {
            FCT fct = estudiante.getFcts().iterator().next();
            respuesta.put("empresa", fct.getTutor() != null && fct.getTutor().getEmpresa() != null
                    ? fct.getTutor().getEmpresa().getNombre() : "Sin empresa");
            respuesta.put("tutorEmpresa", fct.getTutor() != null
                    ? fct.getTutor().getNombreCompleto() : "Sin tutor");
            respuesta.put("fechaInicio", fct.getFechaInicio() != null
                    ? Transformador.transformarFechaAString(fct.getFechaInicio()) : "");
            respuesta.put("fechaFin", fct.getFechaFin() != null
                    ? Transformador.transformarFechaAString(fct.getFechaFin()) : "");
            respuesta.put("periodo", fct.getPeriodo() != null
                    ? fct.getPeriodo().toString() : "");
        } else {
        	//si no la tiene
        	respuesta.put("empresa", "Sin FE asignada");
            respuesta.put("tutorEmpresa", "");
            respuesta.put("fechaInicio", "");
            respuesta.put("fechaFin", "");
            respuesta.put("periodo", "");
        }

        return ResponseEntity.ok(respuesta);
    }
	
	
}
