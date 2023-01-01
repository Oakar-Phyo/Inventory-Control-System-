package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.UserRole;
import com.inventorycontrolsystem.IVCS.repository.UserRoleRepository;

@Service
public class UserRoleJpa {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Autowired
	UserRoleRepository userRoleJpa;
	
	public List<UserRole> findAll(){
	return	userRoleJpa.findAll();
	}
	
	public UserRole findById(Integer id) {
		Optional<UserRole> optional=userRoleJpa.findById(id);
		return optional.get();
	}
	
	public List<UserRole> getByName(UserRole rol){
		List<UserRole> list = new ArrayList<UserRole>();
		list = userRoleJpa.getUserRoleByName(rol.getName());
		return list;
		
	}
	
	
	public void update(UserRole role) {
		userRoleJpa.save(role);
	}
	public void insert(UserRole role) {
		userRoleJpa.save(role);
	}
	public void deleteById(Integer id) {
		userRoleJpa.deleteById(id);
	}
	public UserRole selectOne(Integer id) {
		return entityManager.find(UserRole.class, id);
	}

}
