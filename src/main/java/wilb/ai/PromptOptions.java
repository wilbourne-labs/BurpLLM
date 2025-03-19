package wilb.ai;

public class PromptOptions {
    private final double temperature;

    private PromptOptions(double temperature) {
        this.temperature = temperature;
    }

    public static PromptOptions promptOptions() {
        return new PromptOptions(0.7); // Default temperature
    }

    public PromptOptions withTemperature(double temperature) {
        return new PromptOptions(temperature);
    }

    public double getTemperature() {
        return temperature;
    }
}