package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inventorycontrolsystem.IVCS.model.Permission;


@Repository
@Transactional
public class PermissionJpa {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public void delete(String name) {
		List<Permission> list=select(name);
		for(Permission p:list) {
			entityManager.remove(p);
		}
	}
	public List<Permission> select(String name){
		TypedQuery<Permission> query=entityManager.createQuery("from Permission p where p.email=?1", Permission.class);
		query.setParameter(1, name);
		return query.getResultList();
	}
	
	public void save(Permission permission) {
		entityManager.persist(permission);
	}

	public void update(Permission p) {
		entityManager.merge(p);
		
	}
}
