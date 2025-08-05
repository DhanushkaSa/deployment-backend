package com.deploy.deploymentBackend.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = { "https://springboot-5678.web.app", "http://localhost:5173" }) // Allow all origins for
                                                                                       // simplicity; adjust as needed
public class ChatController {

        // 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
        private final Dotenv dotenv = Dotenv.load();

        @PostMapping
        public ResponseEntity<String> chatWithBot(@RequestBody Map<String, String> payload) throws Exception {
                String userMessage = payload.get("message");

                HttpClient client = HttpClient.newHttpClient();

                String json = """
                                {
                                  "model": "mistralai/mistral-7b-instruct",
                                  "messages": [
                                    {"role": "user", "content": "%s"}
                                  ]
                                }
                                """.formatted(userMessage);

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                                .header("Authorization",
                                                "Bearer " + dotenv.get("OPENROUTER_API_KEY"))
                                .header("Content-Type", "application/json")
                                // .header("HTTP-Referer", "https://yourdomain.com") // Optional
                                // .header("X-Title", "My Free Chatbot") // Optional
                                .POST(HttpRequest.BodyPublishers.ofString(json))
                                .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return ResponseEntity.ok(response.body());
        }
}
