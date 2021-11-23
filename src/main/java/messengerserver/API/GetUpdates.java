
package messengerserver.API;

import messengerserver.*;
import messengerserver.db.DbHandler;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping("/GetUpdates")
public class GetUpdates {


    @GetMapping
    public GetUpdatesResponse doTask(@RequestParam(name = "token") String user_token) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(user_token);
            if (user == null)
                return new GetUpdatesResponse("token error", 409,new ChatsUpdates());

            ChatsUpdates chatsUpdates =  dbHandler.getUpdates(user);

            return new GetUpdatesResponse(Application.SUCCESS_STATUS, 200 ,chatsUpdates);


           // if (chat.participants.contains(user.login))
              //  return new GetMessagesResponse(Application.SUCCESS_STATUS, 200, chat.messages);

          //  return new GetMessagesResponse(Application.SUCCESS_STATUS, 200, new ArrayList<Message>());

        } catch (SQLException e) {
            e.printStackTrace();
            return new GetUpdatesResponse(Application.FAILED_STATUS, 500, new ChatsUpdates());
        }

    }


}
















