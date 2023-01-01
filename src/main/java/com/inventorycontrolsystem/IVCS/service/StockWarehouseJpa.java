package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.StockWarehouseRepository;

@Service
public class StockWarehouseJpa {
	@Autowired
	StockWarehouseRepository repo;

	public void update(StockWarehouse stkw) {
		repo.save(stkw);
	}
	public void insert(StockWarehouse stkw) {
		repo.save(stkw);
	}
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}
	
	public StockWarehouse selectOneByCode(String code,Warehouse warehouse) {
		return repo.findByCode(code,warehouse);
	}
	
	public List<StockWarehouse> selectByWarehouse(Warehouse warehouse){
		return repo.findByWarehouse(warehouse);
	}
	public List<StockWarehouse> findAll() {
		return repo.findAll();
	}

	public StockWarehouse selectByReceiveCode(String code) {
		return repo.findCode(code);
		
	}
	}
