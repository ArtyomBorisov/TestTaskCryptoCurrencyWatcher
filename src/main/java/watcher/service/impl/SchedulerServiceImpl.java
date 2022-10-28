package watcher.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import watcher.dto.Coin;
import watcher.repository.NotificationRepository;
import watcher.repository.entity.NotificationEntity;
import watcher.service.CoinService;
import watcher.service.SchedulerService;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final CoinService coinService;
    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Value("${coin_url}")
    private String urlCoin;

    public SchedulerServiceImpl(CoinService coinService,
                                NotificationRepository notificationRepository) {
        this.coinService = coinService;
        this.notificationRepository = notificationRepository;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    @Override
    public void updatePrice() {
        List<Coin> coins = coinService.get();

        for (Coin coin : coins) {
            Long id = coinService.getId(coin.getSymbol());
            Double newPrice = getPrice(id);
            if (newPrice != null) {
                this.coinService.updatePrice(coin.getSymbol(), newPrice);
                this.logger.info("{}: Обновление цены для монеты {}", LocalDateTime.now(), coin.getSymbol());
            }
        }

        this.notifyUser();
    }

    @Transactional
    @Override
    public void notifyUser() {
        List<NotificationEntity> entities = this.notificationRepository.findAllByEnabledTrue();

        for (NotificationEntity entity : entities) {
            Coin coin = coinService.get(entity.getCoinEntity().getSymbol());

            Double actualPrice = coin.getPriceUsd();
            Double startPrice = entity.getStartPrice();

            double percent = (actualPrice - startPrice) * 100 / startPrice;

            if (Math.abs(percent) >= 1) {
                this.logger.warn( "{} {} {}", entity.getCoinEntity().getSymbol(), entity.getUsername(), percent);
                entity.setEnabled(false);
                this.notificationRepository.save(entity);
            }
        }
    }

    private Double getPrice(Long id) {
        Coin[] coinInArray = this.restTemplate.getForObject(this.urlCoin + id, Coin[].class);
        if (coinInArray != null && coinInArray.length > 0 && coinInArray[0] != null) {
            return coinInArray[0].getPriceUsd();
        } else {
            logger.warn("Ошибка: Цена валюты НЕ ОБНОВЛЕНА!!! {}", id);
            return null;
        }
    }
}
