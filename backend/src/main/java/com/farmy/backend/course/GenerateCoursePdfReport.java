package com.farmy.backend.course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;

import java.io.IOException;

import com.farmy.backend.course.dto.CourseDTO;
import com.farmy.backend.course.dto.LessonDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class GenerateCoursePdfReport {

    public void generatePdf(HttpServletResponse response, CourseDTO course) throws DocumentException, IOException {

        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                "X-Requested-With,Origin,Content-Type, Accept, Authorization,Access-Control-Allow-Origin,*");

        try {
            Document coursePDFDoc = new Document(PageSize.A4);
            String fileType = "inline;  filename=" + this.nomeDoPdf(course);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, fileType);
            PdfWriter pdfWriter = PdfWriter.getInstance(coursePDFDoc, response.getOutputStream());

            coursePDFDoc.open();

            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTitle.setSize(30);
            fontTitle.setStyle(Font.BOLD);
            fontTitle.setColor(CMYKColor.RED);
            Paragraph paragraph = new Paragraph("Course Report", fontTitle);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);

            coursePDFDoc.add(paragraph);

            Font fontCourse = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontCourse.setStyle(Font.BOLD);
            fontCourse.setColor(CMYKColor.BLUE);
            Paragraph graparaphCourse = new Paragraph("Course: " + course.name(), fontCourse);
            graparaphCourse.setFont(fontCourse);
            graparaphCourse.setAlignment(Paragraph.ALIGN_LEFT);

            coursePDFDoc.add(graparaphCourse);

            Font fontCategory = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontCategory.setStyle(Font.BOLD);
            fontCategory.setColor(CMYKColor.BLUE);
            Paragraph graparaphCategory = new Paragraph("Category: " + course.category());
            graparaphCategory.setFont(fontCategory);
            graparaphCategory.setAlignment(Paragraph.ALIGN_LEFT);

            coursePDFDoc.add(graparaphCategory);

            PdfPTable table = new PdfPTable(2);

            table.setWidthPercentage(100f);
            table.setWidths(new int[] { 3, 3 });
            table.setSpacingBefore(25);

            PdfPCell cell = new PdfPCell();

            cell.setBackgroundColor(CMYKColor.MAGENTA);
            cell.setPadding(5);

            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setStyle(Font.BOLD);
            font.setColor(CMYKColor.WHITE);

            cell.setPhrase(new Phrase("Lesson Name", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Youtube Url", font));
            table.addCell(cell);

            for (LessonDTO lesson : course.lessons()) {
                table.addCell(lesson.name());
                table.addCell("https://youtu.be/" + lesson.youtubeUrl());
            }

            coursePDFDoc.add(table);

            // Metadados
            coursePDFDoc.addAuthor("Farmy Silva");
            coursePDFDoc.addCreator("Spring Boot 2 + Angular 16 at 2023");
            coursePDFDoc.addTitle("Course Report - Spring Boot 3 and Angular 16");

            coursePDFDoc.close();

            pdfWriter.flush();
            pdfWriter.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String nomeDoPdf(CourseDTO course) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("ddMMuuuu");
        String dataFormatada = formatterData.format(agora);
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HHmmss");
        String horaFormatada = formatterHora.format(agora);
        String dataEHora = dataFormatada + "_" + horaFormatada;

        String nome = course.name() + " " + "PDF Report on " + dataEHora + ".pdf";

        return nome;
    }

}