package util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Map;

public class SaveFile {
    public static void main(String[] args) {
        Map<String, Integer> testPoints = Map.of(
                "Homework", 5,
                "Project", 10,
                "Exam", 25
        );
        double testAverage = 4.5;
        String testName = "Matti Meikäläinen";
        saveReportAsPdf(testPoints, testAverage, testName);
    }

    public static void saveReportAsPdf(Map<String, Integer> assignmentPoints, double weightedAverage, String studentName) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 32);
            Paragraph titleParagraph = new Paragraph("Report Card", titleFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleParagraph.setSpacingBefore(20);
            titleParagraph.setSpacingAfter(10);
            document.add(titleParagraph);

            // Name
            Font nameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph nameParagraph = new Paragraph(studentName, nameFont);
            nameParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            nameParagraph.setSpacingBefore(20);
            nameParagraph.setSpacingAfter(40);
            document.add(nameParagraph);

            // Grades as table
            // Table headers
            PdfPTable table =  new PdfPTable(2);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            table.addCell(new Paragraph("Assignment", headerFont));
            table.addCell(new Paragraph("Points", headerFont));

            // Table data, assignment points
            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            for (Map.Entry<String, Integer> entry : assignmentPoints.entrySet()) {
                table.addCell(new Paragraph(entry.getKey(), tableFont));
                table.addCell(new Paragraph(String.valueOf(entry.getValue()), tableFont));
            }
            document.add(table);

            // Average
            Font averageFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph averageParagraph = new Paragraph("Final Grade (weighted average): " + weightedAverage, averageFont);
            averageParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            averageParagraph.setSpacingBefore(20);
            averageParagraph.setSpacingAfter(20);
            document.add(averageParagraph);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
