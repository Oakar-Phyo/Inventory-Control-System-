package com.inventorycontrolsystem.IVCS.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.DamageJpa;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;


@RestController
public class DamageAPI {
	
	
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
	DamageJpa  damageJpa;
	
	
	List<Damage> damageList=new ArrayList<Damage>();
	

	@GetMapping("/autofillwareDam")
	public List<StockWarehouse> autofill(@Param("receiveWare")Integer receiveWare) {
		Warehouse warehouse=warehouseJpa.findById(receiveWare);
		List<StockWarehouse> stl=stockWarehouseJpa.selectByWarehouse(warehouse);
		return stl;
	}
	

	
	@GetMapping("/autoFillStockDam")
	public StockWarehouse autofillStock(@Param("recstk")String recstk,@Param("receiveWare")Integer receiveWare) {
		//Stock stock=stockJpa.selectStockCodeForReturn(recstk);
		Warehouse warehouse=warehouseJpa.findById(receiveWare);
		return stockWarehouseJpa.selectOneByCode(recstk,warehouse);
		
	}
	

	@GetMapping("/damageAdd")
	public List<Damage> damageAdd(@Param ("nostk")String nostk,@Param ("nowhCode")Integer nowhCode, @Param("nodamgNo")String nodamgNo,
								  @Param ("noreceiveqty")String noreceiveqty,
			                      @Param ("nodmgqty")String  nodmgqty , @Param("noprice")String  noprice,@Param ("nodmgdate")String nodmgdate){
	
	
		 if(!damageJpa.selectBydamageNo(nodamgNo).isEmpty()||nodmgdate=="") {
	    	   return null; 
	       }
		
		
		Stock stock=stockJpa.selectStockCodeForReturn(nostk);
		Warehouse warehouse=warehouseJpa.findById(nowhCode);
	    StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(nostk, warehouse);
	
	 
	 Integer dmgqty=Integer.parseInt(nodmgqty);
	
		
	 if(stockWarehouse.getQty()<dmgqty) {
		
		 
		 return damageList;
	 }
	 Double price=Double.parseDouble(noprice);
	 double result = dmgqty*price;
	 
	 Damage damage=new Damage(nodamgNo ,dmgqty,nodmgdate,
			 stockWarehouse,  stock,result);
	    
	     damage.setPrice(price);
		damage.setWarehouse(warehouse);
	 damageList.add(damage);
	return damageList;
	
}
	
	@GetMapping("/removeDamage")
	public List<Damage> remove(@Param("i")Integer i){
		System.out.println("id is" +i);
		for(int j=0;j<damageList.size();j++) {
			System.out.println("original"+damageList.size());
			
			if(damageList.get(j).getStock().getId().equals(i)) {
				damageList.remove(j);
				System.out.println("delete"+damageList.size());
			}
		}
		return damageList;
	
	}
	
	@GetMapping("/damagedone")
	public String damageDone(@Param("damageNo")String damageNo,@Param("nre")String nre) {
		for(Damage damage:damageList) {
			
			System.out.println(nre);
			System.out.println(damage.getStock().getStockCode());
			System.out.println(damage.getWarehouse());
			
			StockWarehouse stockWarehouse=stockWarehouseJpa.selectOneByCode(damage.getStock().getStockCode(),damage.getWarehouse());
		
			Integer oldqty=stockWarehouse.getQty();
			System.out.println("old qty"+oldqty);
			Integer newqty=damage.getDamageQty();
			
			System.out.println("new qty"+newqty);
			if(oldqty<newqty) {
				return   damage.getStock().getStockCode()+ " Damage Qualtity is Greater";
			}else {
				stockWarehouse.setQty(oldqty-newqty);
			}
			damage.setCurrentQty(oldqty);
			
			damage.setRemark(nre);
			damage.setDamageCode(damageNo);
			damageJpa.insert(damage);
		}
		
		damageList.removeAll(damageList);
		return "Success";
		
	
	
}
}
