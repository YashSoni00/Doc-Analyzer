package com.example.docanalyzer.service;

import com.asprise.ocr.Ocr;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OcrService {
    Ocr ocr;

    public OcrService() {
        Ocr.setUp();
        ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_FASTEST);
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
                String pageText = ocr.recognize(
                        new File[] {new File(String.valueOf(tempImagePath))},
                        Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT
                );
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