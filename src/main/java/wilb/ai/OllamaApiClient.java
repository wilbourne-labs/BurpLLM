package wilb.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class OllamaApiClient {
    private final HttpClient httpClient;

    public OllamaApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public PromptResponse executePrompt(PromptOptions options, Message... messages) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Config config = new Config();
            String requestBody = objectMapper.writeValueAsString(new OllamaRequest(options, config, messages));
            String OLLAMA_API_URL = config.getApiUrl();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String content = jsonResponse.get("response").asText();

            return new PromptResponse(content);
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Ollama API: " + e.getMessage(), e);
        }
    }

    private static class OllamaRequest {
        @JsonProperty
        private final String model;

        @JsonProperty
        private final String prompt;

        @JsonProperty
        private final double temperature;

        public boolean stream = false; // Ensure a single JSON response

        public OllamaRequest(PromptOptions options, Config config, Message... messages) {
            this.model = config.getModel();
            this.temperature = options.getTemperature();
            this.prompt = buildPrompt(messages);
        }

        private String buildPrompt(Message... messages) {
            StringBuilder promptBuilder = new StringBuilder();
            for (Message message : messages) {
                promptBuilder.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
            }
            return promptBuilder.toString();
        }
    }
}
