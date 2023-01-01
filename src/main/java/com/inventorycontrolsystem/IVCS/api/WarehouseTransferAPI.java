package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseTransferJpa;

@RestController
public class WarehouseTransferAPI { 
	@Autowired
	WarehouseJpa warehouseService;
	@Autowired
	StockWarehouseJpa stockWareHouseService;
	@Autowired
	WarehouseTransferJpa warehouseTransferService;
	@Autowired
	StockJpa stockService;
	
	List<WarehouseTransfer> transferList=new ArrayList<WarehouseTransfer>();	
	@GetMapping("/fromWarehouse")
	public List<StockWarehouse> fromWarehouse(@Param("warehouseNo")Integer warehouseNo) {
		Warehouse wh=warehouseService.findById(warehouseNo);
		List<StockWarehouse> stl=stockWareHouseService.selectByWarehouse(wh);
		return  stl;
		
	}
	@GetMapping("/stockChange")
	public StockWarehouse stockChange(@Param("stockCode")String stockCode,@Param("warehouseId")Integer warehouseId) {
		Warehouse wh=warehouseService.findById(warehouseId);
		StockWarehouse stw=stockWareHouseService.selectOneByCode(stockCode, wh);
		return stw;
	}
	@GetMapping("/stockTransferAdd")
	public List<WarehouseTransfer> stockTransferAdd(@Param("transferNo")String transferNo,@Param("stockCode")String stockCode,@Param("warehouseId")Integer warehouseId,@Param("transferDate")String transferDate,
			@Param("toWarehouseId")Integer toWarehouseId,@Param("transferQty")Integer transferQty,@Param("nprice")String nprice) {
		       if(!warehouseTransferService.selectByTransferNo(transferNo).isEmpty()) {
		    	   return null; 
		       }
		        if(warehouseId==toWarehouseId) {
		        	return null;
		        }
		        Warehouse fw=warehouseService.findById(warehouseId);
		        Warehouse tw=warehouseService.findById(toWarehouseId);
		        Stock stk=stockService.selectStockByCode(stockCode);
		        StockWarehouse cQty=stockWareHouseService.selectOneByCode(stockCode, fw);
		        if(cQty.getQty()<transferQty ) {
	    			return transferList;
	    		}
		        Double price=Double.parseDouble(nprice);
		        Double totalPrice=price*transferQty;
		        WarehouseTransfer wt=new WarehouseTransfer(transferNo, fw, stk, cQty.getQty(), transferDate, tw, transferQty, totalPrice,price);
	            transferList.add(wt);
				return transferList;
		
	}
    @GetMapping("/stockTransferDone")
    public String stockTransferDone(@Param("remarkSt")String remarkSt) {
    	for(WarehouseTransfer wht:transferList) {
    		
    		StockWarehouse fromStwh=stockWareHouseService.selectOneByCode(wht.getStock().getStockCode(), wht.getFromWarehouse());
    		StockWarehouse toStwh=stockWareHouseService.selectOneByCode(wht.getStock().getStockCode(), wht.getToWarehouse());
    		if(toStwh==null) {
    			stockWareHouseService.insert(new StockWarehouse(wht.getPrice(),wht.getTransferQty(), wht.getStock().getStockCode(), wht.getStock(), wht.getToWarehouse()));
    		}else {
    			toStwh.setQty(toStwh.getQty()+wht.getTransferQty());
    			stockWareHouseService.update(toStwh);
    		}
    		fromStwh.setQty(fromStwh.getQty()-wht.getTransferQty());
    		stockWareHouseService.update(fromStwh);
    		wht.setRemark(remarkSt);
    		warehouseTransferService.insert(wht);
    		
    	}
    	transferList.removeAll(transferList);
		return "Success";
    	
    }
    @GetMapping("/stockTransferRemove")
    public List<WarehouseTransfer> stockTransferRemove(@Param("i")Integer i) {
    	for(int j=0;j<transferList.size();j++) {
    		if(i==transferList.get(j).getStock().getId()) {
    			transferList.remove(j);
    		}
    	}
    	return transferList;
    }

}
