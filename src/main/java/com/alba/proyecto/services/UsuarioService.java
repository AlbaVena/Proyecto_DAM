package com.alba.proyecto.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void crearEstudiante(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, String nSS, Curso curso, FCT fct) {
		Estudiante estudiante = new Estudiante(usuario, contraseña, nombre, apellidos, email, telefono, perfil, nSS,
				curso, fct);
		guardarUsuario(estudiante);
	}

	public void crearProfesor(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, Curso curso) {
		Profesor profesor = new Profesor(usuario, contraseña, nombre, apellidos, email, telefono, perfil, curso);
		guardarUsuario(profesor);
	}

	public void crearTutorEmpresa(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil, Empresa empresa, Set<FCT> fcts) {
		TutorEmpresa tutor = new TutorEmpresa(usuario, contraseña, nombre, apellidos, email, telefono, perfil, empresa,
				fcts);
		guardarUsuario(tutor);
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

}
