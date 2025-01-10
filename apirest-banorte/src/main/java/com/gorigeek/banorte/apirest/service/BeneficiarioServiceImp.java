package com.gorigeek.banorte.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorigeek.banorte.apirest.dao.IBeneficiarioDao;
import com.gorigeek.banorte.apirest.entity.Beneficiario;

@Service
public class BeneficiarioServiceImp implements IBeneficiarioService{
	
	@Autowired
	private IBeneficiarioDao dao;

	public List<Beneficiario> findAll() {
		// TODO Auto-generated method stub
		return (List<Beneficiario>) dao.findAll();
	}

}
