package com.example.docanalyzer.parser;

import com.asprise.ocr.Ocr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageParser {

    public static String parse(MultipartFile file) {
        Ocr.setUp();
        Ocr ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_FASTEST);

        String extractedText = "";
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

            Path tempImagePath = Files.createTempFile("temp", ".png");
            ImageIO.write(image, "png", tempImagePath.toFile());

            extractedText = ocr.recognize(
                    new File[] {new File(String.valueOf(tempImagePath))},
                    Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT
            );

            System.out.println("Extracted text: " + extractedText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return extractedText.isEmpty() ? "No text found in the image." : extractedText;
    }
}
