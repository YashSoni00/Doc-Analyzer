package com.example.docanalyzer.service;

import com.example.docanalyzer.exception.UnsupportedFile;
import com.example.docanalyzer.parser.ImageParser;
import com.example.docanalyzer.parser.PdfParser;
import com.example.docanalyzer.parser.TextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {

    @Autowired
    TikaService tikaService;

    public String processFile(MultipartFile file) throws IOException {
        // Your logic to process the file and extract text.
        String extractedText;
        String fileType = tikaService.detectFileType(file);
        if (fileType.contains("pdf")) {
            System.out.println("Processing PDF file");
            extractedText = PdfParser.parse(file);
        } else if (fileType.contains("image")) {
            System.out.println("Processing image file");
            extractedText = ImageParser.parse(file);
        } else if (fileType.contains("text") || fileType.contains("csv") || fileType.contains("document")) {
            System.out.println("Processing text file");
            extractedText = TextParser.parse(file);
        } else if (file.getOriginalFilename().contains(".docx")) {
            System.out.println("Processing docx file");
            extractedText = TextParser.parseDocx(file);
        } else {
            throw new UnsupportedFile("Unsupported file type: " + fileType);
        }
        return extractedText;
    }

    public String forTest(MultipartFile file) throws IOException {
        String fileType = tikaService.detectFileType(file);
        String result;
        if (fileType.contains("pdf")) {
            result = "PDF file.";
        } else if (fileType.contains("image")) {
            result = "Image file.";
        } else if (fileType.contains("text") || fileType.contains("csv") || fileType.contains("document")) {
            result = "Text file.";
        } else {
            throw new UnsupportedFile("Unsupported file type: " + fileType);
        }
        return result;
    }
}