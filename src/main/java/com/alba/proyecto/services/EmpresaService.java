package com.alba.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.repositorios.EmpresaRepository;

/**
 * Clase EmpresaService.
 * 
 * Servicio para la gestión de {@link Empresa}.
 * capa intermedia entre los controladores
 * y {@link EmpresaRepository}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public List<Empresa> findAll(){
		return empresaRepository.findAll();
	}
	
	@SuppressWarnings("null")
	public Empresa save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	@SuppressWarnings("null")
	public void deleteById(Long id) {
		empresaRepository.deleteById(id);
	}
	
	@SuppressWarnings("null")
	public Empresa findById(Long id) {
		return empresaRepository.findById(id).orElse(null);
	}
	
	
}
