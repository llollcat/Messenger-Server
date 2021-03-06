package messengerServer.API.Request;


import messengerServer.*;
import messengerServer.API.Response.BaseResponse;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/setAvatar")
public class SetAvatar {


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "token") String userToken, @RequestParam(name = "avatarUrl") String avatarUrl) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(userToken);
            if (user == null)
                return new BaseResponse("token error", 409);

            dbHandler.setAvatar(user, avatarUrl);
            return new BaseResponse(Application.SUCCESS_STATUS, 200);

        } catch (SQLException e) {
            e.printStackTrace();
            return new BaseResponse(Application.FAILED_STATUS, 500);
        }


    }


}


