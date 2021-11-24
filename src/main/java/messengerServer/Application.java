package messengerServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public final static String OK_CODE = "OK!";
    public static final String SUCCESS_STATUS = "success";
    public static final String FAILED_STATUS = "failed";


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}