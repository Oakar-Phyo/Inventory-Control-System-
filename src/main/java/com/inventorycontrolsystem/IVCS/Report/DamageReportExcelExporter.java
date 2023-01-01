package com.inventorycontrolsystem.IVCS.Report;

import java.io.IOException;
import java.util.List;

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

public class DamageReportExcelExporter {

	//Export Warehouse Transfer
		private XSSFWorkbook workbook;
		private XSSFSheet sheet;
		private List<Damage> listTransfers;
		
		public DamageReportExcelExporter(List<Damage> list) {
			this.listTransfers = list;
			workbook = new XSSFWorkbook();
		}
		
	    //Warehouse Transfer Report
				private void writeHeaderLine() {
					sheet = workbook.createSheet("Damage Report");
					
					//Table Header Starting Line
					Row row = sheet.createRow(2);

					CellStyle style = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();
					font.setBold(true);
					font.setFontHeight(14);
					style.setFont(font);
					
					BorderStyle border = BorderStyle.THICK;
					style.setBorderBottom(border);
					style.setBorderTop(border);
					style.setBorderLeft(border);
					style.setBorderRight(border);
					style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

					createCell(row, 0, "No", style);
					createCell(row, 1, "Damage No", style);
					createCell(row, 2, "Stock Code", style);
					createCell(row, 3, "Stock Name", style);
					createCell(row, 4, " Warehouse Name", style);
					
					createCell(row, 5, " Current Quantity", style);
					createCell(row, 6, " Damage QuantityTotal", style);
					createCell(row, 7, "Price", style);
					createCell(row, 8, "Amount", style);
					createCell(row,  9, "Date", style);
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
					int rowCount = 3;

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
					fontH.setFontHeight(14);
					styleH.setFont(fontH);
					styleH.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
					styleH.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					BorderStyle borderH = BorderStyle.THICK;
					styleH.setBorderBottom(borderH);

					// Top Data Rows
					createCell(sheet.createRow(0), 0, "Damage Report" , styleH);
			        	
					// For Bottom Rows
					int totalNo = 1;
					// Table Rows(Table Header is Created by WriteHeaderLine())
					for (Damage receive : listTransfers) {
						Row row = sheet.createRow(rowCount++);
						int columnCount = 0;
						
						createCell(row, columnCount++, totalNo, style);
						createCell(row, columnCount++, receive.getDamageCode(), style);
						createCell(row, columnCount++, receive.getStock().getStockCode(), style);
						createCell(row, columnCount++, receive.getStock().getName(), style);
						createCell(row, columnCount++, receive.getWarehouse().getName(), style);
						createCell(row, columnCount++, receive.getCurrentQty(), style);
						createCell(row, columnCount++, receive.getDamageQty(), style);
						createCell(row, columnCount++, receive.getStock().getSellprice(), style);
						createCell(row, columnCount++, receive.getSubTotalAmount(), style);
						createCell(row, columnCount++, receive.getDate(), style);
						
						totalNo++;
					}
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
