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

import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;

public class WarehouseTransferExcelExporter {
	//Export Warehouse Transfer
			private XSSFWorkbook workbook;
			private XSSFSheet sheet;
			private List<WarehouseTransfer> listTransfers;
			
			public WarehouseTransferExcelExporter(List<WarehouseTransfer> listTransfers) {
				this.listTransfers = listTransfers;
				workbook = new XSSFWorkbook();
			}
			
	        //Warehouse Transfer
			private void writeHeaderLine() {
				sheet = workbook.createSheet("Warehouse Transfer");
				
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
				createCell(row, 4, "Transfer Quantity", style);
				createCell(row, 5, "Stock Price", style);
				createCell(row, 6, "Total", style);

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
				createCell(sheet.createRow(0), 0, "Warehouse Transfer Details" , styleH);
				
				//Upper Part(Table is Created)
				//This data needs to be written (Object[])
		        Map<String, Object[]> data = new TreeMap<String, Object[]>();
		        data.put("1", new Object[] {"Transfer No:  "+ listTransfers.get(0).getTransferNo(), "Transfer Date"+listTransfers.get(0).getTransferDate()});
		        data.put("2", new Object[] {"From Warehouse Code:  "+listTransfers.get(0).getFromWarehouse().getWarehouseCode(), "To Warehouse Code:  "+listTransfers.get(0).getToWarehouse().getWarehouseCode()});
		        data.put("3", new Object[] {"From Warehouse Name:  "+listTransfers.get(0).getFromWarehouse().getName(), "To Warehouse Name:  "+listTransfers.get(0).getToWarehouse().getName()});
		          
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
				items = listTransfers.size();

				// Table Rows(Table Header is Created by WriteHeaderLine())
				for (WarehouseTransfer receive : listTransfers) {
					Row row = sheet.createRow(rowCount++);
					int columnCount = 0;

					createCell(row, columnCount++, receive.getStock().getStockCode(), style);
					createCell(row, columnCount++, receive.getStock().getName(), style);
					createCell(row, columnCount++, receive.getStock().getUnit().getName(), style);
					createCell(row, columnCount++, receive.getCurrentQty(), style);
					createCell(row, columnCount++, receive.getTransferQty(), style);
					createCell(row, columnCount++, receive.getStock().getSellprice(), style);
					createCell(row, columnCount++, receive.getTotal(), style);
					tQty = receive.getTransferQty()+ tQty;
					tAmt = receive.getTotal() + tAmt;
				}

				// Total & Remark
				createCell(sheet.createRow(16), 0, "Remark:  " + listTransfers.get(0).getRemark() , style1);
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
}
