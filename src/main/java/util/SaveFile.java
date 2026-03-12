package util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
        String testCourse = "Test course";
        saveReportAsPdf(testCourse, testPoints, testAverage, testName);
    }

    public static void saveReportAsPdf(String courseName,
                                       Map<String, Integer> assignmentPoints,
                                       double weightedAverage,
                                       String studentName) {

        FileChooser fc = new FileChooser();
        fc.setTitle("Save Report");
        fc.setInitialFileName(studentName + " " + courseName + ".pdf");
        File fileToSave = fc.showSaveDialog(new Stage());

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28);
            Paragraph titleParagraph = new Paragraph(courseName + " Report Card", titleFont);
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
            PdfPTable table = new PdfPTable(2);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            PdfPCell assignmentHeader = new PdfPCell(new Paragraph("Assignment", headerFont));
            assignmentHeader.setPadding(8);
            table.addCell(assignmentHeader);

            PdfPCell pointsHeader = new PdfPCell(new Paragraph("Points", headerFont));
            pointsHeader.setPadding(8);
            table.addCell(pointsHeader);

            // Table data, assignment points
            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            for (Map.Entry<String, Integer> entry : assignmentPoints.entrySet()) {
                PdfPCell titleCell = new PdfPCell(new Paragraph(entry.getKey(), tableFont));
                titleCell.setPadding(8);
                table.addCell(titleCell);

                String displayPoints = entry.getValue() != null ? String.valueOf(entry.getValue()) : "Ungraded";
                PdfPCell pointsCell = new PdfPCell(new Paragraph(displayPoints, tableFont));
                pointsCell.setPadding(8);
                table.addCell(pointsCell);
            }
            document.add(table);

            // Average grade
            Font averageFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph averageParagraph = new Paragraph("Final Grade (weighted average): " + String.format("%.2f", weightedAverage), averageFont);
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
