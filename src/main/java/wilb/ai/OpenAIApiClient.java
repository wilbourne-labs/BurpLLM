package wilb.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OpenAIApiClient {
    private final HttpClient httpClient;
    
    public OpenAIApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public PromptResponse executePrompt(PromptOptions options, Message... messages) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Config config = new Config();
            String requestBody = objectMapper.writeValueAsString(new OpenAIRequest(options,config,messages));
            String apiKey = config.getApiKey();
            String OPENAI_API_URL = config.getApiUrl();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_API_URL))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            System.out.println(request);

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            String content = jsonResponse.get("choices").get(0).get("message").get("content").asText();
            return new PromptResponse(content);
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with OpenAI API: " + e.getMessage(), e);
        }
    }

    private static class OpenAIRequest {
    @JsonProperty
    private final String model;

    @JsonProperty
    private final double temperature;

    @JsonProperty
    private final List<Message> messages;

    public OpenAIRequest(PromptOptions options,Config config, Message... messages) {
        this.model = config.getModel(); // Retrieve model from options
        this.temperature = options.getTemperature(); // Retrieve temperature from options
        this.messages = List.of(messages); // Convert messages array to a list
    }
    }
}