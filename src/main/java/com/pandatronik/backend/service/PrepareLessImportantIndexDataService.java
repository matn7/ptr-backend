package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.payload.DaysResponse;
import com.pandatronik.payload.ExtraordinaryResponse;
import com.pandatronik.payload.LessImportantIndexResponse;
import com.pandatronik.payload.LessImportantResponse;
import com.pandatronik.payload.LessImportantResponse2;
import com.pandatronik.payload.LessImportantResponse3;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrepareLessImportantIndexDataService implements IndexDataService<LessImportantIndexResponse> {

    private final StatisticsLessImportantIndexService statisticsLessImportantIndexService;

    @Override
    public List<LessImportantIndexResponse> getData(UserEntity userEntity, int year, int month) {
        Optional<List<Object[]>> lessImportantIndexData = statisticsLessImportantIndexService.findIndexData(userEntity, year, month);

        List<LessImportantIndexResponse> lessImportantData = new ArrayList<>();

        for (Object[] obj : lessImportantIndexData.get()) {
            LessImportantIndexResponse importantIndexResponse = LessImportantIndexResponse.builder()
                    .extraordinaryResponse(ExtraordinaryResponse.builder()
                            .id((Long) obj[0])
                            .title((String) obj[1])
                            .build())
                    .lessImportantResponse(LessImportantResponse.builder()
                            .id((Long) obj[2])
                            .made((MadeEnum) obj[3])
                            .title((String) obj[4])
                            .build())
                    .lessImportantResponse2(LessImportantResponse2.builder()
                            .id((Long) obj[5])
                            .made((MadeEnum) obj[6])
                            .title((String) obj[7])
                            .build())
                    .lessImportantResponse3(LessImportantResponse3.builder()
                            .id((Long) obj[8])
                            .made((MadeEnum) obj[9])
                            .title((String) obj[10])
                            .build())
                    .daysResponse(DaysResponse.builder()
                            .id((Long) obj[11])
                            .rateDay((MadeEnum) obj[12])
                            .build())
                    .build();
            lessImportantData.add(importantIndexResponse);
        }

        return lessImportantData;
    }
}
