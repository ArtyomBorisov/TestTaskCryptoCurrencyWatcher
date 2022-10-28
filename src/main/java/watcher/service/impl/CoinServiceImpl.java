package watcher.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watcher.dto.Coin;
import watcher.dto.Notification;
import watcher.repository.CoinRepository;
import watcher.repository.NotificationRepository;
import watcher.repository.entity.CoinEntity;
import watcher.repository.entity.NotificationEntity;
import watcher.service.CoinService;
import watcher.exception.CustomException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;
    private final NotificationRepository notificationRepository;

    public CoinServiceImpl(CoinRepository coinRepository,
                           NotificationRepository notificationRepository) {
        this.coinRepository = coinRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Coin> get() {
        return coinRepository.findAllByOrderById().stream()
                .map(entity -> new Coin(entity.getSymbol(), entity.getPriceUsd()))
                .collect(Collectors.toList());
    }

    @Override
    public Coin get(String symbol) {
        CoinEntity entity = findBySymbol(symbol);

        return new Coin(entity.getSymbol(), entity.getPriceUsd());
    }

    @Override
    public Long getId(String symbol) {
        CoinEntity entity = coinRepository.findBySymbol(symbol).orElse(null);

        return entity == null ? -1 : entity.getId();
    }

    @Transactional
    @Override
    public Coin updatePrice(String symbol, Double newPrice) {
        CoinEntity entity = findBySymbol(symbol);
        entity.setPriceUsd(newPrice);
        coinRepository.save(entity);

        return new Coin(entity.getSymbol(), entity.getPriceUsd());
    }

    @Transactional
    @Override
    public void addNotification(Notification notification) {
        if (notification == null) {
            throw new CustomException("Не передано уведомление");
        }

        CoinEntity coinEntity = findBySymbol(notification.getSymbol());
        NotificationEntity notificationEntity = new NotificationEntity(notification.getUsername(), coinEntity);
        notificationEntity.setStartPrice(coinEntity.getPriceUsd());

        notificationRepository.save(notificationEntity);
    }

    private CoinEntity findBySymbol(String symbol) {
        if (symbol == null) {
            throw new CustomException("Не передан код валюты");
        }

        return this.coinRepository.findBySymbol(symbol).orElseThrow(
                () -> new CustomException("Передан несуществующий код валюты - " + symbol));
    }
}
