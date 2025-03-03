package Websocket.Chatbox.Modals;

public class User {

    private Long id;

    private String userName;

    private String contact;

    public User() {
    }

    public User(Long id, String userName, String contact) {
        this.id = id;
        this.userName = userName;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
