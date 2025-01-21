# Document Summary Assistant

**Document Summary Assistant** is a Spring Boot-based application designed to extract and generate intelligent summaries from various document types, such as PDFs, Word documents, and images. The project leverages tools for Optical Character Recognition (OCR), PDF processing, and document parsing to create concise summaries.

---

## Features

- **Document Parsing:** Supports PDF, Word documents, and images for text extraction.
- **Optical Character Recognition (OCR):** Extracts text from images using Tesseract OCR.
- **Text Summarization:** Provides smart and concise summaries of extracted text.
- **Web Interface:** A user-friendly interface built with Thymeleaf.
- **Real-time Processing:** Quickly extracts and summarizes text for instant results.

---

## Tech Stack

### Backend:
- **Java 17**
- **Spring Boot 3.4.1**
- **Apache POI** (For handling Word documents)
- **Apache PDFBox** (For handling PDF documents)
- **Apache Tika** (For content detection and text extraction)
- **Tess4J** (For OCR functionality)

### Frontend:
- **Thymeleaf** (For dynamic HTML rendering)

### Others:
- **Lombok** (For reducing boilerplate code)
- **JUnit** (For testing)

---

## Prerequisites

Ensure the following are installed on your system:
- Java 17
- Maven 3.8+  
- Tesseract OCR (Installable via [Tesseract GitHub](https://github.com/tesseract-ocr/tesseract))

---

## Installation and Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/document-summary-assistant.git
   cd document-summary-assistant
   ```

2. **Install dependencies:**
   Run the following Maven command to install all dependencies:
   ```bash
   mvn clean install
   ```

3. **Configure Tesseract OCR:**
   Ensure Tesseract is installed and configured in your system. Set the `TESSDATA_PREFIX` environment variable.

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
   Open your browser and go to:
   ```
   http://localhost:8080
   ```

---

## Usage

1. Upload a document or image through the web interface.
2. The application processes the input using OCR and document parsers.
3. View the extracted text and its summarized version on the result page.

---

## Dependencies

The following dependencies are used in this project:

- **Spring Boot Starter Web:** For building RESTful services.
- **Spring Boot Starter Thymeleaf:** For server-side rendering.
- **Apache POI:** For handling Word documents.
- **Apache PDFBox:** For processing PDF files.
- **Apache Tika:** For text extraction and content detection.
- **Tess4J:** For OCR capabilities.
- **Lombok:** For reducing boilerplate code.
- **JUnit:** For writing unit tests.

---

## Project Structure

```
DocumentSummaryAssistant/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── yash/
│   │   │           └── documentSummaryAssistant/
│   │   │               ├── controller/
│   │   │               │   └── DocumentController.java
│   │   │               ├── error/
│   │   │               │   └── ErrorPages.java
│   │   │               ├── service/
│   │   │               │   ├── DocumentService.java
│   │   │               │   ├── TextSummarizeService.java
│   │   │               │   ├── TikaService.java
│   │   │               │   └── OcrService.java
│   │   │               ├── model/
│   │   │               │   ├── DocumentSummary.java
│   │   │               │   └── SummaryStyle.java
│   │   │               ├── parser/
│   │   │               │   ├── ImageParser.java
│   │   │               │   ├── PdfParser.java
│   │   │               │   └── TextParser.java
│   │   │               ├── exception/
│   │   │               │   └── UnsupportedFile.java
│   │   │               └── DocumentSummaryAssistantApplication.java
│   │   └── resources/
│   │   │   ├── static/          # For static files like HTML, CSS, JS if using 
│   │   │       ├── css
│   │   │           ├── error.css
│   │   │           ├── index.css
│   │   │           ├── result.css
│   │   │           └── upload-form.css
│   │   │       ├── favicon.ico
│   │   │       └── favicon.png
│   │   │   ├── templates/       # Thymeleaf templates
│   │   │       ├── error.html
│   │   │       ├── index.html
│   │   │       ├── result.html
│   │   │       └── upload-form.html
│   │   │   ├── application.properties
│   │   │   └── tika-config.xml/        # Tika Configuration File
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── documentsummaryassistant/
│   │   │               └── service/
│   │   │                   ├── DocumentServiceTest.java
│   │   │                   ├── DocumentSummaryAssistantApplicationTests.java
│   │   │                   └── SummarizationServiceTest.java
├── .gitignore
├── .uploads # Directory to temporarily store uploaded files
├── pom.xml
└── README.md
```

---

## Future Enhancements

- Add support for additional file formats (e.g., Excel, JSON).
- Incorporate advanced Natural Language Processing (NLP) techniques for better summaries.
- Implement user authentication and file storage for history tracking.

---

## Contributing

Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your message here"
   ```
4. Push your changes:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Create a pull request.

---

## License

This project is licensed under the **MIT License**. See the `LICENSE` file for more details.

---

## Contact

For any questions or suggestions, feel free to reach out:
- **Developer:** Yash  
- **Email:** ysyashsoni27@gmail.com
