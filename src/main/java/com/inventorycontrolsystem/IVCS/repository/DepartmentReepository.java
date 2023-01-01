package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.Department;


public interface DepartmentReepository extends JpaRepository<Department, Integer> {

	
	@Query ("select d from Department d where d.depCode = :depCode or d.name= :name ")
	public List<Department>getDepbyCode (@Param("depCode") String depCode, @Param ("name") String name);

	@Query("select d from Department d where d.depCode=?1")
	 Department findByDepartmentCode(String code);

}
