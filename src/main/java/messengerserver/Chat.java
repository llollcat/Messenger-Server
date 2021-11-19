package messengerserver;

import java.util.List;

public class Chat {
    public List<Message> messages;
    public List<String> participants;
    public String chat_messages_id;

    public Chat(List<Message> messages, List<String> participants, String chat_messages_id) {
        this.messages = messages;
        this.participants = participants;
        this.chat_messages_id = chat_messages_id;
    }

    public Chat() {
    }

    ;
}
