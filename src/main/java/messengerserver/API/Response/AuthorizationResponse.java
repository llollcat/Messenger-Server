package messengerserver.API.Response;

public class AuthorizationResponse {

    private final String status;
    private final Integer code;
    private final String token;


    public AuthorizationResponse(String status, Integer code, String token) {
        this.status = status;
        this.code = code;
        this.token = token;
    }


    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }
}
