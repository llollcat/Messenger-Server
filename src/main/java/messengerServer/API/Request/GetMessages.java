
package messengerServer.API.Request;

import messengerServer.*;
import messengerServer.API.Response.GetMessagesResponse;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/getMessages")
public class GetMessages {


    @PostMapping
    public GetMessagesResponse doTask(@RequestParam(name = "token") String user_token, @RequestParam(name = "chatId") String chat_id) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(user_token);
            if (user == null)
                return new GetMessagesResponse("token error", 409, new ArrayList<>());

            Chat chat = dbHandler.getChatById(chat_id);
            if (chat == null)
                return new GetMessagesResponse("chat id error", 409, new ArrayList<>());

            if (chat.participants.contains(user.login))
                return new GetMessagesResponse(Application.SUCCESS_STATUS, 200, chat.messages);

            return new GetMessagesResponse(Application.SUCCESS_STATUS, 200, new ArrayList<>());

        } catch (SQLException e) {
            e.printStackTrace();
            return new GetMessagesResponse(Application.FAILED_STATUS, 500, new ArrayList<>());
        }


    }


}









