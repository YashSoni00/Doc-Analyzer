package com.example.docanalyzer.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OcrService {
    private final Tesseract tesseract;

    public OcrService() {
        // Initialize Tesseract
        tesseract = new Tesseract();
        // Set the path to your Tesseract data directory
        tesseract.setDatapath(System.getenv("TESSERACT_ENDPOINT"));
        // Set language
        tesseract.setLanguage("eng");
    }

    public String extractTextFromPdf(MultipartFile file) throws IOException, TesseractException {
        // Create temporary file
        Path tempFile = Files.createTempFile("temp", ".pdf");
        file.transferTo(tempFile.toFile());

        StringBuilder extractedText = new StringBuilder();

        try (PDDocument document = PDDocument.load(tempFile.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Convert each page to image and perform OCR
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI for better quality

                // Create temporary image file
                Path tempImagePath = Files.createTempFile("page_" + page, ".png");
                ImageIO.write(image, "png", tempImagePath.toFile());

                // Perform OCR on the image
                String pageText = tesseract.doOCR(tempImagePath.toFile());
                extractedText.append(pageText).append("\n");

                // Clean up temporary image file
                Files.delete(tempImagePath);
            }
        } finally {
            // Clean up temporary PDF file
            Files.delete(tempFile);
        }

        return extractedText.toString();
    }
}