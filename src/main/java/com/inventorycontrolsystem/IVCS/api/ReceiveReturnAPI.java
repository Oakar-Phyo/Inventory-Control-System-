package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolsystem.IVCS.model.Currency;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockOutJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.SupplierJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;

@RestController
public class ReceiveReturnAPI {
	@Autowired
	StockInJpa stockInJpa;
	@Autowired
	StockJpa stockJpa;
	@Autowired
	WarehouseJpa warehouseJpa;
	@Autowired
	SupplierJpa supJpa;
	@Autowired
	CurrencyService curJpa;
	@Autowired
	StockOutJpa stockOutJpa;
	@Autowired
	StockWarehouseJpa stockWarehouseJpa;
	List<StockOut> stockOutList=new ArrayList<StockOut>();
	
	@GetMapping("/autofillitem")
	public List<StockIn> autofill(@Param("receiveItem")String receiveItem) {
		return  stockInJpa.selectByCode(receiveItem);
	}
	
	@GetMapping("/sutoFillStock")
	public StockWarehouse autofillStock(@Param("recstk")String recstk,@Param("receiveItem")String receiveItem) {
		//Stock stock=stockJpa.selectStockCodeForReturn(recstk);
		return stockWarehouseJpa.selectByReceiveCode(recstk);
	}
	
	
	@GetMapping("/receiveReturnAdd")
	public List<StockOut> receivereturn(@Param("nrenNo") String nrenNo,@Param("nrecNo") String nrecNo,@Param("nwhName")String nwhName
			,@Param("nsupName")String nsupName,@Param("ncur")String ncur,
			@Param("nexcRate")String nexcRate,@Param("nrendate")String nrendate,@Param("nstock")String nstock,
			@Param("nreceiveqty")String nreceiveqty,
			@Param("nreturnqty")String nreturnqty,@Param("nprices")String nprices) {

		 
		  
		  
		
		 if(!stockOutJpa.selectByReturnNo(nrenNo).isEmpty()||nrendate=="") {
	    	   return null; 
	       }
		 
		
		
			 
			 
		Stock stock=stockJpa.selectStockByCode(nstock);
		System.out.println(stock.getName());
		StockIn stockIn=stockInJpa.selectByStockCode(stock, nrecNo);
		Warehouse warehouse=warehouseJpa.selectByWarehouseCode(nwhName);
		Supplier supplier=supJpa.selectBySupplierCode(nsupName);
		Currency currency=curJpa.selectByCurrencyCode(ncur);
		Integer rqty=Integer.parseInt(nreturnqty);
		
		StockWarehouse cQty=stockWarehouseJpa.selectOneByCode(nstock, warehouse);
		System.out.println(rqty+"return qty");
		System.out.println(cQty.getQty()+"receive qty");
		
        if(cQty.getQty()<rqty ) {
			return stockOutList;
		} 
		
		Double price=Double.parseDouble(nprices);
		Double exchangeRate=Double.parseDouble(nexcRate);
		double result = rqty*price;
		StockOut stockOut=new StockOut(nrecNo,nprices, rqty,price,nrendate,stock, stockIn,cQty.getQty());
		stockOut.setWarehouse(warehouse);
		stockOut.setExchangeRate(exchangeRate);
		stockOut.setSupplier(supplier);
		stockOut.setCurrency(currency);
		stockOut.setSubTotalAmount(result);
		stockOutList.add(stockOut);

		return stockOutList;
	}
	@GetMapping("/receiveReturndone")
	public String returnDone(@Param("returnNo")String returnNo,@Param("nrem")String nrem) {
		for(StockOut stockOut:stockOutList) {
			StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(stockOut.getStock().getStockCode(),stockOut.getWarehouse());
			Integer oldqty=stockWarehouse.getQty();
			Integer newqty=stockOut.getReturnqty();
			if(oldqty<newqty) {
				return "Qty not avaliable";
			}else {
				stockWarehouse.setQty(oldqty-newqty);
			}
			stockOut.setCode(returnNo);
			stockOut.setRemark(nrem);
			stockOutJpa.insert(stockOut);
		}
		stockOutList.removeAll(stockOutList);
		return "Success";
		
	}
//	@GetMapping("/returnRemove")
//	public List<StockOut> returnRemove(@Param("i")Integer i) {
//		System.out.println(i);
//        for(int j=0;j<stockOutList.size();j++) {	
//        	if(stockOutList.get(j).getStock().getId().equals(i)) {
//				stockOutList.remove(j);
//			}
//        }
//        System.out.println(stockOutList.size());
//        return stockOutList;
//	}
	
	@GetMapping("/returnRemove")
	public List<StockOut> remove(@Param("i")Integer i){
		for(int j=0;j<stockOutList.size();j++) {
			if(stockOutList.get(j).getStock().getId().equals(i)) {
				stockOutList.remove(j);
			}
		}
		return stockOutList;
	}
	
}
