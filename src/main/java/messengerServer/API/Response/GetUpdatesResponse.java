package messengerServer.API.Response;

import messengerServer.ChatsUpdates;

import java.util.Map;

public class GetUpdatesResponse {
    private final String status;
    private final Integer code;
    private final String updatesOwner;
    private final Map<String, Boolean> chatsIds;

    public GetUpdatesResponse(String status, Integer code, ChatsUpdates updates) {
        this.status = status;
        this.code = code;
        this.updatesOwner = updates.updatesOwner;
        this.chatsIds = updates.chatsIds;


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

    public Map<String, Boolean> getChatsIds() {
        return chatsIds;
    }
}
