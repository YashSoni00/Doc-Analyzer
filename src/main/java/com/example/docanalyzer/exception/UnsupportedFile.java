package com.example.docanalyzer.exception;

public class UnsupportedFile extends RuntimeException {
    public UnsupportedFile(String message) {
        System.out.println("Unsupported file type: " + message);
    }
}
