package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer>{

	@Query("SELECT r FROM UserRole r WHERE r.name =:roleName")
     List<UserRole> getUserRoleByName(@Param("roleName")String rol);

}
