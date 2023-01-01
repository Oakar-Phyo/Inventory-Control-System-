package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.AdjustmentRepository;

@Service
public class AdjustmentJpa {

	@Autowired
	AdjustmentRepository adjustmentRepo;
	
	//save
	public void insertAdjustment(Adjustment adjustment) {
		adjustmentRepo.save(adjustment);
	}
	
	
	public List<Adjustment> selectAll(){
		return adjustmentRepo.findAll();
	}
	
	//pagination
	public Page<Adjustment> findAllList(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return adjustmentRepo.findAllPage(pageable);
	}
	
	//viewList
	public List<Adjustment> adjustmentView(String code){
		return adjustmentRepo.adjustmentViewList(code);
	}
	
	//viewListByStockCode
	public List<Adjustment> adjustmentViewByStockCode(String code){
		return adjustmentRepo.receiveViewListByStock(code);
	}
	
	//
	public List<Adjustment> findByAdjusmentNo(String adjNo){
		return adjustmentRepo.findByAdjusmentNo(adjNo);
	}
	
	
	
	public Page<Adjustment> adjustmentSearch(int pageNumber, Stock stock, Warehouse warehouse, String startDate, String endDate){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		
		if(!startDate.equals("") &&  !endDate.equals("") && stock!=null && warehouse!=null ) {
			return adjustmentRepo.findByAllCond(stock, warehouse, startDate, endDate, pageable);
		}else if(stock!=null && warehouse!=null  ) {
				return adjustmentRepo.findByStkAndWh(stock, warehouse, pageable);
		} else {
		return adjustmentRepo.searchAll(stock, warehouse, startDate, endDate, pageable);
		}
	}
	}

