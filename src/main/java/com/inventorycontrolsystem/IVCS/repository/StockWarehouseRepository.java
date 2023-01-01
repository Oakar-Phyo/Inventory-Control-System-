package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;


@Repository
public interface StockWarehouseRepository extends JpaRepository<StockWarehouse, Integer> { 
  
	@Query("select s from StockWarehouse s where s.code=?1 and s.warehouse=?2")
	StockWarehouse findByCode(String code,Warehouse warehouse);
	
	@Query("select s from StockWarehouse s where s.warehouse=?1")
	List<StockWarehouse> findByWarehouse(Warehouse warehouse);
	
	@Query("select s from StockWarehouse s where s.code=?1")
	StockWarehouse findCode(String stock);
}
