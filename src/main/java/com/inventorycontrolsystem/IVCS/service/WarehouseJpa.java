package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.WarehouseRepository;

@Service
public class WarehouseJpa {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	WarehouseRepository warehouseJpa;
	
	public void insert(Warehouse war) {
		 warehouseJpa.save(war);
	} 
	
	public void update(Warehouse war) {
		warehouseJpa.save(war);
	} 
	
	public void deleteById(Integer id) {
		warehouseJpa.deleteById(id);
	}
	
	public Warehouse selectOne(Integer id) {
		return entityManager.find(Warehouse.class, id);
	}
	public Warehouse selectOneByName(String name) {
		return warehouseJpa.findByName(name);
	}
	

	public Warehouse selectByWarehouseCode(String code) {
		return warehouseJpa.findByWarehouseCode(code);
	}
	
	public List<Warehouse> findAll(){
		return warehouseJpa.findAll();
	}
	
	public Warehouse findById(Integer id) {
		Optional<Warehouse> optional=warehouseJpa.findById(id);
		return optional.get();
	}
	
	public List<Warehouse> findByCode(Warehouse code){
		List<Warehouse> list= new ArrayList<Warehouse>();
		list = warehouseJpa.findByCode(code.getWarehouseCode());
		return list;
	}
	
}
