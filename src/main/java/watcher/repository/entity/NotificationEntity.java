package watcher.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_notification", schema = "app")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    private CoinEntity coinEntity;

    @Column(name = "start_price")
    private Double startPrice;

    private Boolean enabled;

    public NotificationEntity() {
        this.enabled = true;
    }

    public NotificationEntity(String username, CoinEntity coinEntity) {
        this.username = username;
        this.coinEntity = coinEntity;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CoinEntity getCoinEntity() {
        return coinEntity;
    }

    public void setCoinEntity(CoinEntity coinEntity) {
        this.coinEntity = coinEntity;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
