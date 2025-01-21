package com.example.docanalyzer.controller;

import com.example.docanalyzer.model.SummaryStyle;
import com.example.docanalyzer.service.DocumentService;
import com.example.docanalyzer.service.TextSummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping(value = "/upload-form", produces = "text/html")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("summaryType") String summaryType, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "error";
        }

        if (file.getContentType() == null) {
            model.addAttribute("error", "Invalid file type. Please upload a valid file.");
            return "error";
        }

        try {
            String fileName = file.getOriginalFilename();

            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            assert fileName != null;
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            String extractedText = documentService.processFile(file);

            SummaryStyle style = SummaryStyle.valueOf(summaryType.toUpperCase());
            String summary = TextSummarizeService.getSummary(extractedText, style);
            summary = formatSummary(summary);

            model.addAttribute("content", summary);
            model.addAttribute("fileName", fileName);

            // Delete the uploaded file after processing
//            Files.delete(filePath);

            return "result";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while processing the file: " + e.getMessage());
            return "error";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatSummary(String summary) {
        return summary.replace("`html", "")
                .replace("\\n\\n", "<br>")
                .replace("\\n", "<br>")
                .replace("\n", "")
                .replace("`", "")
                .replace("  ", " ")
                .replace("\\", "")
                .replace("\\r", "");
    }
}