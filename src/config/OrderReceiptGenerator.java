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

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Title
            Paragraph title = new Paragraph("Order Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Info section
            document.add(new Paragraph("Order ID: " + orderId, normalFont));
            document.add(new Paragraph("Customer: " + userName, normalFont));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now(), normalFont));
            document.add(Chunk.NEWLINE);

            // Table with colored header
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{3, 1, 1, 1});

            BaseColor headerBgColor = new BaseColor(30, 144, 255); // Dodger Blue

            // Add header cells with background color
            String[] headers = {"Product", "Qty", "Price", "Subtotal"};
            for (String h : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(h, headerFont));
                headerCell.setBackgroundColor(headerBgColor);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(5);
                table.addCell(headerCell);
            }

            // Add item rows
            for (Map<String, Object> item : items) {
                PdfPCell productCell = new PdfPCell(new Phrase(item.get("name").toString(), normalFont));
                productCell.setPadding(5);
                table.addCell(productCell);

                PdfPCell qtyCell = new PdfPCell(new Phrase(item.get("qty").toString(), normalFont));
                qtyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                qtyCell.setPadding(5);
                table.addCell(qtyCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(String.format("₱%.2f", item.get("price")), normalFont));
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                priceCell.setPadding(5);
                table.addCell(priceCell);

                PdfPCell subtotalCell = new PdfPCell(new Phrase(String.format("₱%.2f", item.get("subtotal")), normalFont));
                subtotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                subtotalCell.setPadding(5);
                table.addCell(subtotalCell);
            }

            // Separator line before total
            PdfPCell separator = new PdfPCell();
            separator.setColspan(4);
            separator.setBorder(Rectangle.TOP);
            separator.setPaddingTop(10f);
            table.addCell(separator);

            // Total row
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total:", headerFont));
            totalLabelCell.setColspan(3);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setBorder(Rectangle.NO_BORDER);
            totalLabelCell.setPadding(5);
            table.addCell(totalLabelCell);

            PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("₱%.2f", total), headerFont));
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setPadding(5);
            totalValueCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(totalValueCell);

            document.add(table);

            // Thank you note
            Paragraph thanks = new Paragraph("Thank you for your order!", normalFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            thanks.setSpacingBefore(20);
            document.add(thanks);

            document.close();
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
