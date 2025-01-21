package com.example.docanalyzer;

import com.example.docanalyzer.exception.UnsupportedFile;
import com.example.docanalyzer.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    void testProcessPdfFile() throws IOException {
        File pdfFile = new File("src/test/resources/test-files/samplePdf.pdf");
        MockMultipartFile pdf = new MockMultipartFile(
                "pdfFile",
                pdfFile.getName(),
                Files.probeContentType(pdfFile.toPath()),
                new FileInputStream(pdfFile)
        );
        String result = documentService.forTest(pdf);
        assertEquals("PDF file.", result);
    }

    @Test
    void testProcessImageFile() throws IOException {
        File imageFile = new File("src/test/resources/test-files/sampleImage.jpg");
        MockMultipartFile image = new MockMultipartFile(
                "imageFile",
                imageFile.getName(),
                Files.probeContentType(imageFile.toPath()),
                new FileInputStream(imageFile)
        );
        String result = documentService.forTest(image);
        assertEquals("Image file.", result);
    }

    @Test
    void testProcessTextFile() throws IOException {
        // Test plain text file
        File textFile = new File("src/test/resources/test-files/sampleTest.txt");
        MockMultipartFile text = new MockMultipartFile(
                "textFile",
                textFile.getName(),
                Files.probeContentType(textFile.toPath()),
                new FileInputStream(textFile)
        );
        assertEquals("Text file.", documentService.forTest(text));
    }

    @Test
    void testUnsupportedFileType() throws IOException {
        File unsupportedFile = new File("src/test/resources/test-files/sampleAudio.mp4");
        MockMultipartFile audio = new MockMultipartFile(
                "audioFile",
                unsupportedFile.getName(),
                Files.probeContentType(unsupportedFile.toPath()),
                new FileInputStream(unsupportedFile)
        );
        assertThrows(UnsupportedFile.class, () -> {
            documentService.forTest(audio);
        });
    }
}
