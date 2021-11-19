
package messengerserver.API;

import messengerserver.Application;
import messengerserver.Chat;
import messengerserver.UniqueHashGenerator;
import messengerserver.User;
import messengerserver.db.DbHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequestMapping("/createChat")
public class CreateChat {


    @PostMapping
    public ChatResponse doTask(@RequestParam(name = "token") String user_token) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(user_token);
            if (user == null)
                return new ChatResponse("token error", 409, "");
            Chat chat = dbHandler.createChat(user.user_token);
            if (chat == null)
                return new ChatResponse(Application.FAILED_STATUS, 500, "");
            return new ChatResponse(Application.SUCCESS_STATUS, 200, chat.chat_messages_id );

        } catch (SQLException e) {
            e.printStackTrace();
            return new ChatResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}
