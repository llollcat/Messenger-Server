package messengerserver;

import org.sqlite.JDBC;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DbHandler {
    private static final String SLQ_LITE_DB_PATH = "jdbc:sqlite:main.sqlite";
    private final Connection connection;
    private static DbHandler instance = null;


    public static long GetTimestamp() {
        return System.currentTimeMillis();
    }


    private void initDb() {
        try {

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE USERS(login TEXT UNIQUE NOT NULL, passwd TEXT NOT NULL, user_token TEXT UNIQUE, chats TEXT, avatar TEXT)");
            statement.executeUpdate("CREATE TABLE CHATS(chat_messages_id TEXT NOT NULL, participants TEXT)");
            statement.executeUpdate("CREATE TABLE UPDATES(login TEXT, chat_id TEXT NOT NULL, has_mention BOOL)");
            System.out.println("DB initialized");
        } catch (java.sql.SQLException ignored) {
        }
    }


    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbHandler();
            instance.initDb();
        }

        return instance;
    }


    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(SLQ_LITE_DB_PATH);
    }


    public User getUserByLogin(String login) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from USERS WHERE `login`=? ")) {
            statement.setObject(1, login);
            ResultSet resultSet = statement.executeQuery();

            List<Integer> chats = new ArrayList<>();
            if (resultSet.getString("chats") != null) {
                for (String i : resultSet.getString("chats").split(" ")) {
                    chats.add(Integer.parseInt(i));
                }
            }
            return new User(resultSet.getString("login"), resultSet.getString("passwd"),
                    resultSet.getString("user_token"), resultSet.getString("avatar"), chats);


        } catch (SQLException e) {
            return null;
        }
    }


    public User getUserByToken(String userToken) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from USERS WHERE `user_token`=? ")) {
            statement.setObject(1, userToken);
            ResultSet resultSet = statement.executeQuery();

            List<Integer> chats = new ArrayList<>();
            if (resultSet.getString("chats") != null) {
                for (String i : resultSet.getString("chats").split(" ")) {
                    chats.add(Integer.parseInt(i));
                }
            }
            return new User(resultSet.getString("login"), resultSet.getString("passwd"), resultSet.getString("userToken"), resultSet.getString("avatar"), chats);


        } catch (SQLException e) {
            return null;
        }
    }


    public String registerUser(String login, String passwd) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO USERS(`login`, `passwd`, `user_token`) VALUES(?, ?, ?)")) {
            statement.setObject(1, login);
            try {
                statement.setObject(2, UniqueHashGenerator.getHash(passwd));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            statement.setObject(3, messengerserver.UniqueHashGenerator.getNewUserToken());
            statement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 19)
                return "Pick up another username";
        }
        return Application.OK_CODE;
    }


    public Chat createChat(String userToken) {
        User user = getUserByToken(userToken);
        if (user == null)
            return null;

        String id = UniqueHashGenerator.getChatId();
        try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO CHATS(`chat_messages_id`,`participants`) VALUES(?, ?);")) {
            statement.setObject(1, id);
            statement.setObject(2, user.login);
            statement.execute();

        } catch (SQLException e) {
            return null;
        }

        try (PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE CHAT_" + id + "(timestamp INTEGER NOT NULL, who_wrote TEXT NOT NULL, message_text TEXT, image TEXT);")) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new Chat(new ArrayList<>(), Collections.singletonList(user.login), id);

    }


    public void SendMessage(Chat chat, Message message) {
        try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO CHAT_" + chat.chatMessagesId +
                "(`timestamp`, `who_wrote`, `message_text`, `image` ) VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, GetTimestamp());
            statement.setObject(2, message.whoWrote);
            statement.setObject(3, message.text);
            statement.setObject(4, message.image);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (String p : chat.participants) {
            Boolean mention = null;

            try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM UPDATES WHERE `login` = ? AND `chat_id` = ?")) {
                statement.setObject(1, p);
                statement.setObject(2, chat.chatMessagesId);

                mention = statement.executeQuery().getBoolean("has_mention");

            } catch (SQLException ignored) {
            }

            try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM UPDATES WHERE `login` = ? AND `chat_id` = ?")) {
                statement.setObject(1, p);
                statement.setObject(2, chat.chatMessagesId);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }


            try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO UPDATES(`login`, `chat_id`, `has_mention`) VALUES(?, ?, ?)")) {
                statement.setObject(1, p);
                statement.setObject(2, chat.chatMessagesId);
                Pattern pattern = Pattern.compile("(?<=@)" + p);
                Matcher matcher = pattern.matcher(message.text);
                if (Boolean.TRUE.equals(mention)) {
                    statement.setObject(3, true);
                } else {
                    statement.setObject(3, matcher.find());
                }
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    public Chat getChatById(String id) {
        List<String> participants;
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from CHATS WHERE `chat_messages_id` = ?")) {
            statement.setObject(1, id);
            ResultSet select_result = statement.executeQuery();

            participants = new ArrayList<>(Arrays.asList(select_result.getString("participants").split(" ")));


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from CHAT_" + id)) {
            ResultSet result = statement.executeQuery();


            ArrayList<Message> messages = new ArrayList<>();
            while (result.next()) {
                messages.add(new Message(result.getString("message_text"), result.getString("image"), result.getLong("timestamp"), result.getString("who_wrote")));
            }
            return new Chat(messages, participants, id);


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }


    }


    public ChatsUpdates getUpdates(User user) {
        Map<String, Boolean> chats_ids = new HashMap<>();
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM UPDATES WHERE `login` = ?")) {
            statement.setObject(1, user.login);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                chats_ids.put(result.getString("chat_id"), result.getBoolean("has_mention"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM UPDATES WHERE `login` = ?")) {
            statement.setObject(1, user.login);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return new ChatsUpdates(user.login, chats_ids);

    }


    public void EditMessage(Chat chat, User user, Integer timestamp, String newText, String newImage) {
        try (PreparedStatement statement = this.connection.prepareStatement("UPDATE CHAT_" + chat.chatMessagesId +
                " SET message_text =?, image = ? WHERE timestamp = ? AND who_wrote = ?")) {
            statement.setObject(1, newText);
            statement.setObject(2, newImage);
            statement.setObject(3, timestamp);
            statement.setObject(4, user.login);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setAvatar(User user, String avatar) {
        try (PreparedStatement statement = this.connection.prepareStatement("UPDATE USERS SET avatar=? WHERE login =?")) {
            statement.setObject(1, avatar);
            statement.setObject(2, user.login);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public String getAvatar(User user) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM USERS WHERE login = ?")) {
            statement.setObject(1, user.login);
            return statement.executeQuery().getString("avatar");

        } catch (SQLException ignored) {
            return null;
        }


    }
}
