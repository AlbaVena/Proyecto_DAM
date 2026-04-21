package com.alba.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.repositorios.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public List<Empresa> findAll(){
		return empresaRepository.findAll();
	}
	
	public Empresa save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	public void deleteById(Long id) {
		empresaRepository.deleteById(id);
	}
	
	public Empresa findById(Long id) {
		return empresaRepository.findById(id).orElse(null);
	}
	
	
}
