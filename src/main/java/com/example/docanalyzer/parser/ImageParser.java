package com.example.docanalyzer.parser;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageParser {
    public static String parse(MultipartFile file) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(System.getenv("TESSERACT_ENDPOINT")); // Set Tesseract-OCR directory
        tesseract.setLanguage("eng"); // Set language to English

        String extractedText = "";
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream); // Can read .jpg, .png, .gif, .bmp, .wbmp, and .jpeg
            if (image == null) {
                throw new RuntimeException("Failed to read the image file.");
            }

            extractedText = tesseract.doOCR(image);
        } catch (IOException | TesseractException e) {
            throw new RuntimeException(e);
        }
        return extractedText.isEmpty() ? "No text found in the image." : extractedText;
    }
}
