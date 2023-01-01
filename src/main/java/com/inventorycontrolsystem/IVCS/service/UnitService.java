package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Unit;
import com.inventorycontrolsystem.IVCS.repository.UnitRepository;

@Service
public class UnitService {
	
	@Autowired
	UnitRepository unitRespository;
	@PersistenceContext
	EntityManager entityManager;
	
	
	//Insert
	 
	public void insert(Unit unit) {
		unitRespository.save(unit);
	}
	
	//Delete
	
	public void delete( int id) {
		unitRespository.deleteById(id);
	}
	
	//Update
	public void update(Unit unit) {
		unitRespository.save(unit);
	
	}
	
	//Find All
	public List<Unit >getAll(){
		List<Unit>list=unitRespository.findAll();
		return list;
	}
	//select one
	public Unit selectOne(int id) {
		return entityManager.find(Unit.class, id);
	}
	public List<Unit>getUnitByCode(Unit unit){
		List<Unit>list=new ArrayList<Unit>();
				list=unitRespository.getUnitbyCode(unit.getUnitCode(),unit.getName());
				return list;
		
	}
	public Unit findById(Integer id) {
		Optional<Unit> optional=unitRespository.findById(id);
		return optional.get();
	}
}
