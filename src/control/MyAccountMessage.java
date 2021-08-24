package control;

public class MyAccountMessage implements Message {
    private String userName;

    public MyAccountMessage(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
