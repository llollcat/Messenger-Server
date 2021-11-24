package messengerServer;

import java.util.List;

public class Chat {
    public List<Message> messages;
    public List<String> participants;
    public String chatMessagesId;

    public Chat(List<Message> messages, List<String> participants, String chatMessagesId) {
        this.messages = messages;
        this.participants = participants;
        this.chatMessagesId = chatMessagesId;
    }
}
