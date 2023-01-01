package com.inventorycontrolsystem.IVCS.view;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class TransactionPDFExporter {

	 //For Bottom Table
    int items = 0;
	double tQty = 0;
	double tAmt = 0;
	//items = listReceives.size();
	
private List<StockIn> listReceives;

    public TransactionPDFExporter(List<StockIn> listReceives) {
        this.listReceives = listReceives;
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
         
        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);       
    
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell); 
        //table.completeRow();
        
        //table.addCell("7");
    }
     
    private void writeTableData(PdfPTable table) {
    	items=listReceives.size();
        for (StockIn receive : listReceives) {
            table.addCell(String.valueOf(receive.getStock().getStockCode()));
            table.addCell(receive.getStock().getName());
            table.addCell(receive.getStock().getUnit().getName());
            table.addCell(String.valueOf(receive.getQty()));
            table.addCell(String.valueOf(receive.getPrice()));
            table.addCell(String.valueOf(receive.getSubTotalAmount()));
            tQty = receive.getQty() + tQty;
			tAmt = receive.getSubTotalAmount() + tAmt;
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
        PdfPCell pdfPCellNo = new PdfPCell(new Paragraph("Receive No.:  " + listReceives.get(0).getCode()));
        PdfPCell pdfPCellWCode = new PdfPCell(new Paragraph("Warehouse Code:  " + listReceives.get(0).getWarehouse().getWarehouseCode()));
        PdfPCell pdfPCellWName = new PdfPCell(new Paragraph("Warehouse Name:  " +listReceives.get(0).getWarehouse().getName()));
        PdfPCell pdfPCellSCode = new PdfPCell(new Paragraph("Supplier Code:  " +listReceives.get(0).getSupplier().getSupplierCode()));
        
        PdfPCell pdfPCellSName = new PdfPCell(new Paragraph("Supplier Name:  " +listReceives.get(0).getSupplier().getName()));
        PdfPCell pdfPCellDate = new PdfPCell(new Paragraph("Date:  " +listReceives.get(0).getDate()));
        PdfPCell pdfPCellCuy = new PdfPCell(new Paragraph("Currency:  " +listReceives.get(0).getCurrency().getName()));
        PdfPCell pdfPCellExcRate = new PdfPCell(new Paragraph("Exchange:  " +listReceives.get(0).getExchangeRate()));
        
        PdfPCell pdfPCell9 = new PdfPCell(new Paragraph(" "));
        PdfPCell pdfPCell10 = new PdfPCell(new Paragraph(" "));
        
        //Invisible Border of Table
        pdfPCellNo.setBorder(Rectangle.NO_BORDER);
        pdfPCellWCode.setBorder(Rectangle.NO_BORDER);
        pdfPCellWName.setBorder(Rectangle.NO_BORDER);
        pdfPCellSCode.setBorder(Rectangle.NO_BORDER); 
        pdfPCellSName.setBorder(Rectangle.NO_BORDER);
        
        pdfPCellDate.setBorder(Rectangle.NO_BORDER);
        pdfPCellCuy.setBorder(Rectangle.NO_BORDER);
        pdfPCellExcRate.setBorder(Rectangle.NO_BORDER);
        
        pdfPCell9.setBorder(Rectangle.NO_BORDER);
        pdfPCell10.setBorder(Rectangle.NO_BORDER);
        
        //Add cells to table
        pdfPTable.addCell(pdfPCellNo);
        pdfPTable.addCell(pdfPCellDate);
        pdfPTable.addCell(pdfPCellWCode);
        pdfPTable.addCell(pdfPCellCuy); 
        pdfPTable.addCell(pdfPCellWName);
        
        pdfPTable.addCell(pdfPCellExcRate);
        pdfPTable.addCell(pdfPCellSCode);
        //Blank Cell
        pdfPTable.addCell(pdfPCell9);
        pdfPTable.addCell(pdfPCellSName);
        //Blank Cell
        pdfPTable.addCell(pdfPCell10);
 
        //Adding Header
        Paragraph p = new Paragraph("Inventory Receive Transaction", font);
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
        Paragraph p1 = new Paragraph("Remark: " +listReceives.get(0).getRemark());
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p1);
        Paragraph p2 = new Paragraph("Items: " + listReceives.size());
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
