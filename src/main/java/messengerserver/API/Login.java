package messengerserver.API;

        import messengerserver.UniqueHashGenerator;
        import messengerserver.User;
        import messengerserver.db.*;
        import org.springframework.web.bind.annotation.*;

        import java.security.NoSuchAlgorithmException;
        import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class Login {
    private static final String SUCCESS_STATUS = "success";


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "login") String login, @RequestParam(name = "passwd") String passwd) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByLogin(login);

            try {
                if (user.passwd.equals(UniqueHashGenerator.getHash(passwd))) {
                    System.out.println("Logged");
                } else {
                    System.out.println("passwd inc");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return new BaseResponse(SUCCESS_STATUS, 1);
    }


}