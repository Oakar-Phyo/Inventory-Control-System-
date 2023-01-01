package com.inventorycontrolsystem.IVCS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventorycontrolsystem.IVCS.formmodel.StockForm;
import com.inventorycontrolsystem.IVCS.model.Currency;
import com.inventorycontrolsystem.IVCS.model.Department;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.model.Unit;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.IssueJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.SupplierJpa;
import com.inventorycontrolsystem.IVCS.service.UnitService;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;

@Controller
public class MasterSetupController {
	
	@Autowired
	SupplierJpa suppJpa;
	
	@Autowired
	WarehouseJpa warJpa;
	
	@Autowired
	StockJpa stockjpa;
	
	@Autowired
	private UnitService unitservice;

	@Autowired
	private DepartmentJpa dpaJpa;
	
	@Autowired
	CurrencyService currencyService;
	
	@Autowired
   StockInJpa stockInJpa;
	
	@Autowired
	IssueJpa issueJpa;

	//Supplier Start
	
	@GetMapping("/IVCS_SUP_01")
	public String supplierSetup(Model model) {
		//Setup List 
		model.addAttribute("listSupplier", suppJpa.findAll());
		
		
		Supplier supplier = new Supplier();
		List<Supplier> suppList = suppJpa.findAll();
		if (suppList.isEmpty()) {
			supplier.setSupplierCode("SUP001");
			model.addAttribute("newSupplier", supplier);
		} else {
			for (int i = 0; i < suppList.size(); i++) {
				Supplier suppObj = suppList.get(i);
				Integer id = suppObj.getId() + 1;
				String suppCode = String.format("SUP%03d", id);
				supplier.setSupplierCode(suppCode);
				model.addAttribute("newSupplier", supplier);
			}
		}
		
		return "/MasterSetup/IVCS_SUP_01";
	}
	
	@PostMapping("/addSupplier")
	public String addSupplier(@ModelAttribute("newSupplier")@Validated Supplier supplier ,
			BindingResult bindingResult, Model model,RedirectAttributes redirAttrs ) {
		
		model.addAttribute("listSupplier", suppJpa.findAll());
	    if (bindingResult.hasErrors()) {
	    	model.addAttribute("listSupplier", suppJpa.findAll());
			model.addAttribute("newSupplier", supplier);
			return "MasterSetup/IVCS_SUP_01";
		}
	   
		Supplier supp = new Supplier();
		supp.setSupplierCode(supplier.getSupplierCode());
		supp.setName(supplier.getName());
		supp.setPhone(supplier.getPhone());
		supp.setAddress(supplier.getAddress());
		suppJpa.insert(supp);
		redirAttrs.addFlashAttribute("msg", "Supplier Insert Successful");
	    //}
		return "redirect:/IVCS_SUP_01";	
	}
	
	@GetMapping("/deleteSupplier/{id}")
	public String deleteSupplier(@PathVariable("id") Integer id,RedirectAttributes redirectAtt) {
		
		List<Supplier>supplier=suppJpa.findAll();
		if(supplier.size()!=0) {

			if(isSupplierIdAlreadyUsed(id)) {

			redirectAtt.addFlashAttribute("err", "Supplier is Already Used!!");
			return "redirect:/IVCS_SUP_01";

			} else {
			suppJpa.deleteById(id);
			redirectAtt.addFlashAttribute("msg", "Supplier Delete Successful");
			return "redirect:/IVCS_SUP_01";
			}
		}
		return "redirect:/IVCS_SUP_01";

	}
	
	
	private boolean isSupplierIdAlreadyUsed(Integer supplierId) {
		List<StockIn> stockIns = stockInJpa.findAll();
		for (StockIn stockIn : stockIns) {
		if (stockIn.getSupplier() == null ) {
		continue;
		}
		if (supplierId == stockIn.getSupplier().getId()) {
		return true;
		}
		}
		return false;
		}
	
	
	
	
	//Supplier Update
	@GetMapping("/IVCS_SUP_03/{id}")
	public String supplierUpdate(@PathVariable("id") Integer id, Model model) {
		Supplier supplier = suppJpa.selectOne(id);
		model.addAttribute("supplier",supplier);
		return "/MasterSetup/IVCS_SUP_03";
	}
	
	
	//Supplier Update
	
	@PostMapping("/updateSupplier/{id}")
	public String updateSupplier(@PathVariable("id") Integer id, @ModelAttribute("supplier") @Valid Supplier supplier,
			BindingResult bindingResult,RedirectAttributes rediAttrs, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("supplier", supplier);
			return "MasterSetup/IVCS_SUP_03";
		}
		Supplier updateSupplier = suppJpa.findById(id);
		updateSupplier.setSupplierCode(supplier.getSupplierCode());
		updateSupplier.setName(supplier.getName());
		updateSupplier.setPhone(supplier.getPhone());
		updateSupplier.setAddress(supplier.getAddress());
		suppJpa.update(updateSupplier);
		rediAttrs.addFlashAttribute("msg", "Supplier Update Successful");
		return "redirect:/IVCS_SUP_01";
	}
	
//	Supplier end
	
	
//	Warehouse Start
	
	@GetMapping("/IVCS_WAR_01")
	public String warehouseSetup(Model model) {
		//Setup List 
		model.addAttribute("listWarehouse", warJpa.findAll());
		
		Warehouse warehouse = new Warehouse();
		List<Warehouse> warList = warJpa.findAll();
		if (warList.isEmpty()) {
			warehouse.setWarehouseCode("WAR001");
			model.addAttribute("newWarehouse", warehouse);
		} else {
			for (int i = 0; i < warList.size(); i++) {
				Warehouse warObj = warList.get(i);
				Integer id = warObj.getId() + 1;
				String warCode = String.format("WAR%03d", id);
				warehouse.setWarehouseCode(warCode);
				model.addAttribute("newWarehouse", warehouse);
			}
		}
		
		return "/MasterSetup/IVCS_WAR_01";
	}
	
	@PostMapping("/addWarehouse")
	public String addWarehouse(@ModelAttribute("newWarehouse")@Validated Warehouse warehouse ,
			BindingResult bindingResult,RedirectAttributes redirAttrs, Model model) {
		

		model.addAttribute("listWarehouse", warJpa.findAll());
	    if (bindingResult.hasErrors()) {
	    	model.addAttribute("listWarehouse", warJpa.findAll());
	    	model.addAttribute("newWarehouse", warehouse);
			return "MasterSetup/IVCS_WAR_01";
		}    
	    
		Warehouse war = new Warehouse();
		war.setWarehouseCode(warehouse.getWarehouseCode());
		war.setName(warehouse.getName());
		war.setLocation(warehouse.getLocation());
		war.setStorekeeper(warehouse.getStorekeeper());
		warJpa.insert(war);
		redirAttrs.addFlashAttribute("msg", "Warehouse Insert Successful");
	    
	    return"redirect:/IVCS_WAR_01";
	}
	
	@GetMapping("/deleteWarehouse/{id}")
	public String deleteWarehouse(@PathVariable("id") Integer id,RedirectAttributes redirectAtt ) {
		List<Warehouse>warehouse=warJpa.findAll();
		if(warehouse.size()!=0) {

			if(isWarehouseIdAlreadyUsed(id)) {

			redirectAtt.addFlashAttribute("err", "Warehouse is Already Used!!");
			return "redirect:/IVCS_WAR_01";

			} else {
			warJpa.deleteById(id);
			redirectAtt.addFlashAttribute("msg", "Warehouse Delete Successful");
			return "redirect:/IVCS_WAR_01";
			}
		}
		return "redirect:/IVCS_WAR_01";

	}
	
	
	private boolean isWarehouseIdAlreadyUsed(Integer warehouseId) {
		List<StockIn> stockIns = stockInJpa.findAll();
		for (StockIn stockIn : stockIns) {
		if (stockIn.getWarehouse() == null ) {
		continue;
		}
		if (warehouseId == stockIn.getWarehouse().getId()) {
		return true;
		}
		}
		return false;
		}

	
	
	//Upadate
	@GetMapping("/IVCS_WAR_03/{id}")
	public String warehouseUpdate(@PathVariable("id") Integer id, Model model) {
		Warehouse warehouse = warJpa.selectOne(id);
		model.addAttribute("warehouse",warehouse);
		return "/MasterSetup/IVCS_WAR_03";
	}
	
	@PostMapping("/updateWarehouse/{id}")
	public String updateWarehouse(@PathVariable("id") Integer id, @ModelAttribute("warehouse") @Valid Warehouse warehouse,
			BindingResult bindingResult,Model model,RedirectAttributes redirAttrs) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("warehouse", warehouse);
			return "MasterSetup/IVCS_WAR_03";
		}
		Warehouse updateWarehouse = warJpa.findById(id);
		updateWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
		updateWarehouse.setName(warehouse.getName());
		updateWarehouse.setLocation(warehouse.getLocation());
		updateWarehouse.setStorekeeper(warehouse.getStorekeeper());
		warJpa.update(updateWarehouse);
		
		redirAttrs.addFlashAttribute("msg", "Warehouse Update Successful");
		return "redirect:/IVCS_WAR_01";
	}

//	Warehouse end

	
	// Unit Start
	
		@GetMapping("/IVCS_UNI_01")
		public String unitSetup(Model model) {
			model.addAttribute("list", unitservice.getAll());
			model.addAttribute("unit", new Unit());
			Unit un = new Unit();
			List<Unit> unit = unitservice.getAll();
			if (unit.isEmpty()) {
				un.setUnitCode("UNI001");
				model.addAttribute("unit", un);
			} else {
				for (int i = 0; i < unit.size(); i++) {
					Unit uni = unit.get(i);
					Integer id = uni.getId() + 1;
					String unid = String.format("UNI%03d", id);
					un.setUnitCode(unid);
					model.addAttribute("unit", un);
				}
			}
				
			

			return "/MasterSetup/IVCS_UNI_01";
		}

		// Unit Add
		@PostMapping(value = "/addUnit")
		public String unitAdd(@ModelAttribute("unit") @Validated Unit unit, BindingResult bs, Model model,
				RedirectAttributes redireAtt) {

			if (bs.hasErrors()) {
				model.addAttribute("list", unitservice.getAll());
				return "/MasterSetup/IVCS_UNI_01";
			}
			
			List<Unit> unitList = new ArrayList<Unit>();
			unitList = unitservice.getUnitByCode(unit);
			if (unitList.size() != 0) {
				String unitCode = unitList.get(0).getUnitCode();

				if (unit.getUnitCode().equals(unitCode)) {
					redireAtt.addFlashAttribute("err", "Unit Code has been Already existed!!");

				} else if (unit.getName().equals(unitList.get(0).getName())) {
					redireAtt.addFlashAttribute("err", "Unit Name has been Already existed!!");
				}
			} else {
				unitservice.insert(unit);
				redireAtt.addFlashAttribute("msg", "Unit Insert Successful");
				model.addAttribute("unit", new Unit());
			}
			return "redirect:/IVCS_UNI_01";
		}

		// Unit Delete
		@GetMapping("/deleteUnit/{id}")
		public String unitDelete(@PathVariable("id") int id, RedirectAttributes redirectAttri ) {
			List<Unit>unit=unitservice.getAll();
			if(unit.size()!=0) {

				if(isUnitIdAlreadyUsed(id)) {

					redirectAttri.addFlashAttribute("err", "Unit is Already Used!!");
				return "redirect:/IVCS_UNI_01";

				} else {
				unitservice.delete(id);
				redirectAttri.addFlashAttribute("msg", "Unit Delete Successful");
				return "redirect:/IVCS_UNI_01";
				}
			}
			return "redirect:/IVCS_UNI_01";

		}
		
		
		private boolean isUnitIdAlreadyUsed(Integer  unitId) {
			List<Stock> stocks = stockjpa.findAll();
			for (Stock stock : stocks) {
			if (stock.getUnit() == null ) {
			continue;
			}
			if (unitId == stock.getUnit().getId()) {
			return true;
			}
			}
			return false;
			}

		// Unit Update
		@GetMapping("/IVCS_UNI_03/{id}")
		public String unitUpdate(@PathVariable("id") int id, Model model) {
			Unit unit = unitservice.selectOne(id);
			model.addAttribute("unit", unit);
			return "/MasterSetup/IVCS_UNI_03";
		}

		@PostMapping("/updateUnit/{id}")
		public String unitUpdate(@PathVariable("id") int id, @Valid Unit unit, BindingResult result,
				RedirectAttributes red) {
			if (result.hasErrors()) {
				unit.setId(id);
				return "/MasterSetup/IVCS_UNI_03";
			}

			List<Unit> unitList = new ArrayList<Unit>();
			unitList = unitservice.getUnitByCode(unit);
			for (int i = 0; i < unitList.size(); i++) {
				if (unit.getName().equals(unitList.get(i).getName())) {
					red.addFlashAttribute("err", "Update Failed!.Unit Name has been already exited!!");
					return "redirect:/IVCS_UNI_01";
				}

			}

			unitservice.update(unit);
			red.addFlashAttribute("msg", "Unit Update Successful");
			return "redirect:/IVCS_UNI_01";

		}
		
//		Unit end
		
//		Stock Start
		
		
		@GetMapping("/IVCS_STK_01")
		public String stockSetup(Model model, HttpServletRequest request, StockForm stockbean) {
		
			Stock stock=new Stock();
			List<Stock> list = stockjpa.findAll();
			List<Unit> unlist = unitservice.getAll();
			
			model.addAttribute("stklist", list);
			request.getServletContext().setAttribute("unlist", unlist);
			
		
			
			if(list.isEmpty()) {
				stock.setStockCode("STK001");
				model.addAttribute("stockbean",stock);
			}
			else {
				for(int i=0; i<list.size();i++) {
					Stock stkcode=list.get(i);
					Integer id=stkcode.getId()+1;
					String stkid=String.format("STK%03d", id);
					stock.setStockCode(stkid);
					model.addAttribute("stockbean",stock);
				}
			}
			
			
			return "/MasterSetup/IVCS_STK_01";
		}

		// StockAdd
		@PostMapping(value = "/addStock")
		public String addingStock(Model model, @ModelAttribute("stockbean") @Validated StockForm stockbean, BindingResult br,
				RedirectAttributes redirectAtt,HttpServletRequest request) {
			
			if (br.hasErrors()) {
				List<Stock> list = stockjpa.findAll();
				model.addAttribute("stklist", list);
				List<Unit> unlist = unitservice.getAll();
				request.getServletContext().setAttribute("unlist", unlist);
				
				return "/MasterSetup/IVCS_STK_01";
			} 

				//handle stockName invaild
				List <Stock> namelist= new ArrayList<>();
				
				namelist = stockjpa.findByName(stockbean);
				
				if(namelist.size()!=0) {
					if(namelist.get(0).getName().equals(stockbean.getName())){
						
					redirectAtt.addFlashAttribute("err", "Stock Name has been already existed!!");
					return "redirect:/IVCS_STK_01";
					}
					
				}
				else {
					
					Unit unit = unitservice.selectOne(stockbean.getUnit());
							//unitservice.findById(stockbean.getUnit());
					Stock stock = new Stock();
					stock.setStockCode(stockbean.getStockCode());
					stock.setName(stockbean.getName());
					stock.setUnit(unit);
					stock.setPurprise(stockbean.getPurprise());
					stock.setSellprice(stockbean.getSellprice());
					stock.setStocktype(stockbean.getStocktype());
					
					
					
					stockjpa.insert(stock);
					redirectAtt.addFlashAttribute("msg", "Stock Insert Successful");
					
					}
				return "redirect:/IVCS_STK_01";
		}
		@GetMapping("/IVCS_STK_03/{id}")
		public String stockUpdate(Model model, @PathVariable("id") Integer id,HttpServletRequest request) {

		Stock stockbean = stockjpa.selectOne(id);
		List<Unit> unlist = unitservice.getAll();
		request.getServletContext().setAttribute("unlist", unlist);
		model.addAttribute("stockbean", new StockForm(stockbean.getId(), stockbean.getName(), stockbean.getStockCode(), stockbean.getUnit().getId(),
		stockbean.getSellprice(), stockbean.getPurprise(), stockbean.getStocktype()));
		return "/MasterSetup/IVCS_STK_03";
		}

		// stockUpdate
		@PostMapping("updateStock/{id}")
		public String updatingStock(HttpServletRequest request,Model model, @PathVariable("id") Integer id,
		@ModelAttribute("stockbean") @Validated StockForm stockbean, BindingResult br, RedirectAttributes redirectAtt) {

		if (br.hasErrors()) {
		List<Stock> list = stockjpa.findAll();
		model.addAttribute("stklist", list);
		List<Unit> unlist = unitservice.getAll();
		request.getServletContext().setAttribute("unlist", unlist);
		return "/MasterSetup/IVCS_STK_03";
		}


		Unit unit = unitservice.findById(stockbean.getUnit());
		Stock stock = stockjpa.selectOne(id);
		stock.setStockCode(stockbean.getStockCode());
		stock.setName(stockbean.getName());
		stock.setUnit(unit);
		stock.setPurprise(stockbean.getPurprise());
		stock.setSellprice(stockbean.getSellprice());
		stock.setStocktype(stockbean.getStocktype());

		stockjpa.update(stock);
		redirectAtt.addFlashAttribute("msg", "Stock Update Successful");
		return "redirect:/IVCS_STK_01";
		}
		// StockDelete
		@GetMapping("/deleteStock/{id}")
		public String deletingStock(@PathVariable("id") Integer id,RedirectAttributes redirectAtt ) {
			List<Stock>stock=stockjpa.findAll();
			if(stock.size()!=0) {

				if(isStockIdAlreadyUsed(id)) {

				redirectAtt.addFlashAttribute("err", "Stock is Already Used!!");
				return "redirect:/IVCS_STK_01";

				} else {
				stockjpa.delete(id);
				redirectAtt.addFlashAttribute("msg", "Stock Delete Successful");
				return "redirect:/IVCS_STK_01";
				}
			}
			return "redirect:/IVCS_STK_01";

		}
		
		
		private boolean isStockIdAlreadyUsed(Integer stockId) {
			List<StockIn> stockIns = stockInJpa.findAll();
			for (StockIn stockIn : stockIns) {
			if (stockIn.getStock() == null ) {
			continue;
			}
			if (stockId == stockIn.getStock().getId()) {
			return true;
			}
			}
			return false;
			}
		
		

		
		
		
		
//		Stock end

//	Currency Start
	
	@GetMapping("/IVCS_CUY_01")
	public String currencySetup(Model model, Currency currencyBean) {
		//model.addAttribute("currencyBean", currencyBean);
		
		model.addAttribute("currencyList", currencyService.findAllCurrency());
		 List<Currency> cuList = currencyService.findAllCurrency();
		 if(cuList.isEmpty()) {
			 currencyBean.setCurrencyCode("CUY001");
			 
		 } else {
			 for(int i = 0; i < cuList.size(); i++) {
				// Integer cuId = cuList.size()+1; if you want to size of list to generate
				Currency cur = cuList.get(i);
				 Integer cuId = cur.getId()+1;
				 String currencyCode = String.format("CUY%03d", cuId);
				 currencyBean.setCurrencyCode(currencyCode);
		
			 }
		 }
		model.addAttribute("currencyBean", currencyBean);
		return "/MasterSetup/IVCS_CUY_01";
	}

	@PostMapping("/currencyInsert")
	public String currencyInsert(@ModelAttribute("currencyBean") @Validated Currency currencyBean, BindingResult result,
			Model model, RedirectAttributes redirectAttri) {

		if (result.hasErrors()) {
			model.addAttribute("currencyList", currencyService.findAllCurrency());
			return "/MasterSetup/IVCS_CUY_01";
		}
		List<Currency> currencyList = new ArrayList<Currency>();
		currencyList = currencyService.getByCurrencyCode(currencyBean);

		if (currencyList.size() != 0) {

		 if (currencyBean.getName().equals(currencyList.get(0).getName())) {
				redirectAttri.addFlashAttribute("error", "Currency name has been already existed!!");
				return "redirect:/IVCS_CUY_01";
				
			}  else if (currencyBean.getSname().equals(currencyList.get(0).getSname())) {
				redirectAttri.addFlashAttribute("error", "Currency Short name has been already existed!!");
				return "redirect:/IVCS_CUY_01";
			}
		} else {

			currencyService.insertCurrency(currencyBean);
			redirectAttri.addFlashAttribute("message", "Currency Insert Successful");
			model.addAttribute("currencyBean", currencyBean);

		}

		return "redirect:/IVCS_CUY_01";

	}

	@GetMapping("/IVCS_CUY_03/{id}")
	public String currencyUpdateForm(@PathVariable("id") int id, Model model) {
		Currency currency = currencyService.selectOne(id);

		model.addAttribute("currencyBean", currency);
		return "/MasterSetup/IVCS_CUY_03";
	}

	//update Currency
	@PostMapping("/updateCurrency/{id}")
	public String currencyUpdate(@PathVariable("id") int id,
	@ModelAttribute("currencyBean") @Valid Currency currencyBean, BindingResult result,
	RedirectAttributes redirectAttri, Model model) {

	if (result.hasErrors()) {
	currencyBean.setId(id);
	return "/MasterSetup/IVCS_CUY_03";
	}

	currencyService.updateCurrency(currencyBean);
	redirectAttri.addFlashAttribute("message", "Currency Update Successful");
	return "redirect:/IVCS_CUY_01";
	
	}
	
	
	//delete currency
	@GetMapping("/deleteCurrency/{id}")
	public String deleteCurrency(@PathVariable("id") int id, Currency currency, RedirectAttributes redirectAttri) {
		List<Currency>currencys=currencyService.findAllCurrency();
		if(currencys.size()!=0) {

			if(isCurrencyIdAlreadyUsed(id)) {

				redirectAttri.addFlashAttribute("error", "Currency is Already Used!!");
			return "redirect:/IVCS_CUY_01";

			} else {
			currencyService.deleteCurrency(id);
			redirectAttri.addFlashAttribute("message", "Currency Delete Successful");
			return "redirect:/IVCS_CUY_01";
			}
		}
		return "redirect:/IVCS_CUY_01";

	}
	
	
	private boolean isCurrencyIdAlreadyUsed(Integer currencyId) {
		List<StockIn> stockIns = stockInJpa.findAll();
		for (StockIn stockIn : stockIns) {
		if (stockIn.getCurrency() == null ) {
		continue;
		}
		if (currencyId == stockIn.getCurrency().getId()) {
		return true;
		}
		}
		return false;
		}
	

//	Currency end
	
	
	// Department Start
		@GetMapping("/IVCS_DPT_01")
		public String departementSetup(Model model ) {
			model.addAttribute("dpt", new Department());
			model.addAttribute("list", dpaJpa.findAll());
			Department department=new Department();
			List<Department> dptList =dpaJpa.findAll();
			if (dptList.isEmpty()) {
				department.setDepCode("DPT001");
				model.addAttribute("dpt", department);
			} else {
				for (int i = 0; i < dptList.size(); i++) {
					Department uni = dptList.get(i);
					Integer id = uni.getId() + 1;
					String unid = String.format("DPT%03d", id);
					department.setDepCode(unid);
					model.addAttribute("dpt", department);
				}
			}
			
			
			
			return "/MasterSetup/IVCS_DPT_01";
		}

		@PostMapping("/addDpt")
		public String addDpt(@ModelAttribute("dpt") @Valid Department dpt, BindingResult bindingResult, Model model,
				RedirectAttributes redireAtt) {

			if (bindingResult.hasErrors()) {
				model.addAttribute("list", dpaJpa.findAll());
				return "/MasterSetup/IVCS_DPT_01";
			}
			List<Department> depList = new ArrayList<Department>();
			depList = dpaJpa.getDepByCode(dpt);
			if (depList.size() != 0) {
				String depCode = depList.get(0).getDepCode();

				if (dpt.getDepCode().equals(depCode)) {
					redireAtt.addFlashAttribute("err", "Department Code has been already existed!!");

				} else if (dpt.getName().equals(depList.get(0).getName())) {
					redireAtt.addFlashAttribute("err", "Department Name has been already existed!!");
				}
			} else {
				dpaJpa.insert(dpt);
				redireAtt.addFlashAttribute("msg", "Department Insert Successful");
				model.addAttribute("dpt", new Department());
			}
			return "redirect:/IVCS_DPT_01";
		}
		
		
		
		
		

		@GetMapping("/deleteDpt/{id}")
		public String deleteDpt(@PathVariable("id") int id,RedirectAttributes redirectAttri ) {
			List<Department>department=dpaJpa.findAll();
			if(department.size()!=0) {

				if(isDepartmentIdAlreadyUsed(id)) {

					redirectAttri.addFlashAttribute("err", "Department is Already Used!!");
				return "redirect:/IVCS_DPT_01";

				} else {
				dpaJpa.deleteById(id);
				redirectAttri.addFlashAttribute("msg", "Department Delete Successful");
				return "redirect:/IVCS_DPT_01";
				}
			}
			return "redirect:/IVCS_DPT_01";

		}
		
		
		private boolean isDepartmentIdAlreadyUsed(Integer  departmentId) {
			List<Issue> issues = issueJpa.findAll();
			for (Issue issue : issues) {
			if (issue.getDepartment() == null ) {
			continue;
			}
			if (departmentId == issue.getDepartment().getId()) {
			return true;
			}
			}
			return false;
			}
		
	
		
		@GetMapping("/IVCS_DPT_03/{id}")
		public String departmentUpdate(@PathVariable("id") int id, Model model) {
			Department dpt = dpaJpa.selectOne(id);
			model.addAttribute("dpt", dpt);
			return "/MasterSetup/IVCS_DPT_03";
		}

		@PostMapping("/updateDPT/{id}")

		public String dptUpdate(@PathVariable("id") int id, @ModelAttribute("dpt") @Validated Department dpt,
				BindingResult bindingResult, RedirectAttributes red) {

			if (bindingResult.hasErrors()) {
				dpt.setId(id);
				return "/MasterSetup/IVCS_DPT_03";
			}

			// update already exit
			List<Department> dptList = new ArrayList<Department>();
			dptList = dpaJpa.getDepByCode(dpt);
			for (int i = 0; i < dptList.size(); i++) {
				if (dpt.getName().equals(dptList.get(i).getName())) {
					red.addFlashAttribute("err", "Update Failed! Department Name has been already existed!!");
					return "redirect:/IVCS_DPT_01";
				}

			}
			dpaJpa.update(dpt);
			red.addFlashAttribute("msg", "Department Update Success");
			return "redirect:/IVCS_DPT_01";
		}
		
//		Department end
}
