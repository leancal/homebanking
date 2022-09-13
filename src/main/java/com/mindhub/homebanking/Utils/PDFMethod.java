package com.mindhub.homebanking.Utils;

import com.itextpdf.text.*;
import com.mindhub.homebanking.Models.Transaction;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public class PDFMethod {

    public PDFMethod() {
    }

    public static void createPDF (Set<Transaction> transactionArray) throws Exception {
        var doc = new Document();
        String route = System.getProperty("user.home");
        PdfWriter.getInstance(doc, new FileOutputStream("Your-transactions.pdf"));
        PdfWriter.getInstance(doc, new FileOutputStream(route + "./Downloads/TransactionInfo.pdf"));
        doc.open();

        var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        try
        {
            Image foto = Image.getInstance("C:\\Users\\leand\\Desktop\\MindHub\\FASE 4 - JAVA\\homebanking\\homebanking_CON_H2\\homebanking\\src\\main\\resources\\static\\web\\assets\\img\\Nuevo proyecto2.png");
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            doc.add(foto);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        var paragraph = new Paragraph( "Your transactions", bold);


        var table = new PdfPTable(4);
        Stream.of("Amount", "Description", "Date", "Type").forEach(table::addCell);

        transactionArray.forEach(transaction -> {
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(transaction.getDescription());
            table.addCell(String.valueOf(transaction.getDate()));
            table.addCell(String.valueOf(transaction.getType()));
        });

        doc.add(paragraph);
        doc.add(table);
        doc.close();
    }
}
