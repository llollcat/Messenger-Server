package messengerserver.API;

import messengerserver.db.*;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/register")
public class Registration {
    private static final String SUCCESS_STATUS = "success";


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {


        try {
            DbHandler dbHandler = DbHandler.getInstance();

            System.out.println(dbHandler.registarteUser(login, passwd));
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return new BaseResponse(SUCCESS_STATUS, 1);
    }


}

