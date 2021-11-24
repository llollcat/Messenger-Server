package messengerServer.API.Request;

import messengerServer.API.Response.AuthorizationResponse;
import messengerServer.Application;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/register")
public class Registration {


    protected Boolean isLoginOK(String login) {
        return ((login.length() > 5) && (login.length() < 16));


    }


    @PostMapping
    public AuthorizationResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {

        if (!isLoginOK(login)) {
            return new AuthorizationResponse("Pick up another username", 409, "");
        }
        try {
            DbHandler dbHandler = DbHandler.getInstance();

            String result = dbHandler.registerUser(login, passwd);
            if (result.equals(Application.OK_CODE))
                return new AuthorizationResponse(Application.SUCCESS_STATUS, 200, dbHandler.getUserByLogin(login).userToken);
            else
                return new AuthorizationResponse(result, 409, "");
        } catch (SQLException e) {
            return new AuthorizationResponse(Application.FAILED_STATUS, 500, "");
        }
    }


}

