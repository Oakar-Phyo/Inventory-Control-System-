package com.inventorycontrolsystem.IVCS.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventorycontrolsystem.IVCS.Report.AdjustmentReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.AdjustmentReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.DamageReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.DamageReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.IssueReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.IssueReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.IssueReturnReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.IssueReturnReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.ReceiveReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.ReceiveReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.ReceiveReturnReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.ReceiveReturnReportPDFExporter;
import com.inventorycontrolsystem.IVCS.Report.WarehouseTransferReportExcelExporter;
import com.inventorycontrolsystem.IVCS.Report.WarehouseTransferReportPDFExporter;
import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.IssueReturn;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;
import com.inventorycontrolsystem.IVCS.service.AdjustmentJpa;
import com.inventorycontrolsystem.IVCS.service.DamageJpa;
import com.inventorycontrolsystem.IVCS.service.DepartmentJpa;
import com.inventorycontrolsystem.IVCS.service.IssueJpa;
import com.inventorycontrolsystem.IVCS.service.IssueReturnJpa;
import com.inventorycontrolsystem.IVCS.service.StockInJpa;
import com.inventorycontrolsystem.IVCS.service.StockJpa;
import com.inventorycontrolsystem.IVCS.service.StockOutJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseJpa;
import com.inventorycontrolsystem.IVCS.service.WarehouseTransferJpa;
import com.lowagie.text.DocumentException;

@Controller
public class ReportController {
	@Autowired
	WarehouseTransferJpa  warehouseTransferJpa;
	@Autowired
	StockJpa stockJpa;
	@Autowired
	WarehouseJpa warehouseJpa;
	@Autowired
	DepartmentJpa departmentJpa;
	@Autowired
	IssueJpa issueJpa;
	@Autowired
	StockInJpa stockInJpa;
	@Autowired
	DamageJpa damageJpa;
	@Autowired
	AdjustmentJpa adjustmentJpa;
	@Autowired
	StockOutJpa stockoutJpa;
	@Autowired
	IssueReturnJpa issueReturnJpa;
	
	List<Adjustment> adjustmentList = new ArrayList<Adjustment>();
	List<StockOut> stockOutList = new ArrayList<StockOut>();
	@GetMapping("/IVCS_ADJ_04")
	public String adjustmentReport(Model model) {
		
		return adjustmentReportDetail(model,1,null,null,"","",true);
	}
	
	//adjustmentReport
	@GetMapping("/adjustmentReport")
	public String adjustmentReportDetail(Model model, @RequestParam("pageNumber") int current,@Param("stockCode") String stockCode, @Param("warehouseCode") String warehouseCode,@Param("startDate") String startDate,
			@Param("endDate") String endDate, boolean isInit) {
		Stock stock=stockJpa.selectStockCodeForReturn(stockCode);
		Warehouse warehouse=warehouseJpa.selectByWarehouseCode(warehouseCode);
		Page<Adjustment> page;
		List<Adjustment> adjList;
		int totalPage = 0;
		if (isInit) {
			adjList = new ArrayList<Adjustment>();
		}else {
			page = adjustmentJpa.adjustmentSearch(current, stock, warehouse, startDate, endDate);
			totalPage = page.getTotalPages();
			adjList = page.getContent();
		}
		
		adjustmentList = adjList;
		
		model.addAttribute("adjList", adjList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("current", current);
		model.addAttribute("stockCode", stockCode);
		model.addAttribute("warehouseCode", warehouseCode);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "Report/IVCS_ADJ_04";
	}
	
	//adjustment Report Export EXCEL
	  @GetMapping("/IVCS_ADJ_04/reportExport/excel")
		public void adjustmentReportExcelExporter(HttpServletResponse response, Model model) throws IOException {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=Adjustment_report" + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);

			AdjustmentReportExcelExporter wexcelExporter = new AdjustmentReportExcelExporter(adjustmentList);
			wexcelExporter.export(response);
		}
	  
	//adjustment report Export PDF
	  @GetMapping("/IVCS_ADJ_04/reportExport/pdf")
	    public void adjustmentReportPDFExporter(HttpServletResponse response,Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=Adjustment_report_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	       AdjustmentReportPDFExporter wpdfExporter=new AdjustmentReportPDFExporter(adjustmentList);
	        wpdfExporter.export(response); 
	         
	    }
	
	
	  
	  @GetMapping("/IVCS_DMG_04")
		public String damageReport(Model model) {
		 
	  return damageReportPage(model, 1,null,null,"","",true);
		  
		}
	  
	  
	  List<Damage> damagelist=new ArrayList<Damage>();
		@GetMapping("/damageReportPage")
		public String damageReportPage(Model model, @RequestParam("pageNumber") int current,@Param("code")String code
				,@Param("wCode")String wCode,@Param("start")String start,@Param("end")String end ,boolean test) {
		
			
			
		    Stock stk=stockJpa.selectStockCodeForReturn(code);
			Warehouse wh=warehouseJpa.selectByWarehouseCode(wCode);
			Page<Damage> page;
			List<Damage> dmgList;
			int totalpage=0;
			if(test) {
				dmgList=new ArrayList<Damage>();
			}else {
			
				page=	damageJpa.selectDamageReport(current,stk,wh,start,end);
				totalpage=page.getTotalPages();
				dmgList= page.getContent();
			}

			damagelist=dmgList;
			model.addAttribute("dmgList", dmgList);
			model.addAttribute("totalpage", totalpage);
			model.addAttribute("current", current);
			model.addAttribute("code", code);
			model.addAttribute("wCode", wCode);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			return "Report/IVCS_DMG_04";	
		}
		
		//Damage report Export EXCEL
		  @GetMapping("/IVCS_DMG_04/reportExport/excel")
		  public void exportToExcelDamage(HttpServletResponse response, Model model) throws IOException {
		  response.setContentType("application/octet-stream"); DateFormat dateFormatter
		  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
		  dateFormatter.format(new Date());
		 
		  String headerKey = "Content-Disposition"; String headerValue =
		  "attachment; filename=damage_report" + currentDateTime + ".xlsx";
		  response.setHeader(headerKey, headerValue);

		  DamageReportExcelExporter wexcelExporter=new DamageReportExcelExporter(damagelist);
		  wexcelExporter.export(response); 
		  }
		  
		  
			//Damage report Export PDF
			  @GetMapping("/IVCS_DMG_04/reportExport/pdf")
			    public void DamagePDF(HttpServletResponse response,Model model) throws DocumentException, IOException {
			        response.setContentType("application/pdf");
			        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			        String currentDateTime = dateFormatter.format(new Date());
			         
			        String headerKey = "Content-Disposition";
			        String headerValue = "attachment; filename=damage_report_" + currentDateTime + ".pdf";
			        response.setHeader(headerKey, headerValue);
			         
			     DamageReportPDFExporter wpdfExporter=new DamageReportPDFExporter(damagelist);
			        wpdfExporter.export(response); 
			         
			    }

	//for receive report 
	@GetMapping("/IVCS_IRE_04")
	public String inventoryReceiveReport(Model model) {
		return receiveReportPage(model, 1,null,null,"","");
	}
	
	List<StockIn> stklist=new ArrayList<StockIn>();
	@GetMapping("/receiveReportPage")
	public String receiveReportPage(Model model, @RequestParam("pageNumber") int current,@Param("code")String code
	,@Param("wCode")String wCode,@Param("start")String start,@Param("end")String end ) {
		Stock stk=stockJpa.selectStockCodeForReturn(code);
		Warehouse wh=warehouseJpa.selectByWarehouseCode(wCode);
		Page<StockIn> page = stockInJpa.selectForReport(current,stk,wh,start,end);
		int totalpage=page.getTotalPages();
		List<StockIn> stockList = page.getContent();
		stklist=stockList;
		model.addAttribute("stockList", stockList);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("current", current);
		model.addAttribute("code", code);
		model.addAttribute("wCode", wCode);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		
		return "Report/IVCS_IRE_04";
	}
	//Receive report Export EXCEL
	  @GetMapping("/IVCS_IRE_04/reportExport/excel")
	  public void exportToExcelReceive(HttpServletResponse response, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=inventory_receive_report" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);

	  ReceiveReportExcelExporter stkexcelExporter=new ReceiveReportExcelExporter(stklist);
	  stkexcelExporter.export(response); 
	  }
	  
	//transfer report Export PDF
	  @GetMapping("/IVCS_IRE_04/reportExport/pdf")
	    public void exportToPDFReceive(HttpServletResponse response,Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=inventory_receive_report_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	       ReceiveReportPDFExporter stkpdfExporter=new ReceiveReportPDFExporter(stklist);
	        stkpdfExporter.export(response); 
	         
	    }

	
	

		@GetMapping("/IVCS_IRR_04")
		public String inventoryReceivedReturnReport(Model model) {
			return  receiveReturnReportDetail(model,1,null,null,"","",true);
		}
		
		@GetMapping("/receiveRturnReport")
		public String receiveReturnReportDetail(Model model, @RequestParam("pageNumber") int current,@Param("stockCode") String stockCode, @Param("warehouseCode") String warehouseCode,@Param("startDate") String startDate,
				@Param("endDate") String endDate, boolean isInit) {
			
			Stock stock=stockJpa.selectStockCodeForReturn(stockCode);
			Warehouse warehouse=warehouseJpa.selectByWarehouseCode(warehouseCode);
			int totalPage = 0;
			List<StockOut> receiveRetList;
			Page<StockOut> page;
			if(isInit) {
				receiveRetList = new ArrayList<StockOut>();
			} else {
				page = stockoutJpa.receiveReturnSearch(current, stock, warehouse, startDate, endDate);
				totalPage = page.getTotalPages();
				receiveRetList = page.getContent();
			}
			stockOutList = receiveRetList;
			model.addAttribute("receiveRetList", receiveRetList);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("current", current);
			model.addAttribute("stockCode", stockCode);
			model.addAttribute("warehouseCode", warehouseCode);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			return "Report/IVCS_IRR_04"; 
		}
		
		//receiveReturn report Export EXCEL
		  @GetMapping("/IVCS_IRR_04/reportExport/excel")
		  public void exportToExcelReceiveReturnReport(HttpServletResponse response, Model model) throws IOException {
		  response.setContentType("application/octet-stream"); DateFormat dateFormatter
		  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
		  dateFormatter.format(new Date());
		 
		  String headerKey = "Content-Disposition"; String headerValue =
		  "attachment; filename=receive_return_report" + currentDateTime + ".xlsx";
		  response.setHeader(headerKey, headerValue);

		  ReceiveReturnReportExcelExporter wexcelExporter=new ReceiveReturnReportExcelExporter(stockOutList);
		  wexcelExporter.export(response); 
		  }
		  
		  //receiveReturn report Export PDF
		  @GetMapping("/IVCS_IRR_04/reportExport/pdf")
		    public void receiveReturnReportPDFExporter(HttpServletResponse response,Model model) throws DocumentException, IOException {
		        response.setContentType("application/pdf");
		        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		        String currentDateTime = dateFormatter.format(new Date());
		         
		        String headerKey = "Content-Disposition";
		        String headerValue = "attachment; filename=receive_return_report_" + currentDateTime + ".pdf";
		        response.setHeader(headerKey, headerValue);
		         
		       ReceiveReturnReportPDFExporter wpdfExporter=new ReceiveReturnReportPDFExporter(stockOutList);
		        wpdfExporter.export(response); 
		         
		    }
		  

	//for report 
		@GetMapping("/IVCS_ISS_04")
		public String inventoryIssueReport(Model model) {
			return issueReportPage(model, 1,null,null,"","");
		}
	List<Issue> slist=new ArrayList<Issue>();
	@GetMapping("/issueReportPage")
	public String issueReportPage(Model model, @RequestParam("pageNumber") int current,@Param("code")String code
	,@Param("wCode")String wCode,@Param("start")String start,@Param("end")String end ) {
		Stock stk=stockJpa.selectStockCodeForReturn(code);
		Warehouse wh=warehouseJpa.selectByWarehouseCode(wCode);
		Page<Issue> page = issueJpa.selectForReport(current,stk,wh,start,end);
		int totalpage=page.getTotalPages();
		List<Issue> issList = page.getContent();
		slist=issList;
		model.addAttribute("issList", issList);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("current", current);
		model.addAttribute("code", code);
		model.addAttribute("wCode", wCode);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		
		return "Report/IVCS_ISS_04";
	}
	
	//Issue report Export EXCEL
	  @GetMapping("/IVCS_ISS_04/reportExport/excel")
	  public void exportToExcelIssue(HttpServletResponse response, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=issue_report" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);

	  IssueReportExcelExporter issexcelExporter=new IssueReportExcelExporter(slist);
	  issexcelExporter.export(response); 
	  }
	  
	//Issue report Export PDF
	  @GetMapping("/IVCS_ISS_04/reportExport/pdf")
	    public void exportToPDFIssue(HttpServletResponse response,Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=issue_report_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	       IssueReportPDFExporter isspdfExporter=new IssueReportPDFExporter(slist);
	        isspdfExporter.export(response); 
	         
	    }

	
	  //Issue Return Report
	  @GetMapping("/IVCS_ISR_04")
		public String inventoryIssueReturnReport(Model model) {
			return  issueReturnReportDetail(model,1,null,null,"","",true);
		}
		
	  List<IssueReturn> issueReturnList=new ArrayList<IssueReturn>();
		@GetMapping("/issueReturnReport")
		public String issueReturnReportDetail(Model model, @RequestParam("pageNumber") int current,@Param("stockCode") String stockCode, @Param("warehouseCode") String warehouseCode,@Param("startDate") String startDate,
				@Param("endDate") String endDate, boolean isInit) {
			
			Stock stock=stockJpa.selectStockCodeForReturn(stockCode);
			Warehouse warehouse=warehouseJpa.selectByWarehouseCode(warehouseCode);
			int totalPage = 0;
			List<IssueReturn> issueRtnList;
			Page<IssueReturn> page;
			if(isInit) {
				issueRtnList = new ArrayList<IssueReturn>();
			} else {
				page = issueReturnJpa.issueReturnSearch(current, stock, warehouse, startDate, endDate);
				totalPage = page.getTotalPages();
				issueRtnList = page.getContent();
			}
			issueReturnList = issueRtnList;
			model.addAttribute("receiveRetList", issueRtnList);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("current", current);
			model.addAttribute("stockCode", stockCode);
			model.addAttribute("warehouseCode", warehouseCode);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			System.out.println(issueRtnList.size());
			
			return "Report/IVCS_ISR_04"; 
		}
		
		//Issue Return report Export EXCEL
		  @GetMapping("/IVCS_ISR_04/reportExport/excel")
		  public void exportToExcelIssueReturn(HttpServletResponse response, Model model) throws IOException {
		  response.setContentType("application/octet-stream"); DateFormat dateFormatter
		  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
		  dateFormatter.format(new Date());
		 
		  String headerKey = "Content-Disposition"; String headerValue =
		  "attachment; filename=inventory_issueReturn_report" + currentDateTime + ".xlsx";
		  response.setHeader(headerKey, headerValue);

		  IssueReturnReportExcelExporter stkexcelExporter=new IssueReturnReportExcelExporter(issueReturnList);
		  stkexcelExporter.export(response); 
		  }
		  
		//Issue Return report Export PDF
		  @GetMapping("/IVCS_ISR_04/reportExport/pdf")
		    public void exportToPDFIssueReturn(HttpServletResponse response,Model model) throws DocumentException, IOException {
		        response.setContentType("application/pdf");
		        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		        String currentDateTime = dateFormatter.format(new Date());
		         
		        String headerKey = "Content-Disposition";
		        String headerValue = "attachment; filename=issueReturn_report_" + currentDateTime + ".pdf";
		        response.setHeader(headerKey, headerValue);
		         
		        IssueReturnReportPDFExporter issRtnpdfExporter=new IssueReturnReportPDFExporter(issueReturnList);
		        issRtnpdfExporter.export(response); 
		         
		    }
		  
	//for report 
	@GetMapping("/IVCS_TRS_04")
	public String inventoryTransferReport(Model model) {
		return transferReportPage(model, 1,null,null,null,"","");
	}
	List<WarehouseTransfer> list=new ArrayList<WarehouseTransfer>();
	@GetMapping("/transferReportPage")
	public String transferReportPage(Model model, @RequestParam("pageNumber") int current,@Param("code")String code
			,@Param("wCode")String wCode,@Param("twCode")String twCode,@Param("start")String start,@Param("end")String end ) {
		Stock stk=stockJpa.selectStockCodeForReturn(code);
		Warehouse wh1=warehouseJpa.selectByWarehouseCode(wCode);
		Warehouse wh2=warehouseJpa.selectByWarehouseCode(twCode);
		Page<WarehouseTransfer> page = warehouseTransferJpa.selectForReport(current,stk,wh1,wh2,start,end);
		int totalpage=page.getTotalPages();
		List<WarehouseTransfer> whtList = page.getContent();
		list=whtList;
		model.addAttribute("whtList", whtList);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("current", current);
		model.addAttribute("code", code);
		model.addAttribute("wCode", wCode);
		model.addAttribute("twCode", twCode);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		return "Report/IVCS_TRS_04";
	}
	
	//transfer report Export EXCEL
	  @GetMapping("/IVCS_TRS_04/reportExport/excel")
	  public void exportToExcelWarehouseTransfer(HttpServletResponse response, Model model) throws IOException {
	  response.setContentType("application/octet-stream"); DateFormat dateFormatter
	  = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); String currentDateTime =
	  dateFormatter.format(new Date());
	 
	  String headerKey = "Content-Disposition"; String headerValue =
	  "attachment; filename=warehouse_transfer_report" + currentDateTime + ".xlsx";
	  response.setHeader(headerKey, headerValue);

	  WarehouseTransferReportExcelExporter wexcelExporter=new WarehouseTransferReportExcelExporter(list);
	  wexcelExporter.export(response); 
	  }
	  
	//transfer report Export PDF
	  @GetMapping("/IVCS_TRS_04/reportExport/pdf")
	    public void exportToPDF(HttpServletResponse response,Model model) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=warehouse_transfer_report_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	       WarehouseTransferReportPDFExporter wpdfExporter=new WarehouseTransferReportPDFExporter(list);
	        wpdfExporter.export(response); 
	         
	    }

}
