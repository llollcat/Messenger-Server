
package messengerserver.API;

import messengerserver.*;
import messengerserver.db.DbHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/sendMessage")
public class SendMessage {


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "token") String user_token, @RequestParam(name = "chat_id") String chat_id, @RequestParam(name = "message") String message) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(user_token);
            if (user == null)
                return new BaseResponse("token error", 409, "");

            Chat chat = dbHandler.getChatById(chat_id);
            if (chat == null)
                return new BaseResponse("chat id error", 409, "");

            if (chat.participants.contains(user.login))
                dbHandler.SendMessage(chat, new Message(message, "poka tut pusto", DbHandler.GetTimestamp(), user.login));
            return new BaseResponse(Application.SUCCESS_STATUS, 200, user_token);

        } catch (SQLException e) {
            e.printStackTrace();
            return new BaseResponse(Application.FAILED_STATUS, 500, "");
        }


    }


}






