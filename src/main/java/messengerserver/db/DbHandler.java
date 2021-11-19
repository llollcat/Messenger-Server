package messengerserver.db;

import messengerserver.*;
import org.sqlite.JDBC;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;


public class DbHandler {
    private static final String SLQ_LITE_DB_PATH = "jdbc:sqlite:main.db";
    private static DbHandler instance = null;

    private void initDb() {
        try {

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE USERS(login TEXT UNIQUE NOT NULL, passwd TEXT NOT NULL, user_token TEXT UNIQUE, chats TEXT, avatar TEXT)");
            statement.executeUpdate("CREATE TABLE CHATS(chat_messages_id TEXT NOT NULL, participants TEXT)");
            statement.executeUpdate("CREATE TABLE UPDATES(login TEXT, chat_id TEXT NOT NULL, has_mention BOOL)");
        } catch (java.sql.SQLException e) {
            // OK todo удалить
            //  e.printStackTrace();
        }
    }

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbHandler();
            instance.initDb();
        }
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(SLQ_LITE_DB_PATH);

    }


    public User getUserByLogin(String login) {


        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from USERS WHERE `login`=? ")) {
            statement.setObject(1, login);
            ResultSet resultSet = statement.executeQuery();


            List<Integer> chats = new ArrayList<Integer>();
            if (resultSet.getString("chats") != null) {
                for (String i : resultSet.getString("chats").split(" ")) {
                    chats.add(Integer.parseInt(i));
                }
            }
            return new User(resultSet.getString("login"), resultSet.getString("passwd"), resultSet.getString("user_token"), resultSet.getString("avatar"), chats);


        } catch (SQLException e) {
            return null;

        }


    }

    public User getUserByToken(String user_token) {


        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from USERS WHERE `user_token`=? ")) {
            statement.setObject(1, user_token);
            ResultSet resultSet = statement.executeQuery();


            List<Integer> chats = new ArrayList<Integer>();
            if (resultSet.getString("chats") != null) {
                for (String i : resultSet.getString("chats").split(" ")) {
                    chats.add(Integer.parseInt(i));
                }
            }
            return new User(resultSet.getString("login"), resultSet.getString("passwd"), resultSet.getString("user_token"), resultSet.getString("avatar"), chats);


        } catch (SQLException e) {
            return null;

        }


    }


    public String registarteUser(String login, String passwd) {


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




    public Chat createChat(String user_token) {
        User user = getUserByToken(user_token);
        if (user == null)
            return null;

        String id = UniqueHashGenerator.getChatId();
        try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO CHATS(`chat_messages_id`,`participants`) VALUES(?, ?);")) {
            statement.setObject(1, id);
            statement.setObject(2, user.login);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE CHAT_" + id + "(timestamps INTEGER NOT NULL, who_wrote TEXT NOT NULL, message_text TEXT, image TEXT);")) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
//
        return new Chat(new ArrayList<Message>(), Collections.singletonList(user.login), id);

    }






    public void SendMessage(Chat chat, Message message){

        try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO CHAT_" + chat.chat_messages_id +
                "(`timestamps`, `who_wrote`, `message_text`, `image` ) VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, new Timestamp(System.currentTimeMillis()).toString());
            statement.setObject(2, message.who_wrote);
            statement.setObject(3, message.text);
            statement.setObject(4, message.image);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }






    public Chat getChatById(String id){
        List<String> participants = new ArrayList<String>();
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from CHATS WHERE `chat_messages_id` = ?")) {
            statement.setObject(1, id);
            ResultSet select_result =  statement.executeQuery();

            participants.addAll(Arrays.asList(select_result.getString("participants").split(" ")));




        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }




        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * from CHAT_"+ id)) {


            ResultSet result =  statement.executeQuery();


            ArrayList<Message> messages= new ArrayList<Message>();
            while (result.next()) {
                messages.add(new Message(result.getString("message_text"),result.getString("image"), result.getString("timestamps"), result.getString("who_wrote")));
            }
            return new Chat(messages,participants  ,id);




        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }







    };
















































   /* public List<Product> getAllProducts() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<Product> products = new ArrayList<Product>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT id, good, price, category_name FROM products");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("good"),
                        resultSet.getDouble("price"),
                        resultSet.getString("category_name")));
            }
            // Возвращаем наш список
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Добавление продукта в БД
    public void addProduct(Product product) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Products(`good`, `price`, `category_name`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, product.good);
            statement.setObject(2, product.price);
            statement.setObject(3, product.category_name);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteProduct(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Products WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
