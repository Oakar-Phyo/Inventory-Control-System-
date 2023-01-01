package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.IssueRepository;

@Service
public class IssueJpa {
 
	@Autowired
	IssueRepository issJpa;
	
	public void insert(Issue issue) {
		issJpa.save(issue);
	}
	

	public void update(Issue issue) {
		issJpa.save(issue);
	}

	public List<Issue> findAll() {
		return issJpa.findAll();
	}
	
	public List<Issue> issueGroupedList() {
		List<Issue> list = issJpa.issueGroupedList();
		return list;
	}

	public List<Issue> selectByissueNo(String issueNo){
		return issJpa.findByissueNo(issueNo);
	}
	
	public Page<Issue> select(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return issJpa.findAll(pageable);
	}


	public List<Issue> receiveViewList(String code) {
		List<Issue> list = issJpa.receiveViewList(code);
		return list;
	}
	
	public List<Issue> selectByCode(String code) {
		return issJpa.findByCode(code);
	}
	
	public Issue selectByStockCode(Stock stock,String receiveItem) {
		return issJpa.findByStockCode(stock,receiveItem);
	}

	public List<Issue> receiveViewListByStock(String code) {
		List<Issue> list = issJpa.receiveViewListByStock(code);
		return list;
	}

	public Page<Issue> selectForReport(int pageNumber,Stock stk,Warehouse wh,String start,String end){
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		if(!start.equals("") && !end.equals("") && stk!=null && wh!=null ){
			return issJpa.findByAllCond(stk, wh, start, end, pageable);
			
		}else if(stk!=null && wh!=null ) {
			return issJpa.findByStkAndWh(stk, wh, pageable);
		}
			return issJpa.findByAll(stk, wh, start, end, pageable);
		
	}
	
	
}
