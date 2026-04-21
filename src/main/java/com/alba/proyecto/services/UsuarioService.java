package com.alba.proyecto.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alba.proyecto.modelo.Administrador;
import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.Perfil;
import com.alba.proyecto.modelo.Persona;
import com.alba.proyecto.modelo.Profesor;
import com.alba.proyecto.modelo.TutorEmpresa;
import com.alba.proyecto.repositorios.AdministradorRepository;
import com.alba.proyecto.repositorios.EstudianteRepository;
import com.alba.proyecto.repositorios.ProfesorRepository;
import com.alba.proyecto.repositorios.TutorEmpresaRepository;

import utils.Validador;

@Service
public class UsuarioService {

	@Autowired
	private AdministradorRepository administradorRepository;

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private TutorEmpresaRepository tutorEmpresaRepository;

	@Autowired
	private EstudianteRepository estudianteRepository;

	public Estudiante crearEstudiante(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, String nSS, Curso curso) {
		Estudiante estudiante = new Estudiante(usuario, contraseña, nombre, apellidos, email, telefono, perfil, nSS,
				curso);
		guardarUsuario(estudiante);
		return estudiante;
	}

	public Profesor crearProfesor(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, Curso curso) {
		Profesor profesor = new Profesor(usuario, contraseña, nombre, apellidos, email, telefono, perfil, curso);
		guardarUsuario(profesor);
		return profesor;
	}

	public TutorEmpresa crearTutorEmpresa(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, Empresa empresa, Set<FCT> fcts) {
		TutorEmpresa tutor = new TutorEmpresa(usuario, contraseña, nombre, apellidos, email, telefono, perfil, empresa,
				fcts);
		guardarUsuario(tutor);
		return tutor;
	}

	private void guardarUsuario(Persona nueva) {
		if (nueva.getPerfil() == Perfil.PROFESOR) {
			Profesor profesor = (Profesor) nueva;
			profesorRepository.save(profesor);
		} else if (nueva.getPerfil() == Perfil.ESTUDIANTE) {
			Estudiante estudiante = (Estudiante) nueva;
			estudianteRepository.save(estudiante);
		} else if (nueva.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) nueva;
			tutorEmpresaRepository.save(tutor);
		}
	}

	public void modificarUsuario(Persona nueva) {
		// TODO de momento igual qeu nuevoUsuario
		if (nueva.getPerfil() == Perfil.PROFESOR) {
			Profesor profesor = (Profesor) nueva;
			profesorRepository.save(profesor);
		} else if (nueva.getPerfil() == Perfil.ESTUDIANTE) {
			Estudiante estudiante = (Estudiante) nueva;
			estudianteRepository.save(estudiante);
		} else if (nueva.getPerfil() == Perfil.TUTOREMPRESA) {
			TutorEmpresa tutor = (TutorEmpresa) nueva;
			tutorEmpresaRepository.save(tutor);
		}
	}
	
	public Persona login(String usuario, String contrasena) {
		Persona persona = null;
		
		Optional<Administrador> admin = administradorRepository.findByUsuario(usuario);
	    if (admin.isPresent()) {
	        persona = admin.get();
	    }
	 // buscamos profesor
	    if (persona == null) {
	        Optional<Profesor> profesor = profesorRepository.findByUsuario(usuario);
	        if (profesor.isPresent()) {
	            persona = profesor.get();
	        }
	    }

	    // buscamos estudiante
	    if (persona == null) {
	        Optional<Estudiante> estudiante = estudianteRepository.findByUsuario(usuario);
	        if (estudiante.isPresent()) {
	            persona = estudiante.get();
	        }
	    }

	    // buscamos tutor
	    if (persona == null) {
	        Optional<TutorEmpresa> tutor = tutorEmpresaRepository.findByUsuario(usuario);
	        if (tutor.isPresent()) {
	            persona = tutor.get();
	        }
	    }
	    if (persona == null) {
	        return null; // usuario no existe
	    }

	    // verificamos contraseña con BCrypt
	    if (Validador.verificarPassword(contrasena, persona.getContraseña())) {
	        return persona;
	    }

	    return null; // contraseña incorrecta
	}

}
