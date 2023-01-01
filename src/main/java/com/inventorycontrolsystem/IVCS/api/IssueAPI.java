package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolsystem.IVCS.model.Department;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.IssueJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;

@RestController
public class IssueAPI {
	
	List<Issue> issueList=new ArrayList<Issue>();
	
	@Autowired
	StockJpa stockJpa;
	
	@Autowired
	WarehouseJpa warehouseJpa;
	
	@Autowired
	StockWarehouseJpa stockWarehouseJpa;

	@Autowired
	CurrencyService curJpa;
	
	@Autowired
	DepartmentJpa depJpa;

	@Autowired
	StockInJpa stockInJpa;
	
	@Autowired
	IssueJpa issueJpa;
	
	@GetMapping("/autofillware")
	public List<StockWarehouse> autofill(@Param("receiveWare")Integer receiveWare) {
		Warehouse warehouse=warehouseJpa.findById(receiveWare);
		List<StockWarehouse> stl=stockWarehouseJpa.selectByWarehouse(warehouse);
		return  stl;
	}
	
	@GetMapping("/autoFillStock")
	public StockWarehouse autofillStock(@Param("recstk")String recstk,@Param("receiveWare")Integer receiveWare) {
		Warehouse warehouse=warehouseJpa.findById(receiveWare);
		StockWarehouse stw=stockWarehouseJpa.selectOneByCode(recstk, warehouse);
		return stw;
		
	}
	
	@GetMapping("/issueAdd")
	public List<Issue> issueAdd(@Param("nissNo")String nissNo,@Param("nwhName")Integer nwhName,@Param("ndpName")String ndpName,@Param("ncur")String ncur,
			@Param("nexcRate")String nexcRate,@Param("nissdate")String nissdate,@Param("nstk")String nstk,
			@Param("nreceiveqty")String nreceiveqty,
			@Param("nissqty")Integer nissqty,@Param("nprice")String nprice) {
		
//		String code=issueNo;
//	List<Issue>listissue=	issueJpa.findAll();
//	for(int i=0;i<listissue.size();i++) {
//		listissue.get(i).getIssueCode().equals(code);
//		return null;
//	}
		
		if(!issueJpa.selectByissueNo(nissNo).isEmpty()||nissdate=="") {
	    	   return null; 
	       }
		Warehouse warehouse=warehouseJpa.findById(nwhName);
		Stock stock=stockJpa.selectStockByCode(nstk);
		if(stock.equals(null)) {
		    stockJpa.selectOne(0);
		}
        StockWarehouse cQty=stockWarehouseJpa.selectOneByCode(nstk, warehouse);
        if(cQty.getQty()<nissqty ) {
			return issueList;
		}
		Department department=depJpa.selectOne(Integer.parseInt(ndpName));
		
		Double price=Double.parseDouble(nprice);
		double result = nissqty*price;
		Issue issue=new Issue(nissqty,price,nissdate,stock,cQty.getQty(),department);
		issue.setWarehouse(warehouse);
         issue.setSubTotalAmount(result);
         issueList.add(issue);
		return issueList;
	}
	
	@GetMapping("/issuedone")
	public String issueDone(@Param("issueNo")String issueNo,@Param("nrem")String nrem) {
		for(Issue issue:issueList) {
			StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(issue.getStock().getStockCode(),issue.getWarehouse());
			Integer oldqty=stockWarehouse.getQty();
			Integer newqty=issue.getIssueQty();
			if(oldqty<newqty) {
				return "Qty not avaliable";
			}else {
				stockWarehouse.setQty(oldqty-newqty);
			}
			issue.setIssueCode(issueNo);
			issue.setRemark(nrem);
			issueJpa.insert(issue);
		}
		issueList.removeAll(issueList);
		return "Success";
		
	}
	
	 @GetMapping("/issueRemove")
	    public List<Issue> issueRemove(@Param("i")Integer i) {
	    	for(int j=0;j<issueList.size();j++) {
	    		if(i==issueList.get(j).getStock().getId()) {
	    			issueList.remove(j);
	    		}
	    	}
	    	return issueList;
	    }
	 
	 

	
}
