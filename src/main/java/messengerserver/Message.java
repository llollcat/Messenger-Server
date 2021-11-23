package messengerserver;

public class Message {
    public String text;
    public String image;
    public long timestamp;
    public String who_wrote;

    public Message(String text, String image, long timestamp, String who_wrote) {
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
        this.who_wrote = who_wrote;
    }
}
