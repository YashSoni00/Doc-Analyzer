package com.example.docanalyzer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResult {
    private String fileName; // Name of the uploaded file
    private String fileType; // MIME type (e.g., "text/plain", "application/pdf")
    private long fileSize;   // File size in bytes
    private String content;  // Extracted text/content from the file
    private String result;  // Generated summary of the content

    // Constructors
    public DocumentResult() {
    }
}
