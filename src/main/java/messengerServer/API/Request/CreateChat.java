
package messengerServer.API.Request;

import messengerServer.API.Response.ChatResponse;
import messengerServer.Application;
import messengerServer.Chat;
import messengerServer.User;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/createChat")
public class CreateChat {


    @PostMapping
    public ChatResponse doTask(@RequestParam(name = "userToken") String userToken) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(userToken);
            if (user == null)
                return new ChatResponse("token error", 409, "");
            Chat chat = dbHandler.createChat(user.userToken);
            if (chat == null)
                return new ChatResponse(Application.FAILED_STATUS, 500, "");
            return new ChatResponse(Application.SUCCESS_STATUS, 200, chat.chatMessagesId);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ChatResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}
