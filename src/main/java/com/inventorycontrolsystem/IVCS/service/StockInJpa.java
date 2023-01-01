package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.StockInRepository;

@Repository
@Transactional
public class StockInJpa {
	@Autowired
	StockInRepository stkinJpa;

	@PersistenceContext
	EntityManager entityManager;

	public StockIn selectByStockCode(Stock stock,String receiveItem) {
		return stkinJpa.findByStockCode(stock,receiveItem);
	}
	public StockIn selectByWarehouseCodeByStock(Stock stock,Warehouse warehouse) {
		return stkinJpa.findByWarehouseByStock(stock,warehouse);
	}
	
	public StockIn selectByExchangeRate(double exchangeRate) {
		return stkinJpa.findByExchangeRate(exchangeRate);
	}

	public List<StockIn> selectByCode(String code) {
		return stkinJpa.findByCode(code);
	}
	
	public List<StockIn> selectByWarehouseCode(Warehouse warehouse) {
		return stkinJpa.findByWarehouseCode(warehouse);
	}
	
	public StockIn selectByQty(Integer qty) {
		return	stkinJpa.findByQty(qty);
		}

	public void delete(Integer id) {
		stkinJpa.deleteById(id);

	}

	public void update(StockIn stockin) {
		stkinJpa.save(stockin);
	}

	public StockIn selectOne(Integer id) {
		return entityManager.find(StockIn.class, id);
	}

	public List<StockIn> findAll() {
		return stkinJpa.findAll();
	}

	public void insert(StockIn stockin) {
		stkinJpa.save(stockin);
	}

	public List<StockIn> receiveList() {
		List<StockIn> list = stkinJpa.receiveList();
		return list;
	}

	public List<StockIn> receiveViewList(String code) {
		List<StockIn> list = stkinJpa.receiveViewList(code);
		return list;

	}

	public List<StockIn> receiveViewListByStock(String code) {
		List<StockIn> list = stkinJpa.receiveViewListByStock(code);
		return list;
	}

	public List<StockIn> receiveViewListByTotal(String code) {
		List<StockIn> list = stkinJpa.receiveViewListByTotal(code);
		return list;
	}

	public Page<StockIn> select(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return stkinJpa.findAll(pageable);
	}
	
	public Integer selectForDashboard(String start,String end) {
		return stkinJpa.selectForDashboard(start, end);
	}
	
	public Page<StockIn> selectForReport(int pageNumber,Stock stk,Warehouse wh,String start,String end){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		 if(!start.equals("") && !end.equals("") && stk!=null && wh!=null ){
				return stkinJpa.findByAllCond(stk, wh, start, end, pageable);
		 }else if(stk!=null && wh!=null ) {
				return stkinJpa.findByStkAndWh(stk, wh, pageable);		
		}
			return stkinJpa.findByAll(stk, wh, start, end, pageable);
	}

}
