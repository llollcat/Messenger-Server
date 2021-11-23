package messengerserver.API;

import messengerserver.Message;

import java.util.List;
import java.util.Map;

public class ChatsUpdates {

    public String updatesOwner;
    public Map<String, Boolean> chats_ids;

    public ChatsUpdates(String updatesOwner, Map<String, Boolean> chats_ids) {
        this.updatesOwner = updatesOwner;
        this.chats_ids = chats_ids;
    }
    public  ChatsUpdates(){;}
}
