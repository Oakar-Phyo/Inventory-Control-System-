package com.inventorycontrolsystem.IVCS.view;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class WarehouseTransferPDFExporter {
	// For Bottom Table
	int items = 0;
	double tQty = 0;
	double tAmt = 0;
	
	private List<WarehouseTransfer> listTransfers;
	
	 public  WarehouseTransferPDFExporter(List<WarehouseTransfer> listTransfers) {
	        this.listTransfers = listTransfers;
	    }
	 
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("Stock Code", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Stock Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Unit", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Current Quantity", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Transfer Quantity", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Stock Price", font));
	        table.addCell(cell);       
	    
	        cell.setPhrase(new Phrase("Total", font));
	        table.addCell(cell); 
	    }
	 
	 private void writeTableData(PdfPTable table) {
	    	items=listTransfers.size();
	        for (WarehouseTransfer receivereturn : listTransfers) {
	            table.addCell(receivereturn.getStock().getStockCode());
	            table.addCell(receivereturn.getStock().getName());
	            table.addCell(receivereturn.getStock().getUnit().getName());
	            table.addCell(String.valueOf(receivereturn.getCurrentQty()));
	            table.addCell(String.valueOf(receivereturn.getTransferQty()));
	            table.addCell(String.valueOf(receivereturn.getStock().getSellprice()));
	            table.addCell(String.valueOf(receivereturn.getTotal()));
	            tQty = receivereturn.getTransferQty()+ tQty;
				tAmt = receivereturn.getTotal()+ tAmt;
	        }
	    }
	 
	 public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	        
				
	        
	       //Hidden Table Starts Here
	      //Create Table object, Here 2 specify the no. of columns
	        PdfPTable pdfPTable = new PdfPTable(2);
	        pdfPTable.setWidthPercentage(100);
	        pdfPTable.setSpacingBefore(10);
	        //pdfPTable.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	        
	        //Create cells
	        PdfPCell pdfPCellNo = new PdfPCell(new Paragraph("Transfer No:  " + listTransfers.get(0).getTransferNo()));
	        PdfPCell pdfPCellWCode = new PdfPCell(new Paragraph("From Warehouse Code:  " +listTransfers.get(0).getFromWarehouse().getWarehouseCode()));
	        PdfPCell pdfPCellWName = new PdfPCell(new Paragraph("From Warehouse Name:  "+listTransfers.get(0).getFromWarehouse().getName()));
	        PdfPCell pdfPCellSCode = new PdfPCell(new Paragraph( "Transfer Date: "+listTransfers.get(0).getTransferDate()));
	        
	        PdfPCell pdfPCellSName = new PdfPCell(new Paragraph("To Warehouse Code:  "+listTransfers.get(0).getToWarehouse().getWarehouseCode()));
	        PdfPCell pdfPCellDate = new PdfPCell(new Paragraph("To Warehouse Name:  "+listTransfers.get(0).getToWarehouse().getName()));
	        
	        PdfPCell pdfPCell9 = new PdfPCell(new Paragraph(" "));
	        PdfPCell pdfPCell10 = new PdfPCell(new Paragraph(" "));
	        
	        //Invisible Border of Table
	        pdfPCellNo.setBorder(Rectangle.NO_BORDER);
	        pdfPCellWCode.setBorder(Rectangle.NO_BORDER);
	        pdfPCellWName.setBorder(Rectangle.NO_BORDER);
	        pdfPCellSCode.setBorder(Rectangle.NO_BORDER); 
	        pdfPCellSName.setBorder(Rectangle.NO_BORDER);
	        
	        pdfPCellDate.setBorder(Rectangle.NO_BORDER);
	      
	        
	        pdfPCell9.setBorder(Rectangle.NO_BORDER);
	        pdfPCell10.setBorder(Rectangle.NO_BORDER);
	        
	        //Add cells to table
	        pdfPTable.addCell(pdfPCellNo);
	        pdfPTable.addCell(pdfPCellDate);
	        pdfPTable.addCell(pdfPCellWCode);
	        pdfPTable.addCell(pdfPCellWName);
	        pdfPTable.addCell(pdfPCellSCode);
	        //Blank Cell
//	        pdfPTable.addCell(pdfPCell9);
	        pdfPTable.addCell(pdfPCellSName);
	        //Blank Cell
//	        pdfPTable.addCell(pdfPCell10);
	 
	        //Adding Header
	        Paragraph p = new Paragraph("Warehouse Transfer View", font);
	        p.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p);
	        //Add content to the document using Table objects.
	        document.add(pdfPTable);
	       //Upper Part of the Page Ends(Hidden Table)
	        
	        //Table Size of Stock Table()
	        PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {2.0f, 3.0f, 2.0f, 1.5f, 2.0f,2.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	        
	       
	        
	        //Hiddent Bottom Table
	        Paragraph p1 = new Paragraph("Remark: " +listTransfers.get(0).getRemark());
	        p1.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p1);
	        Paragraph p2 = new Paragraph("Items: " + listTransfers.size());
	        p2.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p2);
	        Paragraph p3 = new Paragraph("Total Quantity: " +tQty);
	        p3.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p3);
	        Paragraph p4 = new Paragraph("Total Amount: " +tAmt);
	        p4.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p4);
	         
	        document.close();
	         
	    }
}
