package com.inventorycontrolsystem.IVCS.service;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inventorycontrolsystem.IVCS.formmodel.StockForm;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.repository.StockRepository;


@Repository
@Transactional
public class StockJpa {

	@Autowired
	StockRepository stockjpa;
	
	@PersistenceContext
	EntityManager entityManager;
	

	public void delete(Integer id) {
		stockjpa.deleteById(id);

	}

	public void update(Stock stock) {
		stockjpa.save(stock);
	}
	

	public Stock selectOne(Integer id) {
		return entityManager.find(Stock.class,id);
	}

	public List<Stock> findAll(){
		return	stockjpa.findAll();
		}

	public void insert(Stock stock) {
		stockjpa.save(stock);
	}
	public Stock selectStockByCode(String code) {
	return	stockjpa.findByCode(code);
	}
	public List<Stock> findByName(StockForm stockbean){
		List<Stock> list = new ArrayList<Stock>();
		list = stockjpa.findByname(stockbean.getName());
		return  list;
		
	}
	
	public Stock selectStockCodeForReturn(String code) {
		return	stockjpa.findByCodeForReturn(code);
	}
	
}
