package com.gorigeek.banorte.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gorigeek.banorte.apirest.entity.Beneficiario;
import com.gorigeek.banorte.apirest.service.BeneficiarioServiceImp;

@RestController
public class IndexController {
	
	@Autowired
	private BeneficiarioServiceImp service;
	
	@GetMapping("/")
	public ResponseEntity<?> index(){
		Map<String, List<Beneficiario>> response = new HashMap<>();
		response.put("Consulta a todos los beneficiarios de la tabla t_beneficiarios", service.findAll());
		
		return new ResponseEntity<Map<String,?>>(response, HttpStatus.ACCEPTED);
	}
	
}
