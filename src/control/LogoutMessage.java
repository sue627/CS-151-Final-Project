package control;

public class LogoutMessage implements Message {
    private String sender;

    public LogoutMessage(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
