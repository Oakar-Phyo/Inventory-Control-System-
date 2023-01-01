package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>{

	@Query("select w from Warehouse w where w.warehouseCode= :warehouseCode ")
	public List<Warehouse> findByCode(@Param("warehouseCode") String warehouseCode);
 
	@Query("select w from Warehouse w where w.name= :name ")
	 Warehouse findByName(String name);

	
	@Query("select w from Warehouse w where w.warehouseCode=?1")
	 Warehouse findByWarehouseCode(String code);
	
	
}
