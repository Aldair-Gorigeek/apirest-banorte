package com.gorigeek.banorte.apirest.dao;

import org.springframework.data.repository.CrudRepository;

import com.gorigeek.banorte.apirest.entity.Beneficiario;

public interface IBeneficiarioDao extends CrudRepository<Beneficiario, Long>{

}
