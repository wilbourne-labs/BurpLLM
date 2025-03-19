package wilb.ai;

public class Message {
    private final String role;
    private final String content;

    private Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static Message systemMessage(String content) {
        return new Message("system", content);
    }

    public static Message userMessage(String content) {
        return new Message("user", content);
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
