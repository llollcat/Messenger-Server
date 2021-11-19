package messengerserver;

public class Message {
    public String text;
    public String image;
    public String timestamp;
    public String who_wrote;

    public Message(String text, String image, String timestamp, String who_wrote) {
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
        this.who_wrote = who_wrote;
    }
}
