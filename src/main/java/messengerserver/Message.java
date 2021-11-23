package messengerserver;

public class Message {
    public String text;
    public String image;
    public long timestamp;
    public String whoWrote;


    public Message(String text, String image, long timestamp, String whoWrote) {
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
        this.whoWrote = whoWrote;
    }
}
