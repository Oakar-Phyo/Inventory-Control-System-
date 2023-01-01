package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.AdjustmentJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;

@RestController
public class AdjustmentAPI {
@Autowired
StockInJpa stockInJpa;

@Autowired
StockJpa stockJpa;

@Autowired
WarehouseJpa warehouseJpa;

@Autowired
StockWarehouseJpa stockWareHouseService;

@Autowired
AdjustmentJpa adjustmentService;

private String qty;
public String getQty() {
	return qty;
}
public void setQty(String qty) {
	this.qty = qty;
}

List<Adjustment> adjustmentList = new ArrayList<Adjustment>();
@GetMapping("/autoFillWarehouse")
	public List<StockWarehouse> autoFillWarehouse(@Param("wareFill") Integer wareFill){
	Warehouse warehouse = warehouseJpa.findById(wareFill);
	List<StockWarehouse> stockWarehouse = stockWareHouseService.selectByWarehouse(warehouse);
	return stockWarehouse;
	}

@GetMapping("/autoFillStockAdj")
	public StockWarehouse stockChange(@Param("stockCode")String stockCode,@Param("wareFill")Integer wareFill) {
	Warehouse warehouse=warehouseJpa.findById(wareFill);
	StockWarehouse stockWarehouse = stockWareHouseService.selectOneByCode(stockCode, warehouse);
	return stockWarehouse;
	}

	@GetMapping("/stockAdjustmentAdd")
	public List<Adjustment> stockAdjustmentAdd(@Param("adjustmentNo") String adjustmentNo,@Param("stockCode") String stockCode,
																						
	@Param("warehouseId") Integer warehouseId,@Param("adjustmentDate") String adjustmentDate,@Param("adjustmentQty") String adjustmentQty){
		
		 if(!adjustmentService.findByAdjusmentNo(adjustmentNo).isEmpty() || adjustmentDate.equals("")) {
	    	   return null; 
	       }
		 
		Warehouse warehouse = warehouseJpa.findById(warehouseId);
		Stock stock = stockJpa.selectStockByCode(stockCode);
		StockWarehouse stockWarehouse = stockWareHouseService.selectOneByCode(stockCode, warehouse);
		
		Adjustment adjustment = null;
		Integer totalAdjustmentQty = 0; 
		 Double subTotalAmount =0.0;
		 Integer adjQty = 0;
		 this.qty = adjustmentQty;
			 
		if(stockWarehouse!=null) {
			
			if(adjustmentQty.startsWith("-")) {
				adjustmentQty = adjustmentQty.substring(1);
			adjQty = Integer.parseInt(adjustmentQty);
			if(stockWarehouse.getQty()< adjQty) {
				return adjustmentList;
			} else {
			totalAdjustmentQty = stockWarehouse.getQty()-adjQty;
			subTotalAmount = totalAdjustmentQty*stockWarehouse.getPrice();
			}
			} else {
				adjQty = Integer.parseInt(qty);
				if(stockWarehouse.getQty()< adjQty) {
					return adjustmentList;
				} else {
				totalAdjustmentQty = stockWarehouse.getQty()+Integer.parseInt(adjustmentQty);
				subTotalAmount = totalAdjustmentQty*stockWarehouse.getPrice();
				}
			}
		}
		adjustment = new Adjustment(adjustmentNo, stockWarehouse.getQty(), Integer.parseInt(qty), adjustmentDate, totalAdjustmentQty, subTotalAmount,  stockWarehouse, stock, warehouse);
		adjustment.setQty(qty);
		adjustmentList.add(adjustment);
		return adjustmentList;
		
	}
	
	@GetMapping("/stockAdjustmentDone")
	public String stockAdjustmentDone(@Param("remark") String remark) {
		for(Adjustment adjustment : adjustmentList) {
			
		StockWarehouse stockWarehouse = stockWareHouseService.selectOneByCode(adjustment.getStock().getStockCode(), adjustment.getWarehouse());
		Integer oldQty = stockWarehouse.getQty();
		String newQty = adjustment.getQty();
		
		System.out.println(newQty+"enter adjustment qty");
		if(newQty.contains("-")) {
			
			newQty = newQty.substring(1);
			Integer adjqty = Integer.parseInt(newQty);
			stockWarehouse.setQty(oldQty-adjqty);
			
		} else {
			stockWarehouse.setQty(oldQty+Integer.parseInt(newQty));
		}
		adjustment.setRemark(remark);
		adjustmentService.insertAdjustment(adjustment);
		}
		adjustmentList.removeAll(adjustmentList);
		return "Success";
	}
	
	@GetMapping("/stockAdjustmentRemove")
    public List<Adjustment> stockAdjustmentRemove(@Param("i")Integer i) {
    	for(int j=0;j<adjustmentList.size();j++) {
    		if(adjustmentList.get(j).getStock().getId().equals(i)) {
    			adjustmentList.remove(j);
    		}
    	}
    	return adjustmentList;
    }

}
