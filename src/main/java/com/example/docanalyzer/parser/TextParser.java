package com.example.docanalyzer.parser;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TextParser {

    public static String parse(MultipartFile file) {
        String contentType = file.getContentType();
        String extractedText = "Unsupported file type.";

        try {
            if ((contentType != null) && (contentType.contains("plain") || contentType.contains("csv"))) {
                extractedText = parseTxtandCsv(file);
            } else {
                extractedText = "Unknown file type.";
            }
        } catch (IOException e) {
            extractedText = "Error reading the file: " + e.getMessage();
        }
        return extractedText;
    }

    /**
     * Parse plain text (.txt) and CSV (.csv) files.
     */
    private static String parseTxtandCsv(MultipartFile file) throws IOException {
        StringBuilder textContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textContent.append(line).append("\n");
            }
        }
        return textContent.toString();
    }

    /**
     * Parse Word documents (.docx).
     */
    public static String parseDocx(MultipartFile file) {
        String extractedText;
        try (XWPFDocument document = new XWPFDocument(file.getInputStream());
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            // Extract text from the document
            extractedText = extractor.getText();
        } catch (IOException e) {
            extractedText = "Failed to parse .docx file: " + e.getMessage();
            e.printStackTrace();
        }
        return extractedText;
    }
}