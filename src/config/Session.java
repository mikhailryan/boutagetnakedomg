package config;

public class Session {
    private static Session instance;
    private Integer userId;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void clearSession() {
        userId = null;
    }

    public boolean isLoggedIn() {
        return userId != null;
    }
}
