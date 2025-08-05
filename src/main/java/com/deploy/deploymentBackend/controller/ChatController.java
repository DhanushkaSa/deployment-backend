package com.deploy.deploymentBackend.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deploy.deploymentBackend.service.UserService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = { "https://springboot-5678.web.app", "http://localhost:5173" }) // Allow all origins for
                                                                                       // simplicity; adjust as needed
public class ChatController {

        // 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

        // @Value("${OPENROUTER_API_KEY}") // <-- Spring will get
        // private String openRouterApiKey;
        // this from the Railway
        // env var

        @Autowired
        private UserService userService;

        @PostMapping
        public ResponseEntity<String> chatWithBot(@RequestBody Map<String, String> payload) throws Exception {
                String userMessage = payload.get("message");
                String userId = payload.get("userId");
                
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
                                                "Bearer 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")
                                .header("Content-Type", "application/json")
                                // .header("HTTP-Referer", "https://yourdomain.com") // Optional
                                // .header("X-Title", "My Free Chatbot") // Optional
                                .POST(HttpRequest.BodyPublishers.ofString(json))
                                .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return ResponseEntity.ok(response.body());
        }
}
