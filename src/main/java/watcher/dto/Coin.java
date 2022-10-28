package watcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coin {
    private String symbol;

    @JsonProperty("price_usd")
    private double priceUsd;

    public Coin() {
    }

    public Coin(String symbol, double priceUsd) {
        this.symbol = symbol;
        this.priceUsd = priceUsd;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPriceUsd() {
        return priceUsd;
    }
}
