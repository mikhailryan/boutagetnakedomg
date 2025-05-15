/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class OrderReceiptGenerator {

    public static String generateReceipt(int orderId, String userName, List<Map<String, Object>> items, double total) {
        Document document = new Document();
        try {
            String folderPath = "OrderSlips/"; 
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();  // create the folder if it doesn't exist
            }

            String fileName = folderPath + "OrderReceipt_" + orderId + ".pdf";  
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Order Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Order ID: " + orderId, normalFont));
            document.add(new Paragraph("Customer: " + userName, normalFont));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now(), normalFont));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{3, 1, 1, 1});

            table.addCell(new PdfPCell(new Phrase("Product", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Qty", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Price", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Subtotal", headerFont)));

            for (Map<String, Object> item : items) {
                table.addCell(new Phrase(item.get("name").toString(), normalFont));
                table.addCell(new Phrase(item.get("qty").toString(), normalFont));
                table.addCell(new Phrase(String.format("₱%.2f", item.get("price")), normalFont));
                table.addCell(new Phrase(String.format("₱%.2f", item.get("subtotal")), normalFont));
            }

            PdfPCell emptyCell = new PdfPCell(new Phrase(""));
            emptyCell.setColspan(3);
            emptyCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell totalCell = new PdfPCell(new Phrase("Total: ₱" + String.format("%.2f", total), headerFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(emptyCell);
            table.addCell(totalCell);

            document.add(table);

            Paragraph thanks = new Paragraph("Thank you for your order!", normalFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            document.close();
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
