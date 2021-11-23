package messengerserver.API.Request;

import messengerserver.API.Response.AuthorizationResponse;
import messengerserver.Application;
import messengerserver.DbHandler;
import messengerserver.UniqueHashGenerator;
import messengerserver.User;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class Login {


    @PostMapping
    public AuthorizationResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByLogin(login);
            if (user == null)
                return new AuthorizationResponse("incorrect login", 401, "");
            try {
                if (user.passwd.equals(UniqueHashGenerator.getHash(passwd))) {
                    return new AuthorizationResponse(Application.SUCCESS_STATUS, 200, user.userToken);
                } else {
                    return new AuthorizationResponse(Application.FAILED_STATUS, 401, "");
                }
            } catch (NoSuchAlgorithmException e) {
                return new AuthorizationResponse(Application.FAILED_STATUS, 500, "");
            }
        } catch (SQLException e) {
            return new AuthorizationResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}