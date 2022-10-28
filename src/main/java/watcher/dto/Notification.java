package watcher.dto;

public class Notification {
    private String username;
    private String symbol;

    public Notification(String username, String symbol) {
        this.username = username;
        this.symbol = symbol;
    }

    public String getUsername() {
        return username;
    }

    public String getSymbol() {
        return symbol;
    }
}
