package watcher.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "coin", schema = "app")
public class CoinEntity implements Serializable {
    @Id
    private Long id;

    @Column(name = "price_usd")
    private double priceUsd;

    private String symbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
