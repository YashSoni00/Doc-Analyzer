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
            if (contentType != null) {
                if (contentType.contains("plain")) { // .txt files
                    extractedText = parseTxt(file);
                } else if (contentType.contains("csv")) { // .csv files
                    extractedText = parseCsv(file);
                }
            } else {
                extractedText = "Unknown file type.";
            }
        } catch (IOException e) {
            extractedText = "Error reading the file: " + e.getMessage();
        }

        return extractedText;
    }

    /**
     * Parse plain text (.txt) files.
     */
    private static String parseTxt(MultipartFile file) throws IOException {
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
     * Parse CSV (.csv) files.
     */
    private static String parseCsv(MultipartFile file) throws IOException {
        StringBuilder csvContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                csvContent.append(line).append("\n");
            }
        }

        return csvContent.toString();
    }

    /**
     * Parse Word documents (.docx).
     */
    public static String parseDocx(MultipartFile file) {
        String extractedText = "Error parsing the file.";
        try (XWPFDocument document = new XWPFDocument(file.getInputStream());
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            // Extract text from the document
            extractedText = extractor.getText();
//            System.out.println("Extracted text: " + (extractedText.isEmpty() ? "Empty" : extractedText));
        } catch (IOException e) {
            extractedText = "Failed to parse .docx file: " + e.getMessage();
            e.printStackTrace();
        }
        return extractedText;
    }
}