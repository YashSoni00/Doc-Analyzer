package com.example.docanalyzer.service;

import com.example.docanalyzer.model.SummaryStyle;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TextSummarizeService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions"; // OpenAI API endpoint

    public static String getSummary(String text, SummaryStyle style) throws IOException, InterruptedException {
        // Prepare the request payload
        String prompt = switch (style) {
            case CONCISE -> "Please summarize the following text concisely and give the result formatted as HTML. Only give the result Do not give the html boilerplate code with it";
            case BALANCED -> "Please provide a balanced summary of the following text and give the result formatted as HTML. Only give the result Do not give the html boilerplate code with it";
            case DETAILED -> "Please provide a detailed description of the following text and give the result formatted as HTML. Only give the result Do not give the html boilerplate code with it";
        };
        String requestBody = String.format("""
                {
                    "model": "gpt-3.5-turbo",
                    "messages": [
                        {
                            "role": "system",
                            "content": "%s"
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }
                """, escapeJsonString(prompt), escapeJsonString(text));

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response is successful
        if (response.statusCode() == 200) {
            // Parse the JSON response to extract the summary
            String responseBody = response.body();
            return extractSummaryFromResponse(responseBody);
        } else {
            throw new IOException("API call failed with status code: " + response.statusCode());
        }
    }

    // Escape the input text for use in JSON format
    private static String escapeJsonString(String text) {
        return text.replace("\\", " ")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Extract the summary from the response JSON
    private static String extractSummaryFromResponse(String responseBody) {
        // Assuming responseBody is a valid JSON and we use a simple method for parsing
        // You can use libraries like Jackson or Gson for JSON parsing here
        int startIndex = responseBody.indexOf("\"content\": \"") + 12;
        int endIndex = responseBody.indexOf("\"", startIndex);
        if (endIndex != -1) {
            return responseBody.substring(startIndex, endIndex);
        }
        return "No summary found.";
    }

    public static String forTest(String text, SummaryStyle style) throws IOException, InterruptedException {
        return switch (style) {
            case CONCISE -> "Concise Summary";
            case BALANCED -> "Balanced Summary";
            case DETAILED -> "Detailed Summary";
        };
    }
}
