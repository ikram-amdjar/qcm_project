package com.example.demo.controller;

import com.example.demo.model.Score;
import com.example.demo.repository.ScoreRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/export")
public class ExportController {

    @Autowired
    private ScoreRepository scoreRepo;

    // --- EXPORT EXCEL ---
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=resultats_qcm.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Scores");
        List<Score> scores = scoreRepo.findAll();

        // En-tête
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Étudiant");
        header.createCell(1).setCellValue("Matière");
        header.createCell(2).setCellValue("Note / 20");

        // Données
        int rowIdx = 1;
        for (Score s : scores) {
            Row row = sheet.createRow(rowIdx++);
            // Sécurité : vérification que les objets liés ne sont pas nuls
            String nomEtudiant = (s.getEtudiant() != null) ? s.getEtudiant().getNom() : "Inconnu";
            String nomMatiere = (s.getMatiere() != null) ? s.getMatiere().getNom() : "N/A";

            row.createCell(0).setCellValue(nomEtudiant);
            row.createCell(1).setCellValue(nomMatiere);
            row.createCell(2).setCellValue(s.getScore());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // --- EXPORT PDF ---
    @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=rapport_scores.pdf");

        // Utilisation des noms complets pour éviter les conflits d'import
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Définition de la police avec le chemin complet
        com.itextpdf.text.Font fontBold = com.itextpdf.text.FontFactory.getFont(
                com.itextpdf.text.FontFactory.HELVETICA_BOLD, 18);

        document.add(new com.itextpdf.text.Paragraph("Rapport Officiel des Scores - QCM-NET", fontBold));
        document.add(new com.itextpdf.text.Paragraph(" "));

        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(3);
        table.addCell("Étudiant");
        table.addCell("Matière");
        table.addCell("Note / 20");

        List<Score> scores = scoreRepo.findAll();
        for (Score s : scores) {
            String nomEtudiant = (s.getEtudiant() != null) ? s.getEtudiant().getNom() : "Inconnu";
            String nomMatiere = (s.getMatiere() != null) ? s.getMatiere().getNom() : "N/A";

            table.addCell(nomEtudiant);
            table.addCell(nomMatiere);
            table.addCell(String.valueOf(s.getScore()));
        }

        document.add(table);
        document.close();
    }
}