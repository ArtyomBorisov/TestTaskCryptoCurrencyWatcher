package watcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "watcher.repository")
@EnableScheduling
public class CryptoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoApplication.class, args);
    }
}
