package com.inventorycontrolsystem.IVCS.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
	@Query("select u from Unit u where u.unitCode = :unitCode or u.name= :name ")
	public List<Unit>getUnitbyCode (@Param("unitCode") String unitCode, @Param ("name") String name);
	
	
	

}
