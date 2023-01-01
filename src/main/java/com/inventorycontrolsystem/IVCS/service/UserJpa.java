package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inventorycontrolsystem.IVCS.formmodel.UserForm;
import com.inventorycontrolsystem.IVCS.model.User;
import com.inventorycontrolsystem.IVCS.repository.UserRepository;

@Repository
@Transactional
public class UserJpa {
	
	@Autowired
	UserRepository userRepo;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public User selectForUser(String email) {
		TypedQuery<User> query = entityManager.createQuery("from User a where a.email=?1", User.class);
		query.setParameter(1, email);
		return query.getSingleResult();
	}
	
	public List<String> selectForSecurity() {
		TypedQuery<User> query = entityManager.createQuery("from User", User.class);
		List<User> role = query.getResultList();
		List<String> list = new ArrayList<String>();
		list.add("User");

		for (int i = 0; i < role.size(); i++) {
			list.add(role.get(i).getRole().getName());
		}

		return list;
	}

	
	public List<User> getByEmail(@Valid UserForm user){
		List<User> list = new ArrayList<User>();
		list = userRepo.findByEmail(user.getEmail());
		return list;
		
	}

	public User findById(Integer id) {
		Optional<User> optional=userRepo.findById(id);
		return optional.get();
	}
	
	
	public void delete(Integer id) {
		userRepo.deleteById(id);

	}

	public void update(User user) {
		userRepo.save(user);
	}

	public User selectOne(Integer id) {
		return entityManager.find(User.class, id);
	}

	public List<User> findAll(){
		return	userRepo.findAll();
		}

	public void inser( User user) {
		userRepo.save(user);
	}

	

	

}
