package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.repository.SupplierRepository;

@Service
public class SupplierJpa {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SupplierRepository supplierJpa;
	
	public void insert(Supplier sup) {
		 supplierJpa.save(sup);
	} 
	
	public void update(Supplier sup) {
		 supplierJpa.save(sup);
	} 
	
	public void deleteById(Integer id) {
		supplierJpa.deleteById(id);
	}
	
	public Supplier selectOne(Integer id) {
		return entityManager.find(Supplier.class, id);
	}
	
	public Supplier selectBySupplierCode(String code) {
		return supplierJpa.findBySupplierCode(code);
	}
	
	public List<Supplier> findAll(){
		return supplierJpa.findAll();
	}
	
	public Supplier findById(Integer id) {
		Optional<Supplier> optional=supplierJpa.findById(id);
		return optional.get();
	}
	
	public List<Supplier> findByCode(Supplier code){
		List<Supplier> list= new ArrayList<Supplier>();
		list = supplierJpa.findByCode(code.getSupplierCode());
		return list;
	}
}
