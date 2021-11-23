package messengerserver.API;

import java.util.Map;

public class GetUpdatesResponse {
    private final String status;
    private final Integer code;
    private final String updatesOwner;
    private final Map<String, Boolean> chats_ids;

    public  GetUpdatesResponse(String status, Integer code, ChatsUpdates updates){
        this.status = status;
        this.code = code;
        this.updatesOwner = updates.updatesOwner;
        this.chats_ids = updates.chats_ids;


    }


    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getUpdatesOwner() {
        return updatesOwner;
    }

    public Map<String, Boolean> getChats_ids() {
        return chats_ids;
    }
}
