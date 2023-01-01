package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.DamageRepository;

@Service
public class DamageJpa {

	@Autowired
	DamageRepository damageJpa;

	
	
	public  void insert (Damage damage) {
		damageJpa.save(damage);
	}
	
	public void update (Damage damage) {
		damageJpa.save(damage);
	}
	public List<Damage>findAll(){
		return damageJpa.findAll();
	}
	
	public Page<Damage> select(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return damageJpa.findAllPage(pageable);
	}
	
	public List<Damage> damageretrunList() {
		List<Damage> list = damageJpa.findAllList();
		return list;
	}
	public List<Damage>selectAll(){
		return damageJpa.findAll();
	}
	
	public List<Damage> receiveViewList(String code) {
		List<Damage> list = damageJpa.receiveViewList(code);
		return list;

	}
	
	public List<Damage> receiveViewListByStock(String code) {
		List<Damage> list = damageJpa.receiveViewListByStock(code);
		return list;
	}
	
	public List<Damage> selectBydamageNo(String damageNo){
		return damageJpa.findBydamageNo(damageNo);
	}
	
	
	
	public Page<Damage> selectDamageReport(int pageNumber,Stock stk,Warehouse wh,String start,String end){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		
		if(!start.equals("") &&  !end.equals("") && stk!=null && wh!=null ) {
			return damageJpa.findByAllCond(stk, wh, start, end, pageable);
		}
		else if(stk!=null && wh!=null  ) {
			return damageJpa.findByStkAndWh(stk, wh, pageable);
		}
		return damageJpa.findByAll(stk, wh, start, end, pageable);
		
	}
}


