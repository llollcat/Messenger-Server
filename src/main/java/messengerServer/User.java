package messengerServer;

import java.util.List;
public class User {
    public String login;
    public String passwd;
    public String userToken;
    public String avatar;
    public List<Integer> chats;


    public User(String login, String passwd, String userToken, String avatar, List<Integer> chats) {
        this.login = login;
        this.passwd = passwd;
        this.userToken = userToken;
        this.avatar = avatar;
        this.chats = chats;
    }
}
