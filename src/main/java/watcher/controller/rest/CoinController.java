package watcher.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import watcher.dto.Coin;
import watcher.dto.Notification;
import watcher.service.CoinService;

import java.util.List;

@RestController
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = "/coin")
    public List<Coin> get() {
        return this.coinService.get();
    }

    @GetMapping(value = "/coin/{symbol}")
    public Coin get(@PathVariable String symbol) {
        return this.coinService.get(symbol);
    }

    @PostMapping(value = "/notify")
    @ResponseStatus(HttpStatus.CREATED)
    public void notifyPrice(@RequestBody Notification notification) {
        this.coinService.addNotification(notification);
    }
}
