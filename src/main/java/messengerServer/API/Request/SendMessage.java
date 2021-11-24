
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
@RequestMapping("/sendMessage")
public class SendMessage {


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "userToken") String userToken, @RequestParam(name = "chatId") String chatId,
                               @RequestParam(name = "message") String message, @RequestParam(name = "imageUrl") String imageUrl) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(userToken);
            if (user == null)
                return new BaseResponse("token error", 409);

            Chat chat = dbHandler.getChatById(chatId);
            if (chat == null)
                return new BaseResponse("chat id error", 409);

            if (chat.participants.contains(user.login))
                dbHandler.SendMessage(chat, new Message(message, imageUrl, DbHandler.GetTimestamp(), user.login));
            return new BaseResponse(Application.SUCCESS_STATUS, 200);

        } catch (SQLException e) {
            e.printStackTrace();
            return new BaseResponse(Application.FAILED_STATUS, 500);
        }


    }

}