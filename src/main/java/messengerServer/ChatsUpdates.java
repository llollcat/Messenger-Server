package messengerServer;

import java.util.Map;

public class ChatsUpdates {

    public String updatesOwner;
    public Map<String, Boolean> chatsIds;

    public ChatsUpdates(String updatesOwner, Map<String, Boolean> chatsIds) {
        this.updatesOwner = updatesOwner;
        this.chatsIds = chatsIds;
    }
    public  ChatsUpdates(){}
}
