package com.inventorycontrolsystem.IVCS.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Currency;
import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Department;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.IssueReturn;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.StockWarehouse;
import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.model.Unit;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;
import com.inventorycontrolsystem.IVCS.service.AdjustmentJpa;
import com.inventorycontrolsystem.IVCS.service.CurrencyService;
import com.inventorycontrolsystem.IVCS.service.DamageJpa;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.IssueJpa;
import com.inventorycontrolsystem.IVCS.service.IssueReturnJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockOutJpa;
import com.inventorycontrolsystem.IVCS.service.StockWarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.SupplierJpa;
import com.inventorycontrolsystem.IVCS.service.UnitService;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseTransferJpa;
import com.inventorycontrolsystem.IVCS.view.AdjustmentExcelExporter;
import com.inventorycontrolsystem.IVCS.view.AdjustmentPDFExporter;
import com.inventorycontrolsystem.IVCS.view.DamageExcelExporter;
import com.inventorycontrolsystem.IVCS.view.DamagePDFExporter;
import com.inventorycontrolsystem.IVCS.view.IssueExcelExporter;
import com.inventorycontrolsystem.IVCS.view.IssuePDFExporter;
import com.inventorycontrolsystem.IVCS.view.IssueReturnExcelExporter;
import com.inventorycontrolsystem.IVCS.view.IssueReturnPDFExporter;
import com.inventorycontrolsystem.IVCS.view.ReceiveReturnExcelExporter;
import com.inventorycontrolsystem.IVCS.view.ReceiveReturnPDFExporter;
import com.inventorycontrolsystem.IVCS.view.TransactionPDFExporter;
import com.inventorycontrolsystem.IVCS.view.TransactionsExcelExporter;
import com.inventorycontrolsystem.IVCS.view.WarehouseTransferExcelExporter;
import com.inventorycontrolsystem.IVCS.view.WarehouseTransferPDFExporter;
import com.lowagie.text.DocumentException;

@Controller
public class InventoryTransactionController {

	@Autowired
	StockInJpa stockinJpa;
	
	@Autowired
	StockOutJpa stockOutJpa;
	
	@Autowired
	StockJpa stockService;
	@Autowired
	SupplierJpa supplierService;
	@Autowired
	WarehouseJpa warehouseService;
	@Autowired
	UnitService unitService;
	@Autowired
	CurrencyService currencyService;
	@Autowired 
	AdjustmentJpa adjustmentService;
	@Autowired
	DamageJpa damageJpa;
	
	@Autowired
	DepartmentJpa depJpa;
	
	@Autowired
	IssueReturnJpa issueReturnJpa;
	
	@Autowired
	IssueJpa issueJpa;
	@Autowired
	StockWarehouseJpa stockWarehouseService;
	
	
	@Autowired
	WarehouseTransferJpa warehouseTransferJpa;


	// RecieveList
	@GetMapping("/IVCS_IRE_02")
	public String receiveList(Model model) {
		return recivePage(model, 1);
	}
	
//	pagination
	@GetMapping("/recivePage/{pageNumber}")
	public String recivePage(Model model, @PathVariable("pageNumber") int current) {
		
		Page<StockIn> page = stockinJpa.select(current);
		int totalpage = page.getTotalPages();
		List<StockIn> stockInList = stockinJpa.receiveList();
           stockInList = page.getContent();
		//stockInList.addAll(page.getContent());
		
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("stockInList", stockInList);
		model.addAttribute("current", current);
		
		return "Transaction/IVCS_IRE_02";
	}

	// Stock In Add
	@GetMapping("/IVCS_IRE_01")
	public String receiveAddForm(Model model, HttpServletRequest request) {
		
		List <Stock> stockList = stockService.findAll();
		List <Supplier> supplierList = supplierService.findAll();
		List <Unit> unitList = unitService.getAll();
		List <Warehouse> warehouseList = warehouseService.findAll();
		List <Currency> currencyList = currencyService.findAllCurrency();
		model.addAttribute("stockList", stockList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("warehouseList",warehouseList);
		model.addAttribute("currencyList", currencyList);
		
		return "Transaction/IVCS_IRE_01";
	}
	
	// view
	@GetMapping("/IVCS_IRE_05/{code}")
	public String receiveView(@PathVariable("code") String code, Model model) {
		List <StockIn> list =  stockinJpa.receiveViewList(code);
		List <StockIn> stockList = stockinJpa.receiveViewListByStock(code);
	//	List<StockIn> totalList = stockinJpa.receiveViewListByTotal(code);
		model.addAttribute("list", list);
		model.addAttribute("stockList", stockList);
		//model.addAttribute("totalList", totalList);
		return "Transaction/IVCS_IRE_05";
	}

	 //viewReport Export EXCEL
	  @GetMapping("/IVCS_IRE_05/export/excel") public void
	  exportToExcel(HttpServletResponse response, @RequestParam("code") String code, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=inventory_receives_" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);
 
	  List<StockIn> listInventoryREC =
	  stockinJpa.receiveViewListByStock(code);
	  
	  TransactionsExcelExporter excelExporter = new TransactionsExcelExporter(listInventoryREC);
	  
	  excelExporter.export(response); 
	  }
	  
	//transfer view Export PDF
	  @GetMapping("/IVCS_IRE_05/export/pdf")
	    public void exportToPDF_Recieve(HttpServletResponse response, @RequestParam("code") String code, Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=inventory_receive_view_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<StockIn> listInventoryREC =stockinJpa.receiveViewListByStock(code);
	         
	        TransactionPDFExporter tpdfExporter=new TransactionPDFExporter(listInventoryREC);
	        tpdfExporter.export(response); 
	         
	    }
	  
	//transfer view Export EXCEL
	  @GetMapping("/IVCS_TRS_05/export/excel")
	  public void exportToExcelWarehouseTransfer(HttpServletResponse response, @RequestParam("no") String no, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=warehouse_transfer_view" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);
 
	  List<WarehouseTransfer> listWarehouseTransfer =warehouseTransferJpa.reviewListByTransferNo(no);
	  WarehouseTransferExcelExporter wexcelExporter=new WarehouseTransferExcelExporter(listWarehouseTransfer);
	  wexcelExporter.export(response); 
	  }
	  
	//transfer view Export PDF
	  @GetMapping("/IVCS_TRS_05/export/pdf")
	    public void exportToPDF(HttpServletResponse response, @RequestParam("no") String no, Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=warehouse_transfer_view_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<WarehouseTransfer> listWarehouseTransfer =warehouseTransferJpa.reviewListByTransferNo(no);
	         
	        WarehouseTransferPDFExporter wpdfExporter=new WarehouseTransferPDFExporter(listWarehouseTransfer);
	        wpdfExporter.export(response); 
	         
	    }
	  
	
	// Receive Return List
	@GetMapping("/IVCS_IRR_02")
	public String receiveReturnList(Model model) {
		return reciveReturnPage(model, 1);
	}
	
//	pagination
	@GetMapping("/reciveReturnPage/{pageNumber}")
	public String reciveReturnPage(Model model, @PathVariable("pageNumber") int current) {
		
		Page<StockOut> page = stockOutJpa.select(current);
		int totalpage = page.getTotalPages();
		List<StockOut> stockOutList = stockOutJpa.receiveReturnList();
		stockOutList = page.getContent();
		
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("stockOutList", stockOutList);
		model.addAttribute("current", current);
		
		return "Transaction/IVCS_IRR_02";
	}

	// Return Add
	@GetMapping("/IVCS_IRR_01" )
	public String select( Model model) {
		
		List<StockIn> list= stockinJpa.receiveList();
		model.addAttribute("list", list);
		
		return "Transaction/IVCS_IRR_01";
	}

	// view
	@GetMapping("/IVCS_IRR_05/{code}")
	public String returnView(@PathVariable("code") String code, Model model) {
		List <StockOut> list =  stockOutJpa.receiveViewList(code);
		List <StockOut> stockList = stockOutJpa.receiveViewListByStock(code);
		model.addAttribute("list", list);
		model.addAttribute("stockList", stockList);
		return "Transaction/IVCS_IRR_05";
	}
	
	 //viewReport Export EXCEL
	  @GetMapping("/IVCS_IRR_05/export/excel") 
	  public void exportToExcelReceiveReturn(HttpServletResponse response, @RequestParam("code") String code, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=inventory_receive_return_" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);

	  List<StockOut> listInventoryRRC =stockOutJpa.receiveViewListByStock(code);
	  
	  ReceiveReturnExcelExporter excelExporter = new ReceiveReturnExcelExporter(listInventoryRRC);
	  
	  excelExporter.export(response); 
	  }
	  
	//viewReport Export PDF
	  @GetMapping("/IVCS_IRR_05/export/pdf")
	    public void exportToPDFReceiveReturn(HttpServletResponse response, @RequestParam("code") String code, Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=inventory_receive_return_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<StockOut> listInventoryRRC = stockOutJpa.receiveViewListByStock(code);
	         
	        ReceiveReturnPDFExporter irrexporter = new ReceiveReturnPDFExporter(listInventoryRRC);
	        irrexporter.export(response);
	         
	    }
	

	
	// Issue List
	@GetMapping("/IVCS_ISS_02")
	public String issueList(Model model) {
		return issuePage(model, 1);
	}
	
//	pagination
	@GetMapping("/issuePage/{pageNumber}")
	public String issuePage(Model model, @PathVariable("pageNumber") int current) {
		Page<Issue> page = issueJpa.select(current);
		int totalpage=page.getTotalPages();
		List<Issue> issList = page.getContent();
		model.addAttribute("issList", issList);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("current", current);
		return  "Transaction/IVCS_ISS_02";
	}
	
	// Issue Add
	@GetMapping("/IVCS_ISS_01")
	public String issueAdd(Model model) {
		List <Warehouse> warehouseList = warehouseService.findAll();
		List <Currency> currencyList = currencyService.findAllCurrency();
		List<StockIn> list= stockinJpa.receiveList();
		model.addAttribute("list", list);
		model.addAttribute("currencyList", currencyList);
		List <Department> depList = depJpa.findAll();
		model.addAttribute("warehouseList", warehouseList);
		model.addAttribute("depList", depList);
		
		return "Transaction/IVCS_ISS_01";
	}

	// Issue view
	@GetMapping("/IVCS_ISS_05/{code}")
	public String issueView(@PathVariable("code") String code, Model model) {
		List <Issue> list =  issueJpa.receiveViewList(code);
		List <Issue> issueList = issueJpa.receiveViewListByStock(code);
		model.addAttribute("list", list);
		model.addAttribute("issueList", issueList);
		return "Transaction/IVCS_ISS_05";
	}
	//transfer view Export EXCEL
	  @GetMapping("/IVCS_ISS_05/export/excel")
	  public void exportToExcelIssue(HttpServletResponse response, @RequestParam("no") String no, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=issue_view" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);

	  List<Issue> listIssue =issueJpa.receiveViewListByStock(no);
	  IssueExcelExporter issexcelExporter=new IssueExcelExporter(listIssue);
	  issexcelExporter.export(response); 
	  }
	  
	//transfer view Export PDF
	  @GetMapping("/IVCS_ISS_05/export/pdf")
	    public void exportToPDFIssue(HttpServletResponse response, @RequestParam("no") String no, Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=issue_view_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Issue> listIssue =issueJpa.receiveViewListByStock(no);
	         
	        IssuePDFExporter isspdfExporter=new IssuePDFExporter(listIssue);
	        isspdfExporter.export(response); 
	         
	    }

	// Issue Return List
			@GetMapping("/IVCS_ISR_02")
			public String issueReturnList(Model model) {
				return issueReturnPage(model, 1);
			}
		
//		pagination
		@GetMapping("/issueReturnPage/{pageNumber}")
		public String issueReturnPage(Model model, @PathVariable("pageNumber") int current) {
			
			Page<IssueReturn> page = issueReturnJpa.select(current);
			int totalpage = page.getTotalPages();
			List<IssueReturn> stockOutList = issueReturnJpa.issueReturnList();
			stockOutList = page.getContent();
			
			model.addAttribute("totalpage", totalpage);
			model.addAttribute("stockOutList", stockOutList);
			model.addAttribute("current", current);
			
			return "Transaction/IVCS_ISR_02";
		}
		

		// Issue Return Add(list of Issue Transaction) Setup for ISR_01
			@GetMapping("/IVCS_ISR_01" )
			public String selectIssue( Model model) {
				
				List<Issue> list= issueJpa.issueGroupedList();
				model.addAttribute("list", list);
				
				return "Transaction/IVCS_ISR_01";
			}

		
		// Issue Return view
			@GetMapping("/IVCS_ISR_05/{code}")
			public String issuereturnView(@PathVariable("code") String code, Model model) {
				List <IssueReturn> list =  issueReturnJpa.issueReturnViewList(code);
				List <IssueReturn> stockList = issueReturnJpa.issueReturnViewListByStock(code);
			//	List<StockIn> totalList = stockinJpa.receiveViewListByTotal(code);
				model.addAttribute("list", list);
				model.addAttribute("stockList", stockList);
				//model.addAttribute("totalList", totalList);
				return "Transaction/IVCS_ISR_05";
			}

			 //Issue Return Report Export EXCEL
			  @GetMapping("/IVCS_ISR_05/export/excel") 
			  public void exportToExcelIssueReturn(HttpServletResponse response, @RequestParam("code") String code, Model model) throws IOException {
			  response.setContentType("application/octet-stream"); DateFormat dateFormatter
			  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
			  dateFormatter.format(new Date());
			 
			  String headerKey = "Content-Disposition"; String headerValue =
			  "attachment; filename=inventory_issueReturn_" + currentDateTime + ".xlsx";
			  response.setHeader(headerKey, headerValue);
		 
			  List<IssueReturn> listIssueReturn =
			  issueReturnJpa.issueReturnViewListByStock(code);
			  
			  IssueReturnExcelExporter excelExporter = new IssueReturnExcelExporter(listIssueReturn);
			  
			  excelExporter.export(response); 
			  }
			  
			//Issue Return view Export PDF
			  @GetMapping("/IVCS_ISR_05/export/pdf")
			    public void exportToPDFIssueReturn(HttpServletResponse response, @RequestParam("code") String code, Model model) throws DocumentException, IOException {
			        response.setContentType("application/pdf");
			        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			        String currentDateTime = dateFormatter.format(new Date());
			         
			        String headerKey = "Content-Disposition";
			        String headerValue = "attachment; filename=inventory_issue_return_" + currentDateTime + ".pdf";
			        response.setHeader(headerKey, headerValue);
			         
			        List<IssueReturn> listIssueReturn =issueReturnJpa.issueReturnViewListByStock(code);
			         
			        IssueReturnPDFExporter irpdfExporter=new IssueReturnPDFExporter(listIssueReturn);
			        irpdfExporter.export(response); 
			         
			    }
			

	
	
	// Adjustment List
	@GetMapping("/IVCS_ADJ_02")
	public String adjustmentList(Model model) {
		
		 return adjustmentPage(model, 1);
	}
	
//	adjustment pagination
	@GetMapping("/adjustmentPage/{pageNumber}")
	public String adjustmentPage(Model model, @PathVariable("pageNumber") int current) {
		
		Page<Adjustment> page = adjustmentService.findAllList(current);
		int totalpage = page.getTotalPages();
		List<Adjustment> adjustmentList = adjustmentService.selectAll();
		adjustmentList = page.getContent();
		
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("adjustmentList", adjustmentList);
		model.addAttribute("current", current);
		
		return "Transaction/IVCS_ADJ_02";
	}
	
	

	// Adjustment Add
	@GetMapping("/IVCS_ADJ_01")
	public String adjustmentAdd(Model model) {
		List<StockWarehouse> stkwarehouseList=stockWarehouseService.findAll();
		List <Warehouse> warehouseList = warehouseService.findAll();
		model.addAttribute("warehouseList", warehouseList);
		model.addAttribute("stkwarehouseList", stkwarehouseList);
		return "Transaction/IVCS_ADJ_01";
	}

	// Adjustment view
	@GetMapping("/IVCS_ADJ_05/{code}")
	public String adjustmentView(@PathVariable("code") String code, Model model) {
		List <Adjustment> viewList =  adjustmentService.adjustmentView(code);
		List <Adjustment> stockList = adjustmentService.adjustmentViewByStockCode(code);
		model.addAttribute("viewList", viewList);
		model.addAttribute("stockList", stockList);
		
		return "Transaction/IVCS_ADJ_05";
	}
	
	//adjustmentViewReport Export EXCEL
	@GetMapping("/IVCS_ADJ_05/export/excel")
	public void adjustmentExportToExcel(HttpServletResponse response, @RequestParam("code") String code, Model model)
			throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=inventory_adjustment_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Adjustment> adjustmentREC = adjustmentService.adjustmentViewByStockCode(code);

		AdjustmentExcelExporter adj = new AdjustmentExcelExporter(adjustmentREC);

		adj.export(response);
	}
	
	//adjustmentViewReport Export PDF
	  @GetMapping("/IVCS_ADJ_05/export/pdf")
	    public void adjustmentExportToPDF(HttpServletResponse response, @RequestParam("code") String code, Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=inventory_adjustment_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Adjustment> adjusmentViewList = adjustmentService.adjustmentViewByStockCode(code);
	         
	        AdjustmentPDFExporter exporter = new AdjustmentPDFExporter(adjusmentViewList);
	        exporter.export(response);
	         
	    }


	// Damage List
		@GetMapping("/IVCS_DMG_02")
		public String damageList(Model model) {

		return damagePage(model,1);
		}

		// pagination
		@GetMapping("/damagePage/{pageNumber}")
		public String damagePage(Model model, @PathVariable("pageNumber") int current) {
			
			
			Page<Damage> page = damageJpa.select(current);
			int totalpage = page.getTotalPages();

			List<Damage> damageList = damageJpa.damageretrunList();
			damageList = page.getContent();

			model.addAttribute("totalpage", totalpage);
			model.addAttribute("damageList", damageList);
			model.addAttribute("current", current);

			return "Transaction/IVCS_DMG_02";

		}

		// Damage Add
		@GetMapping("/IVCS_DMG_01")
		public String damageAdd(Model model) {
			List<Warehouse> warehouseList = warehouseService.findAll();
			model.addAttribute("warehouseList", warehouseList);
			return "Transaction/IVCS_DMG_01";
		}

		// Damage view
		@GetMapping("/IVCS_DMG_05/{code}")
		public String damageView(@PathVariable("code") String code, Model model) {
			
			List<Damage>List=damageJpa.receiveViewList(code);
			List<Damage>damageList=damageJpa.receiveViewListByStock(code);
			model.addAttribute("list",List);
			model.addAttribute("damageList",damageList);
			return "Transaction/IVCS_DMG_05";
		}
		
		
		
		//DamageviewReport Export EXCEL
		@GetMapping("/IVCS_DMG_05/export/excel")
		public void damageExportToExcel(HttpServletResponse response, @RequestParam("code") String code, Model model)
		throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=inventory_Damage_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Damage>damageREC = damageJpa.receiveViewListByStock(code);

		DamageExcelExporter adj = new DamageExcelExporter(damageREC);

		adj.export(response);
		}
		
		
		
		//Damage Report Export PDF
		@GetMapping("/IVCS_DMG_05/export/pdf")
		public void damageExportToPDF(HttpServletResponse response, @RequestParam("code") String code, Model model) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=inventoryDamage_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Damage> damageViewList = damageJpa.receiveViewListByStock(code) ;

		DamagePDFExporter exporter = new DamagePDFExporter(damageViewList);
		exporter.export(response);

		}

		// Transfer List
		@GetMapping("/IVCS_TRS_02")
		public String transferList(Model model) {
			return transferPage(model, 1);
		}
		
		@GetMapping("/transferPage/{pageNumber}")
		public String transferPage(Model model, @PathVariable("pageNumber") int current) {
			Page<WarehouseTransfer> page = warehouseTransferJpa.select(current);
			int totalpage=page.getTotalPages();
			List<WarehouseTransfer> whtList = page.getContent();
			model.addAttribute("whtList", whtList);
			model.addAttribute("totalpage", totalpage);
			model.addAttribute("current", current);
			return "Transaction/IVCS_TRS_02";
		}
		
		// Transfer Add
		@GetMapping("/IVCS_TRS_01")
		public String transferAdd(Model model) {
			model.addAttribute("warehouseList", warehouseService.findAll());
			return "Transaction/IVCS_TRS_01";
		}

		// Transfer view
		@GetMapping("/IVCS_TRS_05/{treNo}")
		public String transferView(Model model,@PathVariable("treNo")String treNo) {
			int totalQty=0;
			int totalAmt=0;
			List<WarehouseTransfer> trlist=warehouseTransferJpa.reviewListByTransferNo(treNo);
			List<WarehouseTransfer> trlistAll=warehouseTransferJpa.reviewListByTransferNoAll(treNo);
			for(WarehouseTransfer list:trlistAll) {
				totalQty=totalQty+list.getTransferQty();
				totalAmt=(int) (totalAmt+list.getTotal());
			}
			model.addAttribute("list", trlist);
			model.addAttribute("listAll", trlistAll);
			model.addAttribute("totalItem", trlistAll.size());
			model.addAttribute("totalQty", totalQty);
			model.addAttribute("totalAmt", totalAmt);
			model.addAttribute("remark", trlist.get(0).getRemark());
			return "Transaction/IVCS_TRS_05";
		}

}