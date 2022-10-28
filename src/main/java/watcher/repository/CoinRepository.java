package watcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import watcher.repository.entity.CoinEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<CoinEntity, Integer> {
    List<CoinEntity> findAllByOrderById();
    Optional<CoinEntity> findBySymbol(String symbol);
}
