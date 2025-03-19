package wilb.ai;

public class PromptResponse {
    private final String content;

    public PromptResponse(String content) {
        this.content = content;
    }

    public String content() {
        return content;
    }
}
