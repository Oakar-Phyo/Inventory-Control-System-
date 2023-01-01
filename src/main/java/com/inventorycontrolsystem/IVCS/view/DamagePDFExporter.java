package com.inventorycontrolsystem.IVCS.view;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;


import com.inventorycontrolsystem.IVCS.model.Damage;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class DamagePDFExporter {

	 //For Bottom Table
    int items = 0;
	int tQty = 0;
	double tAmt = 0;
	//items = listReceives.size();
	
private List<Damage> damageList;

    public DamagePDFExporter(List<Damage> damageList) {
        this.damageList = damageList;
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
         
        cell.setPhrase(new Phrase("Currnet Quantity", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Damage Quantity", font));
        table.addCell(cell);       
    
        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell); 
        //table.completeRow();
        
        //table.addCell("7");
    }
     
    private void writeTableData(PdfPTable table) {
    	items=damageList.size();
        for (Damage damage : damageList) {
            table.addCell(String.valueOf(damage.getStock().getStockCode()));
            table.addCell(damage.getStock().getName());
            table.addCell(damage.getStock().getUnit().getName());
            table.addCell(String.valueOf(damage.getCurrentQty()));
            table.addCell(String.valueOf(damage.getDamageQty()));
            table.addCell(String.valueOf(damage.getSubTotalAmount()));
            tQty =tQty+damage.getDamageQty();
			  tAmt = tAmt+damage.getSubTotalAmount();
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
        PdfPCell pdfPCellNo = new PdfPCell(new Paragraph("Damage No.:  " + damageList.get(0).getDamageCode()));
        PdfPCell pdfPCellWCode = new PdfPCell(new Paragraph("Warehouse Code:  " + damageList.get(0).getWarehouse().getWarehouseCode()));
        PdfPCell pdfPCellWName = new PdfPCell(new Paragraph("Warehouse Name:  " +damageList.get(0).getWarehouse().getName()));
        PdfPCell pdfPCellSDate = new PdfPCell(new Paragraph("Damage Date:  " +damageList.get(0).getDate()));
        
        
        PdfPCell pdfPCell9 = new PdfPCell(new Paragraph(" "));
        PdfPCell pdfPCell10 = new PdfPCell(new Paragraph(" "));
        
        //Invisible Border of Table
        pdfPCellNo.setBorder(Rectangle.NO_BORDER);
        pdfPCellWCode.setBorder(Rectangle.NO_BORDER);
        pdfPCellWName.setBorder(Rectangle.NO_BORDER);
        pdfPCellSDate.setBorder(Rectangle.NO_BORDER); 
       
        
        pdfPCell9.setBorder(Rectangle.NO_BORDER);
        pdfPCell10.setBorder(Rectangle.NO_BORDER);
        
        //Add cells to table
        pdfPTable.addCell(pdfPCellNo);
        pdfPTable.addCell(pdfPCellSDate);
        pdfPTable.addCell(pdfPCellWCode);
        pdfPTable.addCell(pdfPCellWName); 
       
        
		/*
		 * pdfPTable.addCell(pdfPCellExcRate); pdfPTable.addCell(pdfPCellSCode); //Blank
		 * Cell pdfPTable.addCell(pdfPCell9); pdfPTable.addCell(pdfPCellSName); //Blank
		 * Cell pdfPTable.addCell(pdfPCell10);
		 */
        //Adding Header
        Paragraph p = new Paragraph("Inventory Damage Transaction", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p);
        //Add content to the document using Table objects.
        document.add(pdfPTable);
       //Upper Part of the Page Ends(Hidden Table)
        
        //Table Size of Stock Table()
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 3.0f, 2.0f, 1.5f, 2.0f,2.0f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
        
       
        
        //Hiddent Bottom Table
        Paragraph p1 = new Paragraph("Remark: " +damageList.get(0).getRemark());
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p1);
        Paragraph p2 = new Paragraph("Items: " + damageList.size());
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

