package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.payload.DaysResponse;
import com.pandatronik.payload.ExtraordinaryResponse;
import com.pandatronik.payload.ImportantIndexResponse;
import com.pandatronik.payload.ImportantResponse;
import com.pandatronik.payload.ImportantResponse2;
import com.pandatronik.payload.ImportantResponse3;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrepareImportantIndexDataService implements IndexDataService<ImportantIndexResponse> {
    private final StatisticsImportantIndexService statisticsImportantIndexService;

    @Override
    public List<ImportantIndexResponse> getData(UserEntity userEntity, int year, int month) {
        Optional<List<Object[]>> importantIndexData = statisticsImportantIndexService.findIndexData(userEntity, year, month);

        List<ImportantIndexResponse> importantData = new ArrayList<>();

        for (Object[] obj : importantIndexData.get()) {
            ImportantIndexResponse importantIndexResponse = ImportantIndexResponse.builder()
                    .extraordinaryResponse(ExtraordinaryResponse.builder()
                            .id((Long) obj[0])
                            .title((String) obj[1])
                            .build())
                    .importantResponse(ImportantResponse.builder()
                            .id((Long) obj[2])
                            .made((MadeEnum) obj[3])
                            .title((String) obj[4])
                            .build())
                    .importantResponse2(ImportantResponse2.builder()
                            .id((Long) obj[5])
                            .made((MadeEnum) obj[6])
                            .title((String) obj[7])
                            .build())
                    .importantResponse3(ImportantResponse3.builder()
                            .id((Long) obj[8])
                            .made((MadeEnum) obj[9])
                            .title((String) obj[10])
                            .build())
                    .daysResponse(DaysResponse.builder()
                            .id((Long) obj[11])
                            .rateDay((MadeEnum) obj[12])
                            .build())
                    .build();
            importantData.add(importantIndexResponse);
        }

        return importantData;
    }
}
