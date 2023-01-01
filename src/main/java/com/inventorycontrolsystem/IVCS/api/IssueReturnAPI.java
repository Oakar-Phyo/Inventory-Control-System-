package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolsystem.IVCS.model.Department;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.IssueReturn;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.IssueJpa;
import com.inventorycontrolsystem.IVCS.service.IssueReturnJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;

@RestController
public class IssueReturnAPI {
	@Autowired
	IssueJpa issueJpa;
	@Autowired
	StockJpa stockJpa;
	@Autowired
	WarehouseJpa warehouseJpa;
	//@Autowired
	//SupplierJpa supJpa;
	@Autowired
	DepartmentJpa dptJpa;
	
	@Autowired
	CurrencyService curJpa;
	@Autowired
    IssueReturnJpa issueReturnJpa;
	@Autowired
	StockWarehouseJpa stockWarehouseJpa;
	List<IssueReturn> issueReturnList=new ArrayList<IssueReturn>();
	
	@GetMapping("/issautofillitem")
	public List<Issue> autofill(@Param("issueItem")String issueItem) {
		return  issueJpa.selectByCode(issueItem);
	}
	
	@GetMapping("/issautoFillStock")
	public Issue autofillStock(@Param("issstk")String issstk,@Param("issueItem")String issueItem) {
		Stock stock=stockJpa.selectStockCodeForReturn(issstk);
		return issueJpa.selectByStockCode(stock,issueItem);
	}
	
	@GetMapping("/issueReturnAdd")
	public List<IssueReturn> receivereturn(@Param("nisrNo") String nisrNo,@Param("nissNo") String nissNo,@Param("nwhName")String nwhName
			,@Param("ndptName")String ndptName,
			@Param("nrendate")String nrendate,@Param("nstock")String nstock,
			@Param("nissueqty")Integer nissueqty,
			@Param("nreturnqty")String nreturnqty,@Param("nprices")String nprices) {
		
		
		//Condition for return No. already exist)
		if(issueReturnJpa.selectByCode(nisrNo).size()!=0) {
			return new ArrayList<IssueReturn>();
				}
		
		
		int i=nissueqty;
		int j=Integer.parseInt(nreturnqty);
		
		//Condition for Stock Return Qty
		if(j>i)
		{
			return issueReturnList;
		}
		
		Stock stock=stockJpa.selectStockByCode(nstock);
		System.out.println(stock.getName());
		//Dunno its working yet
		Issue issue=issueJpa.selectByStockCode(stock, nissNo);
		Warehouse warehouse=warehouseJpa.selectByWarehouseCode(nwhName);
		//Supplier supplier=supJpa.selectBySupplierCode(nsupName);
		Department department =dptJpa.selectByDepartmentCode(ndptName);
		//Currency currency=curJpa.selectByCurrencyCode(ncur);
		
		
		
		Integer rqty=Integer.parseInt(nreturnqty);
		System.out.println(warehouse.getName());
		Double price=Double.parseDouble(nprices);
		//Double exchangeRate=Double.parseDouble(nexcRate);
		double result = rqty*price;
		//Insert data into Model 
		IssueReturn issueReturn=new IssueReturn(nisrNo,nissNo, rqty,price,nrendate,stock, issue,nissueqty);
		issueReturn.setWarehouse(warehouse);
		issueReturn.setDepartment(department);
		issueReturn.setIssue(issue);
		//issueReturn.setExchangeRate(exchangeRate);
		//stockOut.setSupplier(supplier);
		//stockOut.setCurrency(currency);
		issueReturn.setSubTotalAmount(result);
		issueReturnList.add(issueReturn);

		return issueReturnList;
	}
	
	@GetMapping("/issueReturndone")
	public String returnDone(@Param("issueRtnNo")String issueRtnNo,@Param("nrem")String nrem) {
		for(IssueReturn issueReturn:issueReturnList) {
			StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(issueReturn.getStock().getStockCode(),issueReturn.getWarehouse());
			Integer oldqty=stockWarehouse.getQty();
			Integer newqty=issueReturn.getReturnqty();
			if(oldqty<newqty) {
				return "Qty not avaliable";
			}else {
				stockWarehouse.setQty(oldqty+newqty);
			}
			issueReturn.setCode(issueRtnNo);
			issueReturn.setRemark(nrem);
			//stockOutJpa.insert(stockOut);
			issueReturnJpa.insert(issueReturn);
		}
		issueReturnList.removeAll(issueReturnList);
		return "Success";
	}
	
	@GetMapping("/issueReturnRemove")
	public List<IssueReturn> remove(@Param("i")Integer i){
		for(int j=0;j<issueReturnList.size();j++) {
			if(issueReturnList.get(j).getStock().getId().equals(i)) {
				issueReturnList.remove(j);
			}
		}
		return issueReturnList;
	}
}
