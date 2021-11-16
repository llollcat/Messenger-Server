package messengerserver;

import java.util.List;

public class Chat {
    public List<Message> messages;
    public String hashed_participants;

    public Chat(List<Message> messages, String hashed_participants) {
        this.messages = messages;
        this.hashed_participants = hashed_participants;
    }
}
