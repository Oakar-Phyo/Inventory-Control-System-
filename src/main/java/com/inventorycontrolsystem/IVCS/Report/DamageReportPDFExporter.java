package com.inventorycontrolsystem.IVCS.Report;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DamageReportPDFExporter {

	private List<Damage> listTransfers;
	
	 public  DamageReportPDFExporter(List<Damage> listTransfers) {
	        this.listTransfers = listTransfers;
	    }
	 
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	        
	        cell.setPhrase(new Phrase("No", font));
	        table.addCell(cell);
	        
	        
	        cell.setPhrase(new Phrase("Damage No", font));
	        table.addCell(cell);
	         
	         
	        cell.setPhrase(new Phrase("Stock Code", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Stock Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Warehouse Name", font));
	        table.addCell(cell);
	   
	         
	        cell.setPhrase(new Phrase("Current Quantity", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Damage Quantity", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Price", font));
	        table.addCell(cell);       
	        
	       
	        
	        cell.setPhrase(new Phrase("Amount", font));
	        table.addCell(cell); 
	        
	        cell.setPhrase(new Phrase("Date", font));
	        table.addCell(cell);  
	    }
	 
	 private void writeTableData(PdfPTable table) {
			int totalNo = 1;
	        for (Damage damage : listTransfers) {
	        	table.addCell(String.valueOf(totalNo));
	        	 table.addCell(damage.getDamageCode());
	            table.addCell(damage.getStock().getStockCode());
	            table.addCell(damage.getStock().getName());
	            table.addCell(damage.getWarehouse().getName());
	            table.addCell(String.valueOf(damage.getCurrentQty()));
	            table.addCell(String.valueOf(damage.getDamageQty()));
	            table.addCell(String.valueOf(damage.getStock().getSellprice()));
	            table.addCell(String.valueOf(damage.getSubTotalAmount()));
	            table.addCell(String.valueOf(damage.getDate()));
	            totalNo++;
	        }
	    }
	 
	 public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);

	        PdfPTable pdfPTable = new PdfPTable(2);
	        pdfPTable.setWidthPercentage(100);
	        pdfPTable.setSpacingBefore(9);
	       
	 
	        //Adding Header
	        Paragraph p = new Paragraph("Damage Report", font);
	        p.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(p);
	        //Add content to the document using Table objects.
	        document.add(pdfPTable);
	       //Upper Part of the Page Ends(Hidden Table)
	        
	        //Table Size of Stock Table()
	        PdfPTable table = new PdfPTable(10);
	        table.setWidthPercentage(100f);
//	        table.setWidths(new float[] {2.0f, 3.0f, 2.0f, 1.5f, 2.0f,2.0f,2.0f,2.0f,2.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	
	         
	        document.close();
	         
	    }
}
