package wilb.ai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String model;
    private static final String apiKey;
    private static final String apiUrl;

    // Static block to load model, API key, and default temperature once
    static {
        Properties properties = new Properties();
        String tempModel = "gpt-4o"; // Default model
        String tempApiKey = null;
        String tempApiUrl = null;

        try (InputStream inputStream = PromptOptions.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                tempModel = properties.getProperty("model", tempModel);
                tempApiKey = properties.getProperty("apiKey");
                tempApiUrl = properties.getProperty("apiUrl");
            } else {
                System.out.println("Warning: config.properties not found, using defaults.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        model = tempModel;
        apiKey = tempApiKey;
        apiUrl = tempApiUrl;
    }

    // Getters
    public String getModel() {
        return model;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl(){
        return apiUrl;
    }
}

