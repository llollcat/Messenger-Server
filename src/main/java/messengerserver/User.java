package messengerserver;

import java.sql.Blob;
import java.util.List;
public class User {


    public String login;
    public String passwd;
    public String user_token;
    public String avatar;
    public List<Integer> chats;

    public User(String login, String passwd, String user_token, String avatar, List<Integer> chats) {
        this.login = login;
        this.passwd = passwd;
        this.user_token = user_token;
        this.avatar = avatar;
        this.chats = chats;
    }
    public User(){};
}
