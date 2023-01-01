package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;
import com.inventorycontrolsystem.IVCS.repository.WarehouseTransferRepository;

@Service
public class WarehouseTransferJpa {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	WarehouseTransferRepository wtransferJpa;
	
	
	public void insert(WarehouseTransfer war) {
		wtransferJpa.save(war);
	} 
	
	public void update(WarehouseTransfer war) {
		wtransferJpa.save(war);
	} 
	
	public void deleteById(Integer id) {
		wtransferJpa.deleteById(id);
	}
	
	public WarehouseTransfer selectOne(Integer id) {
		return entityManager.find(WarehouseTransfer.class, id);
	}
	public List<WarehouseTransfer> findAll(){
		return wtransferJpa.findAll();
	}
	public List<WarehouseTransfer> selectByTransferNo(String transferNo){
		return wtransferJpa.findByTransferNo(transferNo);
	}
	public Page<WarehouseTransfer> select(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return wtransferJpa.findAll(pageable);
	}
	
	public List<WarehouseTransfer> reviewListByTransferNo(String no){
		List<WarehouseTransfer> list=wtransferJpa.reviewListByTransferNo(no);
		return list;
	}
	
	public List<WarehouseTransfer> reviewListByTransferNoAll(String no){
		List<WarehouseTransfer> list=wtransferJpa.reviewListByTransferNoAll(no);
		return list;
	}
	
	public Page<WarehouseTransfer> selectForReport(int pageNumber,Stock stk,Warehouse wh1,Warehouse wh2,String start,String end){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		if(!start.equals("") && !end.equals("") && stk!=null && wh1!=null && wh2!=null){
			return wtransferJpa.findByAllCond(stk, wh1,wh2, start, end, pageable);
			
		}else if(stk!=null && wh1!=null && wh2!=null ) {
			return wtransferJpa.findByStkAndWh(stk, wh1,wh2, pageable);
	 
		}
			return wtransferJpa.findByAll(stk, wh1,wh2, start, end, pageable);
	}
}