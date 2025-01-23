package com.example.docanalyzer.parser;

import com.example.docanalyzer.service.OcrService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class PdfParser {
    public static String parse(MultipartFile file) {
        OcrService ocrService = new OcrService();
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String text = pdfTextStripper.getText(document);
                if (text == null || text.isBlank() || text.length() < 20) {
                    System.out.println("PDF is empty and cannot be processed directly.");
                    text = ocrService.extractTextFromPdf(file);
                }
                return text;
            } else {
                return "PDF is encrypted and cannot be processed.";
            }
        } catch (IOException e) {
            System.out.println("Error in extractTextPdf (TikaService): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
