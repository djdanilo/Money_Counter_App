package GUI;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Date;

public class PdfExport {

    private static String FILE = "C:\\Users\\danilo\\Desktop\\MasterTeam_Brojacica\\Sample.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static String[] denomination = {"RSD", "0", "4", "5", "4", "3", "0", "0", "0", "0", "1330"};
    private static String[] serialOcr = {"1$", "AA123456", "2$", "AA654321", "100$", "BB123456"};
    private static String[] serialImage = {"00001110", "00111100", "1000111"};

    public static void createPdfExport(String Id, String user, String client, String file) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addMetaData(document);
            createPdf(document, Id,user, new Date(), client);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMetaData(Document document) {
        document.addTitle("Izveštaj o transakciji");
        document.addSubject("Izveštaj o transakciji");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Master Team");
        document.addCreator("Master Team");
    }

    public static void createPdf(Document document, String id, String user, Date date, String client)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Izveštaj o transakciji", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Izveštaj generisao: " + user + ", " + date, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 2);

        preface.add(new Paragraph("Id transakcije: " + id, smallBold));
        preface.add(new Paragraph("Klijent: " + client, smallBold));

        addEmptyLine(preface, 1);

        preface.add(new Paragraph(
                "Apoenska struktura transakcije",
                smallBold));

        addEmptyLine(preface, 2);

        document.add(preface);
        document.add(createTableDenom(denomination));

        document.add(new Paragraph("\n", smallBold));

        document.add(new Paragraph("Serijski brojevi ", smallBold));
        document.add(new Paragraph("\n", smallBold));
        document.add(new Paragraph("\n", smallBold));

        document.add(createTableSerial(serialOcr, serialImage));
        // Start a new page
        //document.newPage();
    }


    private static PdfPTable createTableDenom(String[] denomination)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Apoen - " + denomination[0]));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Broj komada"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Vrednost"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(denomination.length - 1);

        if (denomination[0].equals("RSD")) {
            table.addCell("10");
            table.addCell(denomination[1]);
            table.addCell(String.valueOf(10*Integer.parseInt(denomination[1])));
            table.addCell("20");
            table.addCell(denomination[2]);
            table.addCell(String.valueOf(20*Integer.parseInt(denomination[2])));
            table.addCell("50");
            table.addCell(denomination[3]);
            table.addCell(String.valueOf(50*Integer.parseInt(denomination[3])));
            table.addCell("100");
            table.addCell(denomination[4]);
            table.addCell(String.valueOf(100*Integer.parseInt(denomination[4])));
            table.addCell("200");
            table.addCell(denomination[5]);
            table.addCell(String.valueOf(200*Integer.parseInt(denomination[5])));
            table.addCell("500");
            table.addCell(denomination[6]);
            table.addCell(String.valueOf(500*Integer.parseInt(denomination[6])));
            table.addCell("1000");
            table.addCell(denomination[7]);
            table.addCell(String.valueOf(1000*Integer.parseInt(denomination[7])));
            table.addCell("2000");
            table.addCell(denomination[8]);
            table.addCell(String.valueOf(2000*Integer.parseInt(denomination[8])));
            table.addCell("5000");
            table.addCell(denomination[9]);
            table.addCell(String.valueOf(5000*Integer.parseInt(denomination[9])));
            table.addCell("Ukupno:");
            table.addCell("16");
            table.addCell(denomination[10]);
        }

        return table;

    }

    private static PdfPTable createTableSerial(String[] serialOcr, String[] serialImage)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Apoen"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Serijski broj"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Slika serijskog broja"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(serialImage.length - 1);

        try {
            int i = 0;
            int j = 0;
            while (j < serialImage.length){
                table.addCell(serialOcr[i]);
                table.addCell(serialOcr[i+1]);
                table.addCell(serialImage[j]);
                i+=2;
                j++;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }


        return table;

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
