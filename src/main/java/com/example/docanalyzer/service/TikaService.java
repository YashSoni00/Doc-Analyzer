package com.example.docanalyzer.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class TikaService {
    private final Tika tika;

    public TikaService() {
        this.tika = new Tika();
    }

    /**
     * Detect the file type using Tika.
     *
     * @param file the uploaded file
     * @return the detected MIME type of the file
     * @throws IOException if an I/O error occurs
     */
    public String detectFileType(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return tika.detect(inputStream);
        }
    }
}
