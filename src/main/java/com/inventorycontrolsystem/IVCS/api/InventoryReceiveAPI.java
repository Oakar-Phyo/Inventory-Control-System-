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
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.SupplierJpa;
import com.inventorycontrolsystem.IVCS.service.UnitService;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;




@RestController
public class InventoryReceiveAPI {
	
	List<StockIn> stockInList=new ArrayList<StockIn>();
	
	
	@Autowired
	StockJpa stockJpa;
	
	@Autowired 
	StockInJpa stkinJpa;
	
	@Autowired
	UnitService unitJpa;
	
	@Autowired
	WarehouseJpa warehouseJpa;
	
	@Autowired
	SupplierJpa supJpa;
	
	@Autowired
	StockWarehouseJpa stockWarehouseJpa;

	@Autowired
	CurrencyService curJpa;
	
	@GetMapping("/receiveAdd")
	public List<StockIn> receive(@Param("nrNo") String nrNo,@Param("nwhName")String nwhName
			,@Param("nsupName")String nsupName,@Param("nstkDate")String nstkDate
			,@Param("ncur")String ncur,@Param("nexcRate")String nexcRate,@Param("nstock")String nstock,
			@Param("nqty")String nqty,@Param("nprice")String nprice,@Param("nunit")String nunit) {
		if(stkinJpa.selectByCode(nrNo).size()!=0) {
			return new ArrayList<StockIn>();
				}
		Warehouse warehouse=warehouseJpa.selectOne(Integer.parseInt(nwhName));
		Supplier supplier=supJpa.selectOne(Integer.parseInt(nsupName));
		Currency currency=curJpa.selectOne(Integer.parseInt(ncur));
		//Unit unit=unitJpa.selectOne(Integer.parseInt(nunit));
		Stock stock=stockJpa.selectStockByCode(nstock);
		if(stock.equals(null)) {
		    stockJpa.selectOne(0);
		}
		

		Double exch=Double.parseDouble(nexcRate);
		Integer qty=Integer.parseInt(nqty);
		Double price=Double.parseDouble(nprice);
		double result = qty*price;
		if(!stock.equals(null)) {
		StockIn stockIn=new StockIn(qty, price,nstkDate, exch,stock,warehouse, supplier, currency);
		stockIn.setSubTotalAmount(result);
		stockInList.add(stockIn);
		}
		
		return stockInList;
	}
	
	@GetMapping("/remove")
	public List<StockIn> remove(@Param("i")Integer i){
		for(int j=0;j<stockInList.size();j++) {
			if(stockInList.get(j).getStock().getId().equals(i)) {
				stockInList.remove(j);
			}
		}
		return stockInList;
	}
	@GetMapping("/done")
	public String done(@Param("receiveNo") String receiveNo, @Param("nrem")String nrem) {
		for(StockIn stockIn:stockInList) {
			stockIn.setCode(receiveNo);
			stockIn.setRemark(nrem);
			stkinJpa.insert(stockIn);
			if(stockWarehouseJpa.selectOneByCode(stockIn.getStock().getStockCode(),stockIn.getWarehouse())==null) {
				stockWarehouseJpa.insert(new StockWarehouse(stockIn.getPrice(),stockIn.getQty(), stockIn.getStock().getStockCode(), stockIn.getStock(), stockIn.getWarehouse()));
			}else {
				StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(stockIn.getStock().getStockCode(),stockIn.getWarehouse());
				
					stockWarehouse.setQty(stockWarehouse.getQty()+stockIn.getQty());
					stockWarehouseJpa.update(stockWarehouse);
				
			}
		}
		stockInList.removeAll(stockInList);
		return "Success";
	}

}
