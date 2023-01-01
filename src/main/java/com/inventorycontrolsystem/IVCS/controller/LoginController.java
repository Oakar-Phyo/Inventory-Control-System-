package com.inventorycontrolsystem.IVCS.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventorycontrolsystem.IVCS.service.StockInJpa;



@Controller
public class LoginController {
	@Autowired
	StockInJpa stkInJpa;
	
	@GetMapping("/")
	public String log() {
		return "redirect:/login";
	}
	@GetMapping("/loginError")
	public String loginError(Model model) {
		
		model.addAttribute("error", "Invalid email or password.");
		return "Dashboard/IVCS_LGN_01";
	}
	@GetMapping("/logoutSuccess")
	public String logOut(Model model) {
		
		model.addAttribute("success", " Logout Successful.");
		return "Dashboard/IVCS_LGN_01";
	}
	@GetMapping("/login")
	public String login() {
		
		
		return "Dashboard/IVCS_LGN_01";
	}
	
	
	@GetMapping("/IVCS_LGN_01")
	public String logout() {
		return "Dashboard/IVCS_LGN_01";
	}
	
	@GetMapping("/IVC")
	public String home() {
		return "Dashboard/IVCS_HME_01";
	}
	
	@GetMapping("/IVCS_DSH_01")
	public String dashboard(Model model) {
		Integer currentYear=0;
		Integer previousMonth=0;
		String month="";
		Date date=new Date();
		if(date.getMonth()==0) {
			currentYear=date.getYear()+1899;	
			previousMonth=12;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			String startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			String endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("first", stkInJpa.selectForDashboard(startDate, endDate));
			double p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pf", Math.round(p));
			model.addAttribute("fmonth", month);
			previousMonth=previousMonth-1;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("second", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("ps", Math.round(p));
			model.addAttribute("smonth", month);
			previousMonth=previousMonth-2;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("third", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pt", Math.round(p));
			model.addAttribute("tmonth", month);
			previousMonth=previousMonth-3;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("fourth", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pfo", Math.round(p));
			model.addAttribute("fomonth", month);
			previousMonth=previousMonth-4;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("fifth", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pfi", Math.round(p));
			model.addAttribute("fimonth", month);
			previousMonth=previousMonth-5;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("sixth", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("psi", Math.round(p));
			model.addAttribute("simonth", month);
			previousMonth=previousMonth-6;
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("seventh", stkInJpa.selectForDashboard(startDate, endDate));
			p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pse", Math.round(p));
			model.addAttribute("semonth", month);
		}else {
			currentYear=date.getYear()+1900;	
			previousMonth=date.getMonth();
			switch (previousMonth) {
			case 1:
				month="JAN";
				break;
			case 2:
				month="FEB";
				break;
			case 3:
				month="MAR";
				break;
			case 4:
				month="APR";
				break;
			case 5:
				month="MAY";
				break;
			case 6:
				month="JUN";
				break;
			case 7:
				month="JUL";
				break;
			case 8:
				month="AUG";
				break;
			case 9:
				month="SEP";
				break;
			case 10:
				month="OCT";
				break;
			case 11:
				month="NOV";
				break;
			case 12:
				month="DEC";
				break;
			}
			String startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
			String endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
			model.addAttribute("first", stkInJpa.selectForDashboard(startDate, endDate));
			double p=0;
			if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
				 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
			}
			model.addAttribute("pf", Math.round(p));
			model.addAttribute("fmonth", month);
			
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("second", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("ps", Math.round(p));
				model.addAttribute("smonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("second", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("ps", Math.round(p));
				model.addAttribute("smonth", month);
			}
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("third", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pt", Math.round(p));
				model.addAttribute("tmonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("third", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pt", Math.round(p));
				model.addAttribute("tmonth", month);
			}
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("fourth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pfo", Math.round(p));
				model.addAttribute("fomonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("fourth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pfo", Math.round(p));
				model.addAttribute("fomonth", month);
			}
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("fifth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pfi", Math.round(p));
				model.addAttribute("fimonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("fifth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pfi", Math.round(p));
				model.addAttribute("fimonth", month);
			}
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("sixth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("psi", Math.round(p));
				model.addAttribute("simonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("sixth", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("psi", Math.round(p));
				model.addAttribute("simonth", month);
			}
			previousMonth=previousMonth-1;
			if(previousMonth==0) {
				previousMonth=12;
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				currentYear=currentYear-1;
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("seventh", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				model.addAttribute("pse", Math.round(p));
				model.addAttribute("semonth", month);
			}else {
				switch (previousMonth) {
				case 1:
					month="JAN";
					break;
				case 2:
					month="FEB";
					break;
				case 3:
					month="MAR";
					break;
				case 4:
					month="APR";
					break;
				case 5:
					month="MAY";
					break;
				case 6:
					month="JUN";
					break;
				case 7:
					month="JUL";
					break;
				case 8:
					month="AUG";
					break;
				case 9:
					month="SEP";
					break;
				case 10:
					month="OCT";
					break;
				case 11:
					month="NOV";
					break;
				case 12:
					month="DEC";
					break;
				}
				startDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"01";
				endDate=currentYear+"-"+String.format("%02d", previousMonth)+"-"+"31";
				model.addAttribute("seventh", stkInJpa.selectForDashboard(startDate, endDate));
				p=0;
				if(stkInJpa.selectForDashboard(startDate, endDate)!=null) {
					 p=100*(Double.valueOf(stkInJpa.selectForDashboard(startDate, endDate))/1000000);
				}
				
				model.addAttribute("pse", Math.round(p));
				model.addAttribute("semonth", month);
			}
		}
	    return "Dashboard/IVCS_DSH_01";
	}
}
