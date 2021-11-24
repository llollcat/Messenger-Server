
package messengerServer.API.Request;

import messengerServer.*;
import messengerServer.ChatsUpdates;
import messengerServer.API.Response.GetUpdatesResponse;
import messengerServer.DbHandler;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/GetUpdates")
public class GetUpdates {


    @GetMapping
    public GetUpdatesResponse doTask(@RequestParam(name = "token") String user_token) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            User user = dbHandler.getUserByToken(user_token);
            if (user == null)
                return new GetUpdatesResponse("token error", 409, new ChatsUpdates());

            ChatsUpdates chatsUpdates = dbHandler.getUpdates(user);

            return new GetUpdatesResponse(Application.SUCCESS_STATUS, 200, chatsUpdates);

        } catch (SQLException e) {
            e.printStackTrace();
            return new GetUpdatesResponse(Application.FAILED_STATUS, 500, new ChatsUpdates());
        }

    }


}
















