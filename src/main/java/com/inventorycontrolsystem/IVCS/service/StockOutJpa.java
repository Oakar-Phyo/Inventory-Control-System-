package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.StockOutRepository;

@Service
public class StockOutJpa {

	@Autowired
	StockOutRepository stockoutJpa;
	
	public void update(StockOut stockout) {
		stockoutJpa.save(stockout);
	}

	
	public List<StockOut> findAll() {
		return stockoutJpa.findAll();
	}

	public void insert(StockOut stockout) {
		stockoutJpa.save(stockout);
	}
	
	public Page<StockOut> select(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return stockoutJpa.findAll(pageable);
	}
	
	public List<StockOut> receiveReturnList() {
		List<StockOut> list = stockoutJpa.receiveReturnList();
		return list;
	}
	
	public List<StockOut> receiveViewList(String code) {
		List<StockOut> list = stockoutJpa.receiveViewList(code);
		return list;

	}
	
	public List<StockOut> receiveViewListByStock(String code) {
		List<StockOut> list = stockoutJpa.receiveViewListByStock(code);
		return list;
	}
	
	public Page<StockOut> receiveReturnSearch(int pageNumber, Stock stock, Warehouse warehouse, String startDate , String endDate){
		Pageable pageable = PageRequest.of(pageNumber-1, 10);
		
		if(!startDate.equals("") &&  !endDate.equals("") && stock!=null && warehouse!=null ) {
			return stockoutJpa.findByAllCond(stock, warehouse, startDate, endDate, pageable);
		}else if(stock!=null && warehouse!=null  ) {
				return stockoutJpa.findByStkAndWh(stock, warehouse, pageable);
		} else {
		return stockoutJpa.findReceiveReport(stock, warehouse, startDate, endDate, pageable);
		}
	}


	public List<StockOut> selectByReturnNo(String nrenNo){
		return stockoutJpa.findByreturnNo(nrenNo);
	}

}
