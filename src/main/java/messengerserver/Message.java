package messengerserver;

public class Message {
    public String text;
    // TODO: 2021-11-15 определиться с типом у image
    public String image;
    public long timestamp;

    public Message(String text, String image, long timestamp) {
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
    }
}
