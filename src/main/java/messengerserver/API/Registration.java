package messengerserver.API;

import messengerserver.Application;
import messengerserver.db.*;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/register")
public class Registration {



    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {


        try {
            DbHandler dbHandler = DbHandler.getInstance();

            String result = dbHandler.registarteUser(login, passwd);
            if (result.equals(Application.OK_CODE))
                return new BaseResponse(Application.SUCCESS_STATUS, 200, dbHandler.getUserByLogin(login).user_token);
            else
                return new BaseResponse(result, 409, "");
        } catch (SQLException e) {
            return new BaseResponse(Application.FAILED_STATUS, 500, "");
        }
    }


}

