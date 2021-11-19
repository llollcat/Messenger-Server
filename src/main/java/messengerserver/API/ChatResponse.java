package messengerserver.API;

public class ChatResponse{

    private final String status;
    private final Integer code;
    private final String chat_id;

    public ChatResponse(String status, Integer code, String chat_id) {
        this.status = status;
        this.code = code;
        this.chat_id = chat_id;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getChat_id() {
        return chat_id;
    }
}
