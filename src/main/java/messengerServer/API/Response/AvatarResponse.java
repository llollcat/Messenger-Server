package messengerServer.API.Response;



public class AvatarResponse {


    private final String status;
    private final Integer code;
    private final String avatarUrl;


    public AvatarResponse(String status, Integer code, String avatarUrl) {
        this.status = status;
        this.code = code;
        this.avatarUrl = avatarUrl;
    }


    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
