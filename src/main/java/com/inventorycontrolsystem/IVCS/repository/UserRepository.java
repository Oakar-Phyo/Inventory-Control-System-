
package com.inventorycontrolsystem.IVCS.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("select u from User u where u.email= :email ")
	public List<User> findByEmail(@Param("email") String email);



}
