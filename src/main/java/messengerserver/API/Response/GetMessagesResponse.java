package messengerserver.API.Response;

import messengerserver.Message;

import java.util.List;

public class GetMessagesResponse {

    private final String status;
    private final Integer code;
    private final List<Message> messages;


    public GetMessagesResponse(String status, Integer code, List<Message> messages) {
        this.status = status;
        this.code = code;
        this.messages = messages;
    }


    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public List<Message> getMessages() {
        return messages;
    }
}








