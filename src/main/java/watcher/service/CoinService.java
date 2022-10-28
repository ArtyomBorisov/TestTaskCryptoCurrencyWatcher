package watcher.service;

import watcher.dto.Coin;
import watcher.dto.Notification;

import java.util.List;

public interface CoinService {
    List<Coin> get();
    Coin get(String symbol);
    Long getId(String symbol);
    Coin updatePrice(String symbol, Double newPrice);
    void addNotification(Notification notification);
}
