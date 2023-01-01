package com.inventorycontrolsystem.IVCS.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.inventorycontrolsystem.IVCS.model.Damage;


public class DamageExcelExporter {

	//Export Inventory Receive
		private XSSFWorkbook workbook;
		private XSSFSheet sheet;
		private List<Damage> damageList;

		public DamageExcelExporter(List<Damage> damageList) {
			this.damageList = damageList;
			workbook = new XSSFWorkbook();
		}

                //Inventory Receive
		private void writeHeaderLine() {
			sheet = workbook.createSheet(" Damage");
			
			//Table Header Starting Line
			Row row = sheet.createRow(8);

			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setFontHeight(16);
			style.setFont(font);
			
			BorderStyle border = BorderStyle.THICK;
			style.setBorderBottom(border);
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			createCell(row, 0, "Stock Code", style);
			createCell(row, 1, "Stock Name", style);
			createCell(row, 2, "Unit", style);
			createCell(row, 3, "Current Quantity", style);
			createCell(row, 4, "Damage Quantity", style);
			createCell(row, 5, "Total", style);

		}

		private void createCell(Row row, int columnCount, Object value, CellStyle style) {
			sheet.autoSizeColumn(columnCount);
			Cell cell = row.createCell(columnCount);
			if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else {
				cell.setCellValue((String) value);
			}
			cell.setCellStyle(style);
		}

		private void writeDataLines() {
			//Start of Table(Related with Table Header) Always Keep +1 of TableHeader Value
			int rowCount = 9;

			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setFontHeight(14);
			style.setFont(font);
			BorderStyle border = BorderStyle.THICK;
			style.setBorderRight(border);
			style.setBorderLeft(border);
			style.setBorderBottom(border);

			CellStyle style1 = workbook.createCellStyle();
			XSSFFont font1 = workbook.createFont();
			font1.setFontHeight(14);
			style1.setFont(font1);
		
			
			CellStyle styleH = workbook.createCellStyle();
			XSSFFont fontH = workbook.createFont();
			fontH.setBold(true);
			fontH.setFontHeight(18);
			styleH.setFont(fontH);
			styleH.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			styleH.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			BorderStyle borderH = BorderStyle.THICK;
			styleH.setBorderBottom(borderH);

			// Top Data Rows
			createCell(sheet.createRow(0), 0, "Inventory Damage Transaction Details" , styleH);
			
			//Upper Part(Table is Created)
			//This data needs to be written (Object[])
	        Map<String, Object[]> data = new TreeMap<String, Object[]>();

			data.put("1", new Object[] { "Damage No.:  " + damageList.get(0).getDamageCode(),
					"Date" + damageList.get(0).getDate() });
			data.put("2", new Object[] { "Warehouse Code:  " + damageList.get(0).getWarehouse().getWarehouseCode()});
			data.put("3", new Object[] { "Warehouse Name:  " + damageList.get(0).getWarehouse().getName()});
			 
	          
	        //Iterate over data and write to sheet
	        Set<String> keyset = data.keySet();
	        int rownum = 1;
	        for (String key : keyset)
	        {
	            Row row = sheet.createRow(rownum++);
	            Object [] objArr = data.get(key);
	            int cellnum = 0;
	            for (Object obj : objArr)
	            {
	               Cell cell = row.createCell(cellnum++);
	               cell.setCellStyle(style1);
	               if(obj instanceof String)
	                    cell.setCellValue((String)obj);
	                else if(obj instanceof Double)
	                    cell.setCellValue((Double)obj);
	            }
	        }
			//Upper Table Ends
	        	
			// For Bottom Rows
			int items = 0;
			int tQty = 0;
			double tAmt = 0;
			items = damageList.size();

			// Table Rows(Table Header is Created by WriteHeaderLine())
			for (Damage damage : damageList) {
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;

				createCell(row, columnCount++, damage.getStock().getStockCode(), style);
				createCell(row, columnCount++, damage.getStock().getName(), style);
				createCell(row, columnCount++, damage.getStock().getUnit().getName(), style);
				
				  createCell(row, columnCount++, damage.getCurrentQty(), style);
				  createCell(row, columnCount++, damage.getDamageQty(), style);
				  createCell(row, columnCount++, damage.getSubTotalAmount(), style); 
				  tQty =tQty+damage.getDamageQty();
				  tAmt = tAmt+damage.getSubTotalAmount();
				 			}

			// Total & Remark
			createCell(sheet.createRow(16), 0, "Remark:  " + damageList.get(0).getRemark() , style1);
			createCell(sheet.createRow(17), 0, "Number of Items:  " + items, style1);
			createCell(sheet.createRow(18), 0, "Total Quantity:  " + tQty, style1);
			createCell(sheet.createRow(19), 0, "Total Amount:  " + tAmt, style1);
		}

		public void export(HttpServletResponse response) throws IOException {
			writeHeaderLine();
			writeDataLines();

			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();

			outputStream.close();

		}
		//Export Inventory Receive Ends 
}
