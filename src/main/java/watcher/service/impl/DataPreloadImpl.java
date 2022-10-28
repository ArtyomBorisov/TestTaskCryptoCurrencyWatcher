package watcher.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import watcher.repository.CoinRepository;
import watcher.repository.entity.CoinEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DataPreloadImpl implements CommandLineRunner {

    private final CoinRepository coinRepository;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(DataPreloadImpl.class);


    @Value("${data_file}")
    private String filePath;

    public DataPreloadImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        String data;

        try {
            data = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            logger.warn("Не удалось прочитать файл с данными для загрузки в бд!", e);
            return;
        }

        List<CoinEntity> entities = mapper.readValue(data, new TypeReference<>() {});

        for (CoinEntity entity : entities) {
            if (coinRepository.findBySymbol(entity.getSymbol()).isEmpty()) {
                coinRepository.save(entity);
            }
        }
    }
}
