package messengerserver.API;

        import messengerserver.Application;
        import messengerserver.UniqueHashGenerator;
        import messengerserver.User;
        import messengerserver.db.*;
        import org.springframework.web.bind.annotation.*;

        import java.security.NoSuchAlgorithmException;
        import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class Login {


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByLogin(login);
            if (user == null)
                return new BaseResponse("incorrect login", 401, "");
            try {
                if (user.passwd.equals(UniqueHashGenerator.getHash(passwd))) {
                    return new BaseResponse(Application.SUCCESS_STATUS, 200, user.user_token);
                } else {
                    return new BaseResponse(Application.FAILED_STATUS, 401, "");
                }
            } catch (NoSuchAlgorithmException e) {
                return new BaseResponse(Application.FAILED_STATUS, 500, "");
            }
        } catch (SQLException e) {
            return new BaseResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}