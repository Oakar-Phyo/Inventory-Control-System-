package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Department;
import com.inventorycontrolsystem.IVCS.repository.DepartmentReepository;

@Service
public class DepartmentJpa {
 
	@Autowired
	DepartmentReepository dpaJpa;
	@PersistenceContext
	EntityManager entityManager;
	
	public void insert(Department department) {
		dpaJpa.save(department);
	}

	
	public void update(Department department) {
		dpaJpa.save(department);
	}
	public void deleteById(int id) {
		dpaJpa.deleteById(id);
	}
	
	public List<Department> findAll(){
		return	dpaJpa.findAll();
		}
	
	public Department selectOne(int id) {
		return entityManager.find(Department.class, id);
	}
	public List<Department>getDepByCode(Department dep){
		List<Department>list=new ArrayList<Department>();
		list=dpaJpa.getDepbyCode(dep.getDepCode(),dep.getName());
		return list;
	}
	
	public Department selectByDepartmentCode(String code) {
		return dpaJpa.findByDepartmentCode(code);
	}
}
