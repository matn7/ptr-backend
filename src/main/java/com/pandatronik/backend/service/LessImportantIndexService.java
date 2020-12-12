package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.persistence.model.LessImportantIndexDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessImportantIndexService implements IndexService<LessImportantIndexDTO> {
    private final ExtraordinaryService extraordinaryService;
    private final DaysService daysService;
    private final LessImportantService lessImportantService;
    private final LessImportant2Service lessImportant2Service;
    private final LessImportant3Service lessImportant3Service;

    @Override
    public LessImportantIndexDTO getData(UserEntity userEntity, int year, int month) {
        LessImportantIndexDTO lessImportantIndexDTO = new LessImportantIndexDTO();
        final List<ExtraordinaryDTO> extraordinaries = extraordinaryService.findByDate(userEntity, year, month);
        final List<DaysDTO> days = daysService.findByDate(userEntity, year, month);
        final List<LessImportantDTO> lessImportant = lessImportantService.findByDate(userEntity, year, month);
        final List<LessImportant2DTO> lessImportant2 = lessImportant2Service.findByDate(userEntity, year, month);
        final List<LessImportant3DTO> lessImportant3 = lessImportant3Service.findByDate(userEntity, year, month);

        // sort here
        Collections.sort(extraordinaries);
        Collections.sort(days);

        Collections.sort(lessImportant);
        Collections.sort(lessImportant2);
        Collections.sort(lessImportant3);

        lessImportantIndexDTO.getExtraordinaryDTO().addAll(extraordinaries);
        lessImportantIndexDTO.getDaysDTO().addAll(days);
        lessImportantIndexDTO.getLessImportantDTO().addAll(lessImportant);
        lessImportantIndexDTO.getLessImportant2DTO().addAll(lessImportant2);
        lessImportantIndexDTO.getLessImportant3DTO().addAll(lessImportant3);

        return lessImportantIndexDTO;
    }
}
