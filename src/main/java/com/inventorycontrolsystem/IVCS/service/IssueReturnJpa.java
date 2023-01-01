package com.inventorycontrolsystem.IVCS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.IssueReturn;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.repository.IssueReturnRepository;

@Service
public class IssueReturnJpa {

	@Autowired
	IssueReturnRepository issRetrunJpa;
	
	public void insert(IssueReturn issueReturn) {
		issRetrunJpa.save(issueReturn);
	}
	
	public List<IssueReturn> selectByIssueNo(String issueReturnNo){
		return issRetrunJpa.findByIssueNo(issueReturnNo);
	}
	
	public Page<IssueReturn> select(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return issRetrunJpa.findAll(pageable);
	}
	
	public List<IssueReturn> issueReturnList() {
		List<IssueReturn> list = issRetrunJpa.issueReturnList();
		return list;
	}
	
	public List<IssueReturn> issueReturnViewList(String code) {
		List<IssueReturn> list = issRetrunJpa.issueReturnViewList(code);
		return list;
	}
	
	public List<IssueReturn> issueReturnViewListByStock(String code) {
		List<IssueReturn> list = issRetrunJpa.issueReturnViewListByStock(code);
		return list;
	}
	
	public List<IssueReturn> selectByCode(String code) {
		return issRetrunJpa.findByCode(code);
	}
	
	public Page<IssueReturn> issueReturnSearch(int pageNumber, Stock stock, Warehouse warehouse, String startDate , String endDate){
		Pageable pageable = PageRequest.of(pageNumber-1, 10);

		if(!startDate.equals("") && !endDate.equals("") && stock!=null && warehouse!=null ) {
		return issRetrunJpa.findByAllCond(stock, warehouse, startDate, endDate, pageable);
		}
		else if(stock!=null && warehouse!=null ) {
		return issRetrunJpa.findByStkAndWh(stock, warehouse, pageable);
		}
		return issRetrunJpa.findByAll(stock, warehouse, startDate, endDate, pageable);
		//return issRetrunJpa.findIssueReturnReport(stock, warehouse, startDate, endDate, pageable);
		}
}
