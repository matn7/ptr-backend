package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.ImportantIndexRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatisticsImportantIndexService implements StatisticsIndexService {
    private final ImportantIndexRepository importantIndexRepository;

    @Override
    public Optional<List<Object[]>> findIndexData(UserEntity userEntity, int year, int month) {
        return importantIndexRepository.findIndexData(userEntity, year, month);
    }
}
