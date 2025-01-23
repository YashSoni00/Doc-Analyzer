package com.example.docanalyzer.parser;

import com.example.docanalyzer.service.OcrService;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.InputStream;

public class ImageParser {

    public static String parse(MultipartFile file) {
        String extractedText;
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream); // Can read .jpg, .png, .gif, .bmp, .wbmp, and .jpeg
            if (image == null) {
                throw new RuntimeException("Failed to read the image file.");
            }

            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
            rescaleOp.filter(image, image);

            OcrService ocrService = new OcrService();
            extractedText = ocrService.processImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return extractedText.isEmpty() ? "No text found in the image." : extractedText;
    }
}
