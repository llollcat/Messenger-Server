package messengerServer.API.Response;

public class ChatResponse{

    private final String status;
    private final Integer code;
    private final String chatId;

    public ChatResponse(String status, Integer code, String chatId) {
        this.status = status;
        this.code = code;
        this.chatId = chatId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getChatId() {
        return chatId;
    }
}
