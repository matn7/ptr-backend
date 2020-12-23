package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.model.ImportantIndexDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportantIndexService implements IndexService<ImportantIndexDTO> {

    private final ExtraordinaryService extraordinaryService;
    private final DaysService daysService;
    private final ImportantService importantService;
    private final Important2Service important2Service;
    private final Important3Service important3Service;

    @Override
    public ImportantIndexDTO getData(UserEntity userEntity, int year, int month) {
        ImportantIndexDTO importantIndexDTO = new ImportantIndexDTO();
        final List<ExtraordinaryDTO> extraordinaries = extraordinaryService.findByDate(userEntity, year, month);
        final List<DaysDTO> days = daysService.findByDate(userEntity, year, month);
        final List<ImportantDTO> important = importantService.findByDate(userEntity, year, month);
        final List<Important2DTO> important2 = important2Service.findByDate(userEntity, year, month);
        final List<Important3DTO> important3 = important3Service.findByDate(userEntity, year, month);

        // sort here
        Collections.sort(extraordinaries);
        Collections.sort(days);

        Collections.sort(important);
        Collections.sort(important2);
        Collections.sort(important3);

        importantIndexDTO.getExtraordinaryDTO().addAll(extraordinaries);
        importantIndexDTO.getDaysDTO().addAll(days);
        importantIndexDTO.getImportantDTO().addAll(important);
        importantIndexDTO.getImportant2DTO().addAll(important2);
        importantIndexDTO.getImportant3DTO().addAll(important3);

        return importantIndexDTO;
    }
}
