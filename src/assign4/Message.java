package assign4;

import java.io.Serializable;

public class Message implements Serializable {

    private final User user;
    private final String msgText;
    private final Message inResponseTo;

    public Message(User user, String msgText, Message inResponseTo) {
        this.user = user;
        this.msgText = msgText;
        this.inResponseTo = inResponseTo;
    }

    public User getUser() {
        return user;
    }

    public String getMsgText() {
        return msgText;
    }

    public Message getInResponseTo() {
        return inResponseTo;
    }

    public void print() {
        System.out.println("Sender: " + user.getName());
        System.out.println("Message: " + msgText);
    }
}
