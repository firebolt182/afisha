package org.javaacademy.afisha.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Profile("first")
@RequiredArgsConstructor
public class InitDatabaseService {
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        String query = Files.readString(Path.of("resources/init.sql"));
        jdbcTemplate.execute(query);
    }
}
