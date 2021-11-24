package messengerServer.API.Request;


import messengerServer.*;
import messengerServer.API.Response.AvatarResponse;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/getAvatar")
public class GetAvatar {


    @GetMapping
    public AvatarResponse doTask(@RequestParam(name = "login") String login) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByLogin(login);
            if (user == null)
                return new AvatarResponse("login doesn't exist", 409, "");

            dbHandler.getAvatar(user);
            return new AvatarResponse(Application.SUCCESS_STATUS, 200, dbHandler.getAvatar(user));

        } catch (SQLException e) {
            e.printStackTrace();
            return new AvatarResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}



