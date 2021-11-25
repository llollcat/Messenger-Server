package messengerServer.API.Request;

import messengerServer.*;
import messengerServer.API.Response.BaseResponse;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/editMessage")
public class EditMessage {


    @PostMapping
    public BaseResponse doTask(@RequestParam(name = "token") String userToken, @RequestParam(name = "chatId") String chatId,
                               @RequestParam(name = "timestamp") String timestamp, @RequestParam(name = "newText") String text,
                               @RequestParam(name = "newImage") String image) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(userToken);
            if (user == null)
                return new BaseResponse("token error", 409);

            Chat chat = dbHandler.getChatById(chatId);
            if (chat == null)
                return new BaseResponse("chat id error", 409);

            dbHandler.EditMessage(chat, user, timestamp, text, image);

            return new BaseResponse(Application.SUCCESS_STATUS, 200);

        } catch (SQLException e) {
            e.printStackTrace();
            return new BaseResponse(Application.FAILED_STATUS, 500);
        }

    }

}