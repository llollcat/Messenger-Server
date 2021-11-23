package messengerserver.API.Request;

import messengerserver.*;
import messengerserver.API.Response.BaseResponse;
import messengerserver.DbHandler;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/editMessage")
public class EditMessage {


    @GetMapping
    public BaseResponse doTask(@RequestParam(name = "userToken") String userToken, @RequestParam(name = "chatId") String chatId,
                               @RequestParam(name = "timestamp") Integer timestamp, @RequestParam(name = "new_text") String text,
                               @RequestParam(name = "new_image") String image) {
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